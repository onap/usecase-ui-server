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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import okhttp3.ResponseBody;
import org.onap.usecaseui.server.bean.activateEdge.ServiceInstance;
import org.onap.usecaseui.server.bean.lcm.sotne2eservice.ModelConfig;
import org.onap.usecaseui.server.bean.lcm.sotne2eservicemonitor.ResponseServiceInstanceWrapper;
import org.onap.usecaseui.server.bean.lcm.sotne2eservicemonitor.ServiceInstanceList;
import org.onap.usecaseui.server.bean.lcm.sotne2eservicemonitor.ServiceInstanceListWrapper;
import org.onap.usecaseui.server.service.lcm.SotnServiceQryService;
import org.onap.usecaseui.server.service.lcm.domain.aai.AAIClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import retrofit2.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service("SotnServiceQry")
public class SotnServiceQryServiceImpl implements SotnServiceQryService {

    private static final Logger logger = LoggerFactory.getLogger(SotnServiceQryServiceImpl.class);

    private final AAIClient aaiClient;

    @Override
    public String getServiceInstances(String serviceType) {
        ModelConfig modelConfig = readFile();
        String customerid = modelConfig.getSubscriberId();
        List<String> result = new ArrayList<>();

        //serviceType = "generic";
        ObjectMapper mapper = new ObjectMapper();

        try {
            Response<ResponseBody> response = aaiClient.listServiceInstances(customerid, serviceType).execute();
            if (response.isSuccessful()) {
                String resultStr=new String(response.body().bytes());
                ServiceInstanceListWrapper serviceInstances = mapper.readValue(resultStr, new TypeReference<ServiceInstanceListWrapper>() {
                    });
                List<ServiceInstanceList> serviceIntances = new ArrayList<>();
              for(ServiceInstance ServiceInstance : serviceInstances.getServiceIntances()) {
                  if(!ServiceInstance.getServiceInstanceName().contains("Site_Service_")){
                      ServiceInstanceList serviceInstanceList = new ServiceInstanceList();
                      serviceInstanceList.setServiceInstance(ServiceInstance.getServiceInstanceId());
                      serviceInstanceList.setServiceInstancename(ServiceInstance.getServiceInstanceName());
                      serviceIntances.add(serviceInstanceList);
                  }
                }
                ResponseServiceInstanceWrapper responseServiceInstanceWrapper= new ResponseServiceInstanceWrapper();
                responseServiceInstanceWrapper.setServiceInstanceListList(serviceIntances);
                return responseServiceInstanceWrapper.toString();
                }

            else {
                logger.info(String.format("Can not get service instances[code=%s, message=%s]", response.code(), response.message()));
                return null;
            }
        } catch (IOException e) {
            logger.error("list services instances occur exception"+e.getMessage());
            return null;
        }
    }

    public ModelConfig readFile() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Resource resource = new ClassPathResource("modelconfig.json");
            ModelConfig modelInformation = mapper.readValue(resource.getInputStream(), ModelConfig.class);
            logger.info("subscriber id is: {}.", modelInformation.getSubscriberId());
            return modelInformation;
        } catch (IOException ex) {
            logger.error("Exception occured while reading configuration file: {}", ex);
            return null;
        }
    }
}
