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

import org.junit.Assert;
import org.junit.Test;
import org.onap.usecaseui.server.bean.lcm.ServiceTemplateInput;
import org.onap.usecaseui.server.bean.lcm.TemplateInput;
import org.onap.usecaseui.server.service.lcm.ServiceTemplateService;
import org.onap.usecaseui.server.service.lcm.domain.aai.AAIService;
import org.onap.usecaseui.server.service.lcm.domain.aai.bean.VimInfo;
import org.onap.usecaseui.server.service.lcm.domain.sdc.SDCCatalogService;
import org.onap.usecaseui.server.service.lcm.domain.sdc.bean.SDCServiceTemplate;
import org.onap.usecaseui.server.service.lcm.domain.sdc.exceptions.SDCCatalogException;
import org.openecomp.sdc.toscaparser.api.NodeTemplate;
import org.openecomp.sdc.toscaparser.api.ToscaTemplate;
import org.openecomp.sdc.toscaparser.api.common.JToscaException;
import org.openecomp.sdc.toscaparser.api.elements.Metadata;
import org.openecomp.sdc.toscaparser.api.parameters.Input;
import retrofit2.Call;

import java.io.IOException;
import java.util.*;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.onap.usecaseui.server.service.lcm.domain.sdc.consts.SDCConsts.CATEGORY_E2E_SERVICE;
import static org.onap.usecaseui.server.service.lcm.domain.sdc.consts.SDCConsts.DISTRIBUTION_STATUS_DISTRIBUTED;
import static org.onap.usecaseui.server.util.CallStub.failedCall;
import static org.onap.usecaseui.server.util.CallStub.successfulCall;

public class DefaultServiceTemplateServiceTest {

    @Test
    public void itCanListDistributedServiceTemplate() {
        List<SDCServiceTemplate> templates = Collections.singletonList(new SDCServiceTemplate("uuid", "uuid", "name", "V1","url", "category"));
        SDCCatalogService sdcService = mock(SDCCatalogService.class);
        when(sdcService.listServices(CATEGORY_E2E_SERVICE, DISTRIBUTION_STATUS_DISTRIBUTED)).thenReturn(successfulCall(templates));

        ServiceTemplateService service = new DefaultServiceTemplateService(sdcService,null);

        Assert.assertSame(templates, service.listDistributedServiceTemplate());
    }

    @Test(expected = SDCCatalogException.class)
    public void retrieveServiceWillThrowExceptionWhenSDCIsNotAvailable() {
        SDCCatalogService sdcService = mock(SDCCatalogService.class);
        when(sdcService.listServices(CATEGORY_E2E_SERVICE, DISTRIBUTION_STATUS_DISTRIBUTED)).thenReturn(failedCall("SDC is not available!"));

        ServiceTemplateService service = new DefaultServiceTemplateService(sdcService,null);
        service.listDistributedServiceTemplate();
    }

    @Test
    public void itCanRetrieveInputsFromServiceTemplate() throws IOException {
        final String uuid = "1";
        String modelPath = "model_path";
        String nodeUUID = "2";

        SDCCatalogService sdcService = newSdcCatalogService(nodeUUID);

        List<VimInfo> vim = Collections.singletonList(new VimInfo("owner", "regionId"));
        AAIService aaiService = newAAIService(vim);

        ServiceTemplateService service = newServiceTemplateService(uuid, nodeUUID, sdcService, aaiService);

        Assert.assertThat(service.fetchServiceTemplateInput(uuid, modelPath), equalTo(expectedServiceInputs(uuid, nodeUUID)));
    }

    private DefaultServiceTemplateService newServiceTemplateService(String uuid, String nodeUUID, SDCCatalogService sdcService, AAIService aaiService) {
        return new DefaultServiceTemplateService(sdcService, aaiService) {

            @Override
            protected void downloadFile(String templateUrl, String toPath) throws IOException {
                // download successfully...
            }

            @Override
            protected ToscaTemplate translateToToscaTemplate(String toPath) throws JToscaException {
                if (toPath.contains(uuid)) {
                    return e2eToscaTemplate(nodeUUID);
                }
                return nodeToscaTemplate(nodeUUID);
            }
        };
    }

    private SDCCatalogService newSdcCatalogService(String nodeUUID) throws IOException {
        SDCCatalogService sdcService = mock(SDCCatalogService.class);
        when(sdcService.getService(nodeUUID)).thenReturn(successfulCall(new SDCServiceTemplate(nodeUUID, nodeUUID, "node", "V1", "nodeModelUrl", "service")));
        return sdcService;
    }

    private ServiceTemplateInput expectedServiceInputs(String uuid, String nodeUUID) {
        ServiceTemplateInput e2eServiceTemplateInput = new ServiceTemplateInput(
                uuid, uuid, "VoLTE", "service","", "VoLTE", "service", "", Collections.EMPTY_LIST);
        TemplateInput templateInput = new TemplateInput("field_name","field_type", "field_description", "true", "field_default");
        ServiceTemplateInput nodeTemplateInput = new ServiceTemplateInput(
                nodeUUID, nodeUUID, "", "", "","", "service", "", Collections.singletonList(templateInput));
        e2eServiceTemplateInput.addNestedTemplate(nodeTemplateInput);
        return e2eServiceTemplateInput;
    }

    private ToscaTemplate e2eToscaTemplate(String nodeUUID) {
        ToscaTemplate toscaTemplate = mock(ToscaTemplate.class);
        Map<String, Object> e2eAttributes = new HashMap<>();
        e2eAttributes.put("invariantUUID", "1");
        e2eAttributes.put("UUID", "1");
        e2eAttributes.put("name", "VoLTE");
        e2eAttributes.put("type", "service");
        e2eAttributes.put("description", "VoLTE");
        e2eAttributes.put("category", "service");
        e2eAttributes.put("subcategory", "");
        when(toscaTemplate.getMetaData()).thenReturn(new Metadata(e2eAttributes));
        when(toscaTemplate.getInputs()).thenReturn(new ArrayList<>());
        NodeTemplate nodeTemplate = mock(NodeTemplate.class);

        Map<String, Object> nodeUUIDAttr = new HashMap<>();

        nodeUUIDAttr.put("UUID", nodeUUID);
        when(nodeTemplate.getMetaData()).thenReturn(new Metadata(nodeUUIDAttr));

        ArrayList<NodeTemplate> nodeTemplates = new ArrayList<>();
        nodeTemplates.add(nodeTemplate);
        when(toscaTemplate.getNodeTemplates()).thenReturn(nodeTemplates);

        return toscaTemplate;
    }

    private ToscaTemplate nodeToscaTemplate(String nodeUUID) {
        ToscaTemplate toscaTemplate = mock(ToscaTemplate.class);
        Map<String, Object> Attributes = new HashMap<>();
        Attributes.put("invariantUUID", nodeUUID);
        Attributes.put("UUID", nodeUUID);
        Attributes.put("name", "");
        Attributes.put("type", "");
        Attributes.put("description", "");
        Attributes.put("category", "service");
        Attributes.put("subcategory", "");
        when(toscaTemplate.getMetaData()).thenReturn(new Metadata(Attributes));

        Input input = mock(Input.class);
        when(input.getName()).thenReturn("field_name");
        when(input.getDescription()).thenReturn("field_description");
        when(input.getType()).thenReturn("field_type");
        when(input.getDefault()).thenReturn("field_default");
        when(input.isRequired()).thenReturn(true);

        ArrayList<Input> inputs = new ArrayList<>();
        inputs.add(input);
        when(toscaTemplate.getInputs()).thenReturn(inputs);
        when(toscaTemplate.getNodeTemplates()).thenReturn(new ArrayList<>());

        return toscaTemplate;
    }

    private AAIService newAAIService(List<VimInfo> vim) {
        AAIService aaiService = mock(AAIService.class);

        Call<List<VimInfo>> vimCall = successfulCall(vim);
        when(aaiService.listVimInfo()).thenReturn(vimCall);
        return aaiService;
    }

    @Test(expected = SDCCatalogException.class)
    public void retrieveInputsWillThrowExceptionWhenDownloadFailed() {
        ServiceTemplateService service = new DefaultServiceTemplateService() {
            @Override
            protected void downloadFile(String templateUrl, String toPath) throws IOException {
                throw new IOException("download failed!");
            }
        };
        service.fetchServiceTemplateInput("1", "url");
    }

    @Test(expected = SDCCatalogException.class)
    public void retrieveInputsWillThrowExceptionWhenParsingToscaTemplateFailed() {
        ServiceTemplateService service = new DefaultServiceTemplateService() {
            @Override
            protected void downloadFile(String templateUrl, String toPath) throws IOException {
                // download successfully...
            }

            @Override
            protected ToscaTemplate translateToToscaTemplate(String toPath) throws JToscaException {
                throw new JToscaException("parse tosca template failed!", "123");
            }
        };
        service.fetchServiceTemplateInput("1", "url");
    }
}