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

import org.onap.usecaseui.server.service.lcm.domain.so.bean.DeleteOperationRsp;
import org.onap.usecaseui.server.service.lcm.domain.so.bean.ServiceOperation;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

public interface SotnServiceTemplateService {

    public String getSOTNSiteInformationTopology(String subscriptionType, String instanceid);
    public ServiceOperation instantiate_CCVPN_Service(HashMap<String, Object> reqt);
    //public DeleteOperationRsp deleteService(String serviceId, String subscriptionType, HttpServletRequest request);
    public String getSOTNResourceInformationTopology(String subscriptionType, String instanceid) throws Exception;
    public String getService(String subscriptionType, String instanceid);
}
