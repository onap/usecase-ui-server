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
package org.onap.usecaseui.server.service.customer.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import okhttp3.ResponseBody;

import org.onap.usecaseui.server.bean.customer.ServiceInstance;
import org.onap.usecaseui.server.bean.customer.ServiceInstances;
import org.onap.usecaseui.server.bean.customer.SubscriptionType;
import org.onap.usecaseui.server.bean.lcm.sotne2eservice.Model;
import org.onap.usecaseui.server.bean.lcm.sotne2eservice.ModelConfig;

import org.onap.usecaseui.server.constant.Constant;
import org.onap.usecaseui.server.service.customer.CcvpnCustomerService;
import org.onap.usecaseui.server.service.lcm.domain.aai.AAIService;
import org.onap.usecaseui.server.util.RestfulServices;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import retrofit2.Response;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


@Service("CcvpnCustomerService")
public class CcvpnCustomerServiceImpl implements CcvpnCustomerService {

    private static final Logger logger = LoggerFactory.getLogger(CcvpnCustomerServiceImpl.class);

    private AAIService aaiService;

    public CcvpnCustomerServiceImpl() {
        this(RestfulServices.create(AAIService.class));
    }

    public CcvpnCustomerServiceImpl(AAIService aaiService) {
        this.aaiService = aaiService;
    }

    @Override
    public ServiceInstances getServiceInstances(String customerId, String serviceType) {

        List<ServiceInstance> serviceInstanceList = new ArrayList<>();
        ModelConfig modelConfig = readFile();
        Map<String, Model> modelInfo = readConfigToMap(modelConfig);
        customerId = modelConfig.getSubscriberId();
        List<ServiceInstance> ccvpnServiceInstance = new ArrayList<>();
        ServiceInstances serviceInstance = getAllServiceInstances(customerId, serviceType);
        return serviceInstance;
    }


    public ServiceInstances getAllServiceInstances(String customerId, String serviceType) {
        logger.info("Execute get all service for customer : Begin");
        ModelConfig modelConfig = readFile();
        Map<String, Model> modelInfo = readConfigToMap(modelConfig);
        customerId = modelConfig.getSubscriberId();
        ObjectMapper mapper = new ObjectMapper();
        ServiceInstances serviceInstances = null;
        try {
            Response<ResponseBody> response = this.aaiService.getAllServiceInformation(customerId, serviceType).execute();
            if (response.isSuccessful()) {
                logger.info("Execute get all service for customer : End");
                String result = new String(response.body().bytes());
                serviceInstances = mapper.readValue(result, new TypeReference<ServiceInstances>() {
                });
                return serviceInstances;
                //System.out.println("Response received : "+response.body().bytes());
            } else {
                logger.info("Execute get all service for customer : Failed");

            }
        } catch (Exception e) {
            logger.info("Execute get all service for customer : Failed");
        }
        return null;
    }


    public String querySubscriptionType() {
        SubscriptionType subscriptions = new SubscriptionType();
        String result = "";
        ModelConfig modelConfig = readFile();
        Map<String, Model> modelInfo = readConfigToMap(modelConfig);
        String customerId = modelConfig.getSubscriberId();
        ObjectMapper mapper = new ObjectMapper();
        try {
            logger.info("aai querySubscriptionType is starting!");

            Response<ResponseBody> response = this.aaiService.getServiceSubscription(customerId).execute();
            logger.info("aai querySubscriptionType has finished!");
            if (response.isSuccessful()) {
                result = new String(response.body().bytes());
                subscriptions = mapper.readValue(result, SubscriptionType.class);

            } else {
                // logger.info(String.format("Failed to get data from AAI[code=%s, message=%s]", response.code(), response.message()));
                result = Constant.CONSTANT_FAILED;
                throw new NullPointerException("Failed to get data from AAI");
            }

        } catch (Exception ex) {

            logger.error("getServiceSubscription exception occured:" + ex);
            result = Constant.CONSTANT_FAILED;
        }
	logger.info("getServiceSubscription:" + subscriptions.toString());
        return subscriptions.toString();
    }


    private Map<String, Model> readConfigToMap(ModelConfig modelConfig) {

        //ModelConfig modelConfig = readFile();
//        Map<String, Model> modelinfo = modelConfig.getModels().stream()
//                .collect(Collectors.toMap(ModelInfor::getModelType, ModelInfor::getModel));
        return null;
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
