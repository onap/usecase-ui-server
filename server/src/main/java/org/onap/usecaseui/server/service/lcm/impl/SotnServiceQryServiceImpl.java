/**
 * Copyright 2020 Huawei Corporation.
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

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.ResponseBody;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.onap.usecaseui.server.bean.activateEdge.ServiceInstance;
import org.onap.usecaseui.server.bean.lcm.sotne2eservice.ModelConfig;
import org.onap.usecaseui.server.bean.lcm.sotne2eservicemonitor.ResponseServiceInstanceWrapper;
import org.onap.usecaseui.server.bean.lcm.sotne2eservicemonitor.ServiceInstanceList;
import org.onap.usecaseui.server.bean.lcm.sotne2eservicemonitor.ServiceInstanceListWrapper;
//import org.onap.usecaseui.server.bean.lcm.sotne2eservicemonitor.ServiceSubscriptionWrapper;
import org.onap.usecaseui.server.service.lcm.SotnServiceQryService;
import org.onap.usecaseui.server.service.lcm.domain.aai.AAIService;
import org.onap.usecaseui.server.util.RestfulServices;
import org.onap.usecaseui.server.util.UuiCommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Service;
import retrofit2.Response;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service("SotnServiceQry")
@org.springframework.context.annotation.Configuration
@EnableAspectJAutoProxy
public class SotnServiceQryServiceImpl implements SotnServiceQryService {

    private static final Logger logger = LoggerFactory.getLogger(SotnServiceQryServiceImpl.class);

    private AAIService aaiService;

    public SotnServiceQryServiceImpl() {
        this(RestfulServices.create(AAIService.class));
    }

    public SotnServiceQryServiceImpl(AAIService aaiService) {
        this.aaiService = aaiService;
    }

    @Override
    public String getServiceInstances(String serviceType) {
        ModelConfig modelConfig = readFile();
        String customerid = modelConfig.getSubscriberId();
        List<String> result = new ArrayList<>();

        //serviceType = "generic";
        ObjectMapper mapper = new ObjectMapper();
        JSONParser parser = new JSONParser();

        try {
            Response<ResponseBody> response = aaiService.listServiceInstances(customerid, serviceType).execute();
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
        JSONParser parser = new JSONParser();
        String jsonPath = "/home/root1/Desktop/modelconfig.json";
        String jsonString = null;
        ObjectMapper mapper = new ObjectMapper();

        try {

            Object object = parser.parse(new FileReader(jsonPath));
            //System.out.println(object.toString());
            ModelConfig modelInformation = mapper.readValue(object.toString(), new TypeReference<ModelConfig>() {
            });

            return modelInformation;
        } catch (ParseException | IOException ex) {
            logger.error("Exception occured while reading configuration file:" + ex);
            return null;
        }
    }
}
