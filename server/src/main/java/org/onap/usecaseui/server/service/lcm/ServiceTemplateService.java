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
package org.onap.usecaseui.server.service.lcm;

import org.onap.usecaseui.server.bean.lcm.ServiceTemplateInput;
import org.onap.usecaseui.server.service.lcm.domain.aai.bean.SDNCController;
import org.onap.usecaseui.server.service.lcm.domain.aai.bean.VimInfo;
import org.onap.usecaseui.server.service.lcm.domain.sdc.bean.SDCServiceTemplate;

import java.util.List;

public interface ServiceTemplateService {

    List<SDCServiceTemplate> listDistributedServiceTemplate();

    ServiceTemplateInput fetchServiceTemplateInput(String uuid, String toscaModelPath);

    List<VimInfo> listVim();

    List<SDNCController> listSDNCControllers();
}