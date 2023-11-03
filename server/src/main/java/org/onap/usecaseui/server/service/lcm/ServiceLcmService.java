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

import org.onap.usecaseui.server.bean.ServiceBean;
import org.onap.usecaseui.server.bean.ServiceInstanceOperations;
import org.onap.usecaseui.server.service.lcm.domain.so.bean.DeleteOperationRsp;
import org.onap.usecaseui.server.service.lcm.domain.so.bean.OperationProgressInformation;
import org.onap.usecaseui.server.service.lcm.domain.so.bean.SaveOrUpdateOperationRsp;
import org.onap.usecaseui.server.service.lcm.domain.so.bean.ServiceOperation;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

public interface ServiceLcmService {

    ServiceOperation instantiateService(HttpServletRequest request);

    OperationProgressInformation queryOperationProgress(String serviceId, String operationId);

    DeleteOperationRsp terminateService(String serviceId, HttpServletRequest request);
    
    SaveOrUpdateOperationRsp scaleService(String serviceId,HttpServletRequest request);
    
    SaveOrUpdateOperationRsp updateService(String serviceId,HttpServletRequest request);
    
    void saveOrUpdateServiceBean(ServiceBean serviceBean);
    
    void updateServiceInstanceStatusById(String status,String serviceInstanceId);
    
    ServiceBean getServiceBeanByServiceInStanceId(String serviceInstanceId);
    
    List<String> getServiceInstanceIdByParentId(String serviceInstanceId);
    
    void saveOrUpdateServiceInstanceOperation(ServiceInstanceOperations serviceOperation);
    
    void updateServiceInstanceOperation(String serviceInstanceId,String operationType,String progress,String operationResult);
    
    ServiceInstanceOperations getServiceInstanceOperationById(String serviceId);

    List<ServiceBean>getAllServiceBean();
}
