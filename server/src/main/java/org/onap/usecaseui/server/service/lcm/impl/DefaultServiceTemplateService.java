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
import okhttp3.ResponseBody;
import org.onap.usecaseui.server.bean.lcm.ServiceTemplateInput;
import org.onap.usecaseui.server.bean.lcm.TemplateInput;
import org.onap.usecaseui.server.service.lcm.ServiceTemplateService;
import org.onap.usecaseui.server.service.lcm.domain.aai.AAIService;
import org.onap.usecaseui.server.service.lcm.domain.aai.bean.VimInfo;
import org.onap.usecaseui.server.service.lcm.domain.aai.exceptions.AAIException;
import org.onap.usecaseui.server.service.lcm.domain.sdc.SDCCatalogService;
import org.onap.usecaseui.server.service.lcm.domain.sdc.bean.SDCServiceTemplate;
import org.onap.usecaseui.server.service.lcm.domain.sdc.exceptions.SDCCatalogException;
import org.onap.usecaseui.server.util.RestfulServices;
import org.openecomp.sdc.toscaparser.api.NodeTemplate;
import org.openecomp.sdc.toscaparser.api.ToscaTemplate;
import org.openecomp.sdc.toscaparser.api.common.JToscaException;
import org.openecomp.sdc.toscaparser.api.parameters.Input;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.onap.usecaseui.server.service.lcm.domain.sdc.consts.SDCConsts.CATEGORY_E2E_SERVICE;
import static org.onap.usecaseui.server.service.lcm.domain.sdc.consts.SDCConsts.DISTRIBUTION_STATUS_DISTRIBUTED;

@Service("ServiceTemplateService")
@org.springframework.context.annotation.Configuration
@EnableAspectJAutoProxy
public class DefaultServiceTemplateService implements ServiceTemplateService {

    private static final Logger logger = LoggerFactory.getLogger(DefaultServiceTemplateService.class);

    private SDCCatalogService sdcCatalog;

    private AAIService aaiService;

    public DefaultServiceTemplateService() {
        this(RestfulServices.create("https://sdc.api.simpledemo.openecomp.org:8443", SDCCatalogService.class), RestfulServices.create("https://aai.api.simpledemo.openecomp.org:8443", AAIService.class));
    }

    public DefaultServiceTemplateService(SDCCatalogService sdcCatalog, AAIService aaiService) {
        this.sdcCatalog = sdcCatalog;
        this.aaiService = aaiService;
    }

    @Override
    public List<SDCServiceTemplate> listDistributedServiceTemplate() {
        try {
            return this.sdcCatalog.listServices(CATEGORY_E2E_SERVICE, DISTRIBUTION_STATUS_DISTRIBUTED).execute().body();
        } catch (IOException e) {
            logger.error("Visit SDC Catalog occur exception");
            throw new SDCCatalogException("SDC Catalog is not available.", e);
        }
    }

    @Override
    public ServiceTemplateInput fetchServiceTemplateInput(String uuid, String toscaModelPath) {
        return fetchServiceTemplate(uuid, toscaModelPath);
    }

    private ServiceTemplateInput fetchServiceTemplate(String uuid, String toscaModelPath) {
        String toPath = String.format("/home/uui/%s.csar", uuid);
        try {
            downloadFile(toscaModelPath, toPath);
            return extractTemplate(toPath);
        }  catch (IOException e) {
            throw new SDCCatalogException("download csar file failed!", e);
        } catch (JToscaException e) {
            throw new SDCCatalogException("parse csar file failed!", e);
        }
    }

    protected void downloadFile(String toscaModelPath, String toPath) throws IOException {
        try {
            String msbUrl = RestfulServices.getMsbAddress();
            String templateUrl = String.format("http://%s/%s", msbUrl, toscaModelPath);
            ResponseBody body = sdcCatalog.downloadCsar(templateUrl).execute().body();
            Files.write(body.bytes(),new File(toPath));
        } catch (IOException e) {
            logger.error(String.format("Download %s failed!", toscaModelPath));
            throw e;
        }
    }

    private ServiceTemplateInput extractTemplate(String toPath) throws JToscaException, IOException {
        ToscaTemplate tosca = translateToToscaTemplate(toPath);
        ServiceTemplateInput serviceTemplateInput = fetchServiceTemplateInput(tosca);
        for (NodeTemplate nodeTemplate : tosca.getNodeTemplates()) {
            String nodeUUID = nodeTemplate.getMetaData().getValue("UUID");
            SDCServiceTemplate template = sdcCatalog.getService(nodeUUID).execute().body();
            String toscaModelURL = template.getToscaModelURL();
            if (toscaModelURL == null) {
                continue;
            }
            ServiceTemplateInput nodeService = fetchServiceTemplate(nodeUUID, toscaModelURL);
            serviceTemplateInput.addNestedTemplate(nodeService);
        }
        return serviceTemplateInput;
    }

//    private List<ServiceTemplateInput> extractInputs(String toPath, List<ServiceTemplateInput> serviceTemplateInputs) throws JToscaException, IOException {
//        ToscaTemplate tosca = translateToToscaTemplate(toPath);
//        ServiceTemplateInput serviceTemplateInput = fetchServiceTemplateInput(tosca);
//        serviceTemplateInputs.add(serviceTemplateInput);
//        for (NodeTemplate nodeTemplate : tosca.getNodeTemplates()) {
//            String nodeUUID = nodeTemplate.getMetaData().getValue("UUID");
//            SDCServiceTemplate template = sdcCatalog.getService(nodeUUID).execute().body();
//            String toscaModelURL = template.getToscaModelURL();
//            if (toscaModelURL == null) {
//                continue;
//            }
//            String savePath = String.format("temp/%s.csar", nodeUUID);
//            downloadFile(toscaModelURL, savePath);
//            extractInputs(savePath, serviceTemplateInputs);
//        }
//        return serviceTemplateInputs;
//    }

    protected ToscaTemplate translateToToscaTemplate(String toPath) throws JToscaException {
        return new ToscaTemplate(toPath,null,true,null,true);
    }

    private static ServiceTemplateInput fetchServiceTemplateInput(ToscaTemplate tosca) {
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
        if(subcategory == null) {
            subcategory = "";
        }
        List<TemplateInput> templateInputs = new ArrayList<>();
        for(Input input : tosca.getInputs()) {
            templateInputs.add(new TemplateInput(
                    input.getName(),
                    input.getType(),
                    input.getDescription(),
                    String.valueOf(input.isRequired()),
                    String.valueOf(input.getDefault())
            ));
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
                templateInputs);
    }

    @Override
    public List<VimInfo> listVim() {
        try {
            return aaiService.listVimInfo().execute().body();
        } catch (IOException e) {
            logger.error("Visit AAI occur exception");
            throw new AAIException("AAI is not available.", e);
        }
    }
}
