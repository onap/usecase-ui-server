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

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.onap.usecaseui.server.service.lcm.ServiceInstanceService;
import org.onap.usecaseui.server.service.lcm.domain.aai.AAIService;
import org.onap.usecaseui.server.service.lcm.domain.aai.bean.ServiceInstanceRsp;
import org.onap.usecaseui.server.service.lcm.domain.aai.exceptions.AAIException;
import org.onap.usecaseui.server.util.RestfulServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Service;

import okhttp3.ResponseBody;
import retrofit2.Response;

@Service("ServiceInstanceService")
@org.springframework.context.annotation.Configuration
@EnableAspectJAutoProxy
public class DefaultServiceInstanceService implements ServiceInstanceService {

    private static final Logger logger = LoggerFactory.getLogger(DefaultServiceInstanceService.class);

    private AAIService aaiService;

    public DefaultServiceInstanceService() {
        this(RestfulServices.create(AAIService.class));
    }

    public DefaultServiceInstanceService(AAIService aaiService) {
        this.aaiService = aaiService;
    }

    @Override
    public List<String> listServiceInstances(String customerId, String serviceType) {
        try {
            Response<ServiceInstanceRsp> response = aaiService.listServiceInstances(customerId, serviceType).execute();
            if (response.isSuccessful()) {
                return response.body().getServiceInstances();
            } else {
                logger.info(String.format("Can not get service instances[code=%s, message=%s]", response.code(), response.message()));
                return Collections.emptyList();
            }
        } catch (IOException e) {
            logger.error("list services instances occur exception");
            throw new AAIException("AAI is not available.", e);
        }
    }

	@Override
	public String getRelationShipData(String customerId, String serviceType, String serviceId) {
        try {
            Response<ResponseBody> response = aaiService.getAAIServiceInstance(customerId, serviceType,serviceId).execute();
            if (response.isSuccessful()) {
            	String result=new String(response.body().bytes());
                return result;
            } else {
                logger.info(String.format("Can not get service instances[code=%s, message=%s]", response.code(), response.message()));
                return "";
            }
        } catch (IOException e) {
            logger.error("list services instances occur exception");
            throw new AAIException("AAI is not available.", e);
        }
	}
}
