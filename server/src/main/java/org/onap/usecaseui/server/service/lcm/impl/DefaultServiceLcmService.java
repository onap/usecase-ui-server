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

import org.onap.usecaseui.server.service.lcm.ServiceLcmService;
import org.onap.usecaseui.server.service.lcm.domain.so.SOService;
import org.onap.usecaseui.server.service.lcm.domain.so.bean.OperationProgressInformation;
import org.onap.usecaseui.server.service.lcm.domain.so.bean.ServiceInstantiationRequest;
import org.onap.usecaseui.server.service.lcm.domain.so.bean.ServiceOperation;
import org.onap.usecaseui.server.service.lcm.domain.so.exceptions.SOException;
import org.onap.usecaseui.server.util.RestfulServices;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service("ServiceLcmService")
@org.springframework.context.annotation.Configuration
@EnableAspectJAutoProxy
public class DefaultServiceLcmService implements ServiceLcmService {

    private SOService soService;

    public DefaultServiceLcmService() {
        this(RestfulServices.create(SOService.class));
    }

    public DefaultServiceLcmService(SOService soService) {
        this.soService = soService;
    }

    @Override
    public ServiceOperation instantiateService(ServiceInstantiationRequest request) {
        try {
            return soService.instantiateService(request).execute().body();
        } catch (IOException e) {
            throw new SOException("SO Service is not available!", e);
        }
    }

    @Override
    public OperationProgressInformation queryOperationProgress(String serviceId, String operationId) {
        try {
            return soService.queryOperationProgress(serviceId, operationId).execute().body();
        } catch (IOException e) {
            throw new SOException("SO Service is not available!", e);
        }
    }

    @Override
    public ServiceOperation terminateService(String serviceId) {
        try {
            return soService.terminateService(serviceId).execute().body();
        } catch (IOException e) {
            throw new SOException("SO Service is not available!", e);
        }
    }
}
