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
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

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
import org.springframework.stereotype.Service;
import retrofit2.Response;

import java.io.File;
import java.io.FileReader;
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
//        JSONParser parser = new JSONParser();
//        ClassLoader classLoader = new CcvpnCustomerServiceImpl().getClass().getClassLoader();
//        File file = new File(classLoader.getResource("modelconfig.json").getFile());
//        ObjectMapper mapper = new ObjectMapper();
//        try {
//            Object object = parser.parse(new FileReader(file));
//            ModelConfig modelInformation = mapper.readValue(object.toString(), new TypeReference<ModelConfig>() {
//            });
//            return modelInformation;
//        } catch (ParseException | IOException ex) {
//            logger.error("Exception occured while reading configuration file:" + ex);
//            return null;
//        }
//--------------------------------------Temporary Code--------------------------------
        String file = "{\n" +
                "\t\"subscriberId\":\"SOTN-CUST\",\n" +
                "\t\"subscriptionType\":\"SOTN\",\n" +
                "\t\"status\":1,\n" +
                "\t\"modelInformation\":[\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"modelType\":\"sotnservice\",\n" +
                "\t\t\t\t\"model\": {\n" +
                "\t\t\t\t\t\"resourceName\":\"sotn\",\n" +
                "\t\t\t\t\t\"resourceDescription\":\"sotn service\",\n" +
                "\t\t\t\t\t\"resourceInvariantUuid\":\"b36a7816-d4bf-4355-a3f1-20825ee7fce7\",\n" +
                "\t\t\t\t\t\"resourceUuid\":\"68db4561-8d85-4e7e-9653-7263118cb3ef\",\n" +
                "\t\t\t\t\t\"resourceCustomizationUuid\":\"\"\n" +
                "\t\t\t\t\t}\n" +
                "\t\t\t},\n" +
                "{\n" +
                "\t\t\t\t\"modelType\":\"siteservice\",\n" +
                "\t\t\t\t\"model\": {\n" +
                "\t\t\t\t\t\"resourceName\":\"siteservice\",\n" +
                "\t\t\t\t\t\"resourceDescription\":\"sotn service\",\n" +
                "\t\t\t\t\t\"resourceInvariantUuid\":\"5448e42c-a22a-4bbd-bd24-387db52d3e58\",\n" +
                "\t\t\t\t\t\"resourceUuid\":\"0bf85631-6d70-471c-b97c-b5cc05056034\",\n" +
                "\t\t\t\t\t\"resourceCustomizationUuid\":\"\"\n" +
                "\t\t\t\t\t}\n" +
                "\t\t\t},\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"modelType\":\"site\",\n" +
                "\t\t\t\t\"model\": {\n" +
                "\t\t\t\t\t\"resourceName\":\"SiteVF\",\n" +
                "\t\t\t\t\t\"resourceDescription\":\"site 0\",\n" +
                "\t\t\t\t\t\"resourceInvariantUuid\":\"66b96cb7-541d-4340-9bd6-8a5e37f1d5f1\",\n" +
                "\t\t\t\t\t\"resourceUuid\":\"60504c1f-8c3a-42c3-b071-1f1750688f3a\",\n" +
                "\t\t\t\t\t\"resourceCustomizationUuid\":\"c0f4205b-3101-41cf-90b3-aae6d97b3576\"\n" +
                "\t\t\t\t\t}\n" +
                "\t\t\t},\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"modelType\":\"connectivity\",\n" +
                "\t\t\t\t\"model\": {\n" +
                "\t\t\t\t\t\"resourceName\":\"sotnconnectivityv2 0\",\n" +
                "\t\t\t\t\t\"resourceDescription\":\"sotnconnectivityv2 0\",\n" +
                "\t\t\t\t\t\"resourceInvariantUuid\":\"fea25ffc-f87c-418d-a33b-6ddfbf1d014d\",\n" +
                "\t\t\t\t\t\"resourceUuid\":\"d5dad832-e337-4b33-8faa-6d3dfb41c7fb\",\n" +
                "\t\t\t\t\t\"resourceCustomizationUuid\":\"67053d18-4350-48e9-9c11-2785605fb3bb\"\n" +
                "\t\t\t\t\t}\n" +
                "\t\t\t},\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"modelType\":\"partner\",\n" +
                "\t\t\t\t\"model\": {\n" +
                "\t\t\t\t\t\"resourceName\":\"SPpartnerVF\",\n" +
                "\t\t\t\t\t\"resourceDescription\":\"spPartnerv2 0\",\n" +
                "\t\t\t\t\t\"resourceInvariantUuid\":\"0b0f5ceb-4235-4422-8be1-2ea48ddcd283\",\n" +
                "\t\t\t\t\t\"resourceUuid\":\"180fcfa0-4eda-4038-916b-1c69b1723395\",\n" +
                "\t\t\t\t\t\"resourceCustomizationUuid\":\"f6d84644-ce0d-4445-a52c-28361f73439f\"\n" +
                "\t\t\t\t\t}\n" +
                "\t\t\t},\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"modelType\":\"attachment\",\n" +
                "\t\t\t\t\"model\": {\n" +
                "\t\t\t\t\t\"resourceName\":\"elinesotnattachmentvf0\",\n" +
                "\t\t\t\t\t\"resourceDescription\":\"spPartnerv2 0\",\n" +
                "\t\t\t\t\t\"resourceInvariantUuid\":\"4aa1e3ee-8318-4214-b2e8-29e7bc7528ce\",\n" +
                "\t\t\t\t\t\"resourceUuid\":\"2ca95a2b-b034-4e7e-97b5-636c21681652\",\n" +
                "\t\t\t\t\t\"resourceCustomizationUuid\":\"7f35f47b-3f60-41db-ab44-9ff89917d247\"\n" +
                "\t\t\t\t\t}\n" +
                "\t\t\t}]\t\n" +
                "}\n";
        try {
            JSONParser parser = new JSONParser();
            ObjectMapper mapper = new ObjectMapper();
            logger.info("successfully  read hardcode the file");
            Object object = parser.parse(file);
            ModelConfig modelInformation = mapper.readValue(object.toString(), new TypeReference<ModelConfig>() {
            });
            logger.info("convert json to object successfully.");
            return modelInformation;
        } catch (Exception ex) {
            logger.error("Exception occured while reading configuration file:" + ex);
            return null;
        }
    }
}
