/**
 * Copyright 2016-2017 ZTE Corporation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.onap.usecaseui.server.service.lcm.impl;

import com.google.common.io.Files;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.ResponseBody;
import org.onap.usecaseui.server.bean.lcm.ServiceTemplateInput;
import org.onap.usecaseui.server.bean.lcm.TemplateInput;
import org.onap.usecaseui.server.constant.CommonConstant;
import org.onap.usecaseui.server.service.lcm.ServiceTemplateService;
import org.onap.usecaseui.server.service.lcm.domain.aai.AAIClient;
import org.onap.usecaseui.server.service.lcm.domain.aai.bean.SDNCController;
import org.onap.usecaseui.server.service.lcm.domain.aai.bean.SDNCControllerRsp;
import org.onap.usecaseui.server.service.lcm.domain.aai.bean.VimInfo;
import org.onap.usecaseui.server.service.lcm.domain.aai.bean.VimInfoRsp;
import org.onap.usecaseui.server.service.lcm.domain.aai.exceptions.AAIException;
import org.onap.usecaseui.server.service.lcm.domain.sdc.SDCCatalogClient;
import org.onap.usecaseui.server.service.lcm.domain.sdc.bean.SDCServiceTemplate;
import org.onap.usecaseui.server.service.lcm.domain.sdc.exceptions.SDCCatalogException;
import org.onap.usecaseui.server.util.RestfulServices;
import org.onap.usecaseui.server.util.UuiCommonUtil;
import org.openecomp.sdc.toscaparser.api.NodeTemplate;
import org.openecomp.sdc.toscaparser.api.Property;
import org.openecomp.sdc.toscaparser.api.ToscaTemplate;
import org.openecomp.sdc.toscaparser.api.common.JToscaException;
import org.openecomp.sdc.toscaparser.api.parameters.Input;
import org.springframework.stereotype.Service;
import retrofit2.Response;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static org.onap.usecaseui.server.service.lcm.domain.sdc.consts.SDCConsts.CATEGORY_E2E_SERVICE;
import static org.onap.usecaseui.server.service.lcm.domain.sdc.consts.SDCConsts.DISTRIBUTION_STATUS_DISTRIBUTED;

@Slf4j
@RequiredArgsConstructor
@Service("ServiceTemplateService")
public class DefaultServiceTemplateService implements ServiceTemplateService {

    private final SDCCatalogClient sdcCatalog;
    private final AAIClient aaiClient;

    @Override
    public List<SDCServiceTemplate> listDistributedServiceTemplate() {
        try {
            Response<List<SDCServiceTemplate>> response = this.sdcCatalog.listServices(CATEGORY_E2E_SERVICE, DISTRIBUTION_STATUS_DISTRIBUTED).execute();
            if (response.isSuccessful()) {
                return response.body();
            } else {
                log.info(String.format("Can not get distributed e2e service templates[code=%s, message=%s]", response.code(), response.message()));
                return Collections.emptyList();
            }
        } catch (IOException e) {
            log.error("Visit SDC Catalog occur exception");
            throw new SDCCatalogException("SDC Catalog is not available.", e);
        }
    }

    @Override
    public ServiceTemplateInput fetchServiceTemplateInput(String uuid, String toscaModelPath) {
        return fetchServiceTemplate(uuid, toscaModelPath, false);
    }

    private ServiceTemplateInput fetchServiceTemplate(String uuid, String toscaModelPath, boolean isVF) {
        String toPath = String.format("/home/uui/%s.csar", uuid);
        //String toPath = String.format("D:\\work/%s.csar", uuid);
        try {
        	if(!UuiCommonUtil.isExistFile(toPath)){
        		downloadFile(toscaModelPath, toPath);
        	}
            return extractTemplate(toPath, isVF);
        }  catch (IOException e) {
            throw new SDCCatalogException("download csar file failed!", e);
        } catch (JToscaException e) {
            throw new SDCCatalogException("parse csar file failed!", e);
        }
    }

    protected void downloadFile(String toscaModelPath, String toPath) throws IOException {
        try {
            String msbUrl = RestfulServices.getMsbAddress();
            String templateUrl = String.format("https://%s%s", msbUrl, toscaModelPath);
            log.info("download Csar File Url is:"+templateUrl);
            ResponseBody body = sdcCatalog.downloadCsar(templateUrl).execute().body();
            Files.write(body.bytes(),new File(toPath));
        } catch (IOException e) {
            log.error(String.format("Download %s failed!", toscaModelPath));
            throw e;
        }
    }

    public ServiceTemplateInput extractTemplate(String toPath, boolean isVF) throws JToscaException, IOException {
        ToscaTemplate tosca = translateToToscaTemplate(toPath);
        ServiceTemplateInput serviceTemplateInput = newServiceTemplateInput(tosca);
        Map<String, Input> inputsMap = getInputsMap(tosca);
        for (NodeTemplate nodeTemplate : tosca.getNodeTemplates()) {
            String nodeType = nodeTemplate.getMetaData().getValue("type");
            if ("VF".equals(nodeType)) {
                ServiceTemplateInput nodeService = fetchVFNodeTemplateInput(nodeTemplate);
                if (nodeService == null) {
                    continue;
                }
                serviceTemplateInput.addNestedTemplate(nodeService);
            } else {
                ServiceTemplateInput nodeService = fetchVLServiceTemplateInput(nodeTemplate, inputsMap);
                serviceTemplateInput.addNestedTemplate(nodeService);
            }
        }
        List<TemplateInput> serviceInputs = getServiceInputs(inputsMap.values());
        serviceTemplateInput.addInputs(serviceInputs);
        if (isVF) {
            serviceTemplateInput.setType("VF");
            appendLocationParameters(serviceTemplateInput, tosca);
            appendSdnControllerParameter(serviceTemplateInput);
        }
        return serviceTemplateInput;
    }

    private void appendLocationParameters(ServiceTemplateInput serviceTemplateInput, ToscaTemplate tosca) {
        for (NodeTemplate nodeTemplate : tosca.getNodeTemplates()) {
            String type = nodeTemplate.getMetaData().getValue("type");
            String uuid = nodeTemplate.getMetaData().getValue("UUID");
            String nodeName = nodeTemplate.getMetaData().getValue("name");
//            String nodeName = nodeTemplate.getName();
            if ("VF".equals(type)) {
                serviceTemplateInput.addInput(
                        new TemplateInput(
                                uuid,
                                "vf_location",
                                nodeName,
                                "true",
                                ""
                        )
                );
            }
        }
    }

    private void appendSdnControllerParameter(ServiceTemplateInput serviceTemplateInput) {
        serviceTemplateInput.addInput(
                new TemplateInput(
                        "sdncontroller",
                        "sdn_controller",
                        "location for the service",
                        "true",
                        ""
                )
        );
    }

    private ServiceTemplateInput fetchVLServiceTemplateInput(NodeTemplate nodeTemplate, Map<String, Input> inputsMap) {
        ServiceTemplateInput nodeService = newServiceTemplateInput(nodeTemplate);
        //String prefix = getPrefix(nodeTemplate.getName());
        List<TemplateInput> templateInputs = getServiceInputs(inputsMap.values());
        nodeService.addInputs(templateInputs);
        return nodeService;
    }

    private ServiceTemplateInput fetchVFNodeTemplateInput(NodeTemplate nodeTemplate) throws IOException {
        String nodeUUID = fetchNodeUUID(nodeTemplate);
        if (nodeUUID == null) {
            // not found nested node
            return null;
        }
        String toscaModelURL = getToscaUrl(nodeUUID);
        if (toscaModelURL == null) {
            return null;
        }
        //return fetchServiceTemplate("f809cda7-7ce3-4a9b-a2a0-9af84051bfb5", "", true);
        return fetchServiceTemplate(nodeUUID, toscaModelURL, true);
    }

    private List<TemplateInput> getServiceInputs(Collection<Input> inputs) {
        List<TemplateInput> result = new ArrayList<>();
        for (Input input : inputs) {
            result.add(
                    new TemplateInput(
                            input.getName(),
                            input.getType(),
                            input.getDescription(),
                            String.valueOf(input.isRequired()),
                            String.valueOf(input.getDefault())
                    )
            );
        }
        return result;
    }

    private String fetchNodeUUID(NodeTemplate nodeTemplate) {
        LinkedHashMap<String, Property> properties = nodeTemplate.getProperties();
        for (Map.Entry<String, Property> entry : properties.entrySet()) {
            String key = entry.getKey();
            if (key.endsWith("providing_service_uuid")) {
            	CommonConstant.netWorkMap.put(String.valueOf(entry.getValue().getValue()), newServiceTemplateInput(nodeTemplate));
                return String.valueOf(entry.getValue().getValue());
            }
        }
        // not found
        return null;
    }

//    private List<TemplateInput> collectInputs(String prefix, Map<String, Input> inputsMap) {
//        List<TemplateInput> result = new ArrayList<>();
//        List<String> removeItems = new ArrayList<>();
//        for (Map.Entry<String, Input> entry : inputsMap.entrySet()) {
//            String name = entry.getKey();
//            if (name.startsWith(prefix)) {
//                //remove resource name prefix which sdc added.
//                name = name.substring(prefix.length() + 1);
//                Input in = entry.getValue();
//                result.add(
//                        new TemplateInput(
//                                name,
//                                in.getType(),
//                                in.getDescription(),
//                                String.valueOf(in.isRequired()),
//                                String.valueOf(in.getDefault())
//                        )
//                );
//                removeItems.add(entry.getKey());
//            }
//        }
//        for (String key : removeItems) {
//            inputsMap.remove(key);
//        }
//        return result;
//    }

    private Map<String, Input> getInputsMap(ToscaTemplate tosca) {
        Map<String, Input> result = new HashMap<>();
        for (Input input : tosca.getInputs()) {
            result.put(input.getName(), input);
        }
        return result;
    }

//    private String getPrefix(String name) {
//        return name.replaceAll(" +", "").toLowerCase();
//    }

    protected String getToscaUrl(String nodeUUID) throws IOException {
        Response<SDCServiceTemplate> response = sdcCatalog.getService(nodeUUID).execute();
        if (response.isSuccessful()) {
            SDCServiceTemplate template = response.body();
            return template.getToscaModelURL();
        } else {
            log.info(String.format("Cannot get tosca model for node template[%s]", nodeUUID));
            return null;
        }
    }

    protected ToscaTemplate translateToToscaTemplate(String toPath) throws JToscaException {
//        return new ToscaTemplate(toPath,null,true,null,true);
        return new ToscaTemplate(toPath,null,true,null);
    }

    private static ServiceTemplateInput newServiceTemplateInput(ToscaTemplate tosca) {
    	if(CommonConstant.netWorkMap.containsKey(tosca.getMetaData().getValue("UUID"))){
    		return CommonConstant.netWorkMap.get(tosca.getMetaData().getValue("UUID"));
    	}else{
            String invariantUUID = tosca.getMetaData().getValue("invariantUUID");
            String uuid = tosca.getMetaData().getValue("UUID");
            String name = tosca.getMetaData().getValue("name");
            String type = tosca.getMetaData().getValue("type");
            String version = tosca.getMetaData().getValue("version");
            if (version == null) {
                version = "";
            }
            String description = tosca.getMetaData().getValue("description");
            String category = tosca.getMetaData().getValue("category");
            String subcategory = tosca.getMetaData().getValue("subcategory");
            String customizationUuid = tosca.getMetaData().getValue("customizationUUID");
            if(subcategory == null) {
                subcategory = "";
            }
            return new ServiceTemplateInput(
                    invariantUUID,
                    uuid,
                    name,
                    type,
                    version,
                    description,
                    category,
                    subcategory,
                    customizationUuid,
                    new ArrayList<>());
    	}
    }

    private static ServiceTemplateInput newServiceTemplateInput(NodeTemplate nodeTemplate) {
        String invariantUUID = nodeTemplate.getMetaData().getValue("invariantUUID");
        String uuid = nodeTemplate.getMetaData().getValue("UUID");
        String name = nodeTemplate.getMetaData().getValue("name");
        String type = nodeTemplate.getMetaData().getValue("type");
        String version = nodeTemplate.getMetaData().getValue("version");
        if (version == null) {
            version = "";
        }
        String description = nodeTemplate.getMetaData().getValue("description");
        String category = nodeTemplate.getMetaData().getValue("category");
        String subcategory = nodeTemplate.getMetaData().getValue("subcategory");
        String customizationUuid = nodeTemplate.getMetaData().getValue("customizationUUID");
        if(subcategory == null) {
            subcategory = "";
        }
        return new ServiceTemplateInput(
                invariantUUID,
                uuid,
                name,
                type,
                version,
                description,
                category,
                subcategory,
                customizationUuid,
                new ArrayList<>());
    }

    @Override
    public List<VimInfo> listVim() {
        try {
            Response<VimInfoRsp> response = aaiClient.listVimInfo().execute();
            if (response.isSuccessful()) {
                return response.body().getCloudRegion();
            } else {
                log.info(String.format("Can not get vim info[code=%s, message=%s]", response.code(), response.message()));
                return Collections.emptyList();
            }
        } catch (IOException e) {
            log.error("Visit AAI occur exception");
            throw new AAIException("AAI is not available.", e);
        }
    }

    @Override
    public List<SDNCController> listSDNCControllers() {
        try {
            Response<SDNCControllerRsp> response = aaiClient.listSdncControllers().execute();
            if (response.isSuccessful()) {
                return response.body().getEsrThirdpartySdncList();
            } else {
                log.info(String.format("Can not get sdnc controllers[code=%s, message=%s]", response.code(), response.message()));
                return Collections.emptyList();
            }
        } catch (IOException e) {
            log.error("Visit AAI occur exception");
            throw new AAIException("AAI is not available.", e);
        }
    }
}
