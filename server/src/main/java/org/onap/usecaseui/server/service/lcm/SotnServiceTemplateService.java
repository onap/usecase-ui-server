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

import org.onap.usecaseui.server.bean.activateEdge.ServiceInstance;
import org.onap.usecaseui.server.bean.lcm.sotne2eservice.FileWrapper;
import org.onap.usecaseui.server.service.lcm.domain.so.bean.DeleteOperationRsp;
import org.onap.usecaseui.server.service.lcm.domain.so.bean.ServiceOperation;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public interface SotnServiceTemplateService {
    public String ServiceCost(HttpServletRequest request);
    public ServiceOperation instantiateService(HttpServletRequest request);
    public ServiceOperation instantiate_CCVPN_Service(HashMap<String, Object> reqt);
    public String getSOTNInstantiationstatus(String instanceid);
    public String getService(String subscriptionType, String instanceid);
    public DeleteOperationRsp deleteService(String serviceId, String subscriptionType, HttpServletRequest request);
    public String getSOTNSiteInformationTopology(String subscriptionType, String instanceid);
    public FileWrapper getSOTNBandWidthData(String subscriptionType, String instanceid);
    public String getSOTNResourceInformationTopology(String subscriptionType, String instanceid) throws Exception;
}
