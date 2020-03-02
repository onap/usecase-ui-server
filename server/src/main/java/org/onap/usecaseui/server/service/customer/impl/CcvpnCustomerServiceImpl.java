
/*
 * Copyright (C) 2017 CMCC, Inc. and others. All rights reserved.
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
        } catch (Exception e)
        {
            logger.info("Execute get all service for customer : Failed");
        }
        return null;
    }


    public String querySubscriptionType(String customerId) {
        SubscriptionType subscriptions = new SubscriptionType();
        String result = "";
        ModelConfig modelConfig = readFile();
        Map<String, Model> modelInfo = readConfigToMap(modelConfig);
        customerId = modelConfig.getSubscriberId();
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
        return "{\"subscriptions\":[{\"serviceType\":\"SOTN\"}]}";
        //return subscriptions.toString();
    }



    private Map<String, Model> readConfigToMap(ModelConfig modelConfig)
    {

        //ModelConfig modelConfig = readFile();
//        Map<String, Model> modelinfo = modelConfig.getModels().stream()
//                .collect(Collectors.toMap(ModelInfor::getModelType, ModelInfor::getModel));
        return null;
    }

    public ModelConfig readFile()
    {
        JSONParser parser = new JSONParser();
        String jsonPath="/home/root1/Desktop/modelconfig.json";
        String jsonString = null;
        ObjectMapper mapper = new ObjectMapper();

        try {

            Object object = parser.parse(new FileReader(jsonPath));
            //System.out.println(object.toString());
            ModelConfig modelInformation = mapper.readValue(object.toString(), new TypeReference<ModelConfig>() {
            });

            return modelInformation;
        }catch (ParseException | IOException ex)
        {
            logger.error("Exception occured while reading configuration file:"+ex);
            return null;
        }
    }


/*
    @Override
    public String getServiceInformation(String serviceInstanceId) throws Exception{
        //serviceInstanceId = "106";
        String result = "";
        ObjectMapper mapper = new ObjectMapper();
        List<SiteTopologyBean> beanList = new ArrayList<>();
        CostInformation costInformation = getCostInformation();
        List<SiteCost> siteCost = costInformation.getSiteCost();

        String path ="/home/root1/Desktop/SystemConfiguration.json";
        StaticConfiguration staticConfig = ReadStaticConfiguration(path);

        String customerId = staticConfig.getSystemInformation().getCustomerName();
        String serviceType = staticConfig.getSystemInformation().getServiceType();



        List<Relationship> perfectrelationship = new ArrayList<>();

        ServiceInstance serviceInstance=null;

        //----------------------------- GET SERVICE INSTANCE INFORMATION FROM AAI : BEGIN ---------------------------------------
        try{
            serviceInstance=getServiceInstancesForEdge(customerId, serviceType, serviceInstanceId);
            if(serviceInstance == null)
                return null;
        }catch (Exception e)
        {
            logger.info("Query Service Instance information failed. No service information found for customer "
                    +customerId+" and Service Type "+serviceType);
            return null;
        }
        //----------------------------- GET SERVICE INSTANCE INFORMATION FROM AAI : END -----------------------------------------

        //----------------------------- Get the detailed information of Generic VNF from the service instance and filter the site resource : BEGIN ---------------------------------------

        RelationshipList genericvnfrelationshipList = serviceInstance.getRelationshipList();
        if(genericvnfrelationshipList == null)
        {
            logger.info("No VNF associated with the service instance : "+serviceInstanceId);
            return null;
        }
        List<Relationship> genericRelationship = genericvnfrelationshipList.getRelationship();
        List<String> resourceurl = new ArrayList<>();
        for(Relationship tempRelation : genericRelationship) {
            String relatedLink = tempRelation.getRelatedLink();
            resourceurl.add(tempRelation.getRelatedLink());
        }


        // Need a code change here to identify VPN VNF and Site resource VNF.

        String vnfID = new String(resourceurl.get(0).substring(resourceurl.get(0).lastIndexOf("/")+1));



        GenericVnf genericVnf = getGenericVnfInfo(vnfID);

        RelationshipList newrelationshipList = genericVnf.getRelationshipList();

        perfectrelationship = newrelationshipList.getRelationship().stream().filter(x -> x.getRelatedTo()
                .equalsIgnoreCase("site-resource")
                || x.getRelatedTo().equalsIgnoreCase("device")
                ||x.getRelatedTo().equalsIgnoreCase("edge-camera")).collect(Collectors.toList());



        //----------------------------- Get the detailed information of Generic VNF from the service instance and filter the site resource : END ---------------------------------------


        //----------------------------- Get the VNF information from the service instance and filter the site resource : END ---------------------------------------


        for (Relationship relationship :perfectrelationship)
        {
            SiteTopologyBean bean = new SiteTopologyBean();
            bean.setEdgeStatus("Offline");
            //Relationship relationship = itr.next();
            if (relationship.getRelatedTo().equalsIgnoreCase("site-resource")) {
                // String relatedLink = relationship.getRelatedLink();
                String siteResourceId = relationship.getRelationshipData().get(0).getRelationshipValue();
                Response<ResponseBody> rsp = this.aaiService.getSiteResourceInfo(siteResourceId).execute();
                if (rsp.isSuccessful()) {
                    result = new String(rsp.body().bytes());

                    SiteResource siteResource = mapper.readValue(result, SiteResource.class);
                    String siteResourceName = siteResource.getSiteResourceName();
                    bean.setSiteStatus(siteResource.getOperationalStatus());
                    String role = siteResource.getRole();
                    bean.setRole(role);
                    String desc = siteResource.getDescription();
                    bean.setDescription(desc);
                    bean.setSiteName(siteResourceName);
                    bean.setSiteId(siteResourceId);
                    bean.setZipCode(siteResource.getPostalcode());
                    bean.setLocation(siteResource.getCity());
                    /*
                    List<Relationship> rList = siteResource.getRelationshipList().getRelationship();
                    ListIterator<Relationship> itr1 = rList.listIterator();
                    while (itr1.hasNext()) {
                        Relationship relationship1 = itr1.next();
                        if (relationship1.getRelatedTo().equalsIgnoreCase("complex")) {
                            String complexId = relationship1.getRelationshipData().get(0).getRelationshipValue();
                            Response<ResponseBody> compResp = this.aaiService.getComplexObject(complexId).execute();
                            if (compResp.isSuccessful()) {
                                result = new String(compResp.body().bytes());
                                ComplexObj complexObj = mapper.readValue(result, ComplexObj.class);
                                String zipCode = complexObj.getPostalCode();
                                bean.setZipCode(zipCode);
                                String location = complexObj.getCity();
                                bean.setLocation(location);
                            }
                        }
                    }
                    */
                /*}
            }

            if (relationship.getRelatedTo().equalsIgnoreCase("device")) {
                String deviceId = relationship.getRelationshipData().get(0).getRelationshipValue();
                Map<String,String> resultMaps = getDeviceStatus(deviceId);
                String deviceDesc = resultMaps.get("deviceDesc");
                String deviceName = resultMaps.get("deviceName");
                bean.setDeviceName(deviceName);
                bean.setStatus(resultMaps.get("status"));
                bean.setDeviceDesc(deviceDesc);
            }

            if (relationship.getRelatedTo().equalsIgnoreCase("edge-camera")) {
                String edgeCamId = relationship.getRelationshipData().get(0).getRelationshipValue();
                Response<ResponseBody> edgeRsp = this.aaiService.getEdgeInfo(edgeCamId).execute();
                if(edgeRsp.isSuccessful())
                {
                    result = new String(edgeRsp.body().bytes());
                    EdgeCamera edgeInfo = mapper.readValue(result, EdgeCamera.class);
                    String edgeName = edgeInfo.getEdgeCameraName();
                    String edgeIP = edgeInfo.getIpAddress();
                    bean.setEdgeName(edgeName);
                    bean.setEdgeIP(edgeIP);
                    bean.setEdgeStatus("Online");
                }
            }
            if(bean.getStatus()!=null && bean.getEdgeStatus()!=null) {
                Enum value = generateEnumValue(bean.getStatus(), bean.getEdgeStatus());
                String enumVal = value.name();
                bean.setStatEnum(enumVal);
            }

            if(bean.getRole()!=null) {
                if (bean.getRole().toLowerCase().contains("hub")) {
                    String price = siteCost.stream().filter(cost -> cost.getSiteType().equalsIgnoreCase("1"))
                            .map(SiteCost::getSiteCost).collect(Collectors.toList()).get(0);
                    bean.setPrice(price);

                } else {
                    String price = siteCost.stream().filter(cost -> cost.getSiteType().equalsIgnoreCase("0"))
                            .map(SiteCost::getSiteCost).collect(Collectors.toList()).get(0);
                    bean.setPrice(price);
                }
                bean.setSiteinterface(staticConfig.getSiteInformation().getSiteInterface());
                bean.setSubnet(staticConfig.getSiteInformation().getSubnetMask());
                beanList.add(bean);
            }
        }
        return beanList.toString();
    }


    public CostInformation getCostInformation() {
        JSONParser parser = new JSONParser();
        String jsonPath = "/home/root1/Desktop/costconfig.json";
        String jsonString = null;
        ObjectMapper mapper = new ObjectMapper();

        try {

            Object object = parser.parse(new FileReader(jsonPath));
            //System.out.println(object.toString());
            CostInformation costInformation = mapper.readValue(object.toString(), new TypeReference<CostInformation>() {
            });

            return costInformation;
        } catch (ParseException | IOException ex) {
            logger.error("getDataMonitorInfo exception occured:" + ex);
            return null;
        }
    }


    public StaticConfiguration ReadStaticConfiguration(String path)
    {
        JSONParser parser = new JSONParser();

        String jsonString = null;
        ObjectMapper mapper = new ObjectMapper();

        try {

            Object object = parser.parse(new FileReader(path));
            //System.out.println(object.toString());
            StaticConfiguration staticConfiguration = mapper.readValue(object.toString(), new TypeReference<StaticConfiguration>() {
            });

            return staticConfiguration;
        }catch (ParseException | IOException ex)
        {
            logger.error("Reading static information from configuration file (StaticConfiguration.json) failed:"+ex.getMessage());
            return null;
        }
    }

    public ServiceInstance getServiceInstancesForEdge(String customerId, String serviceType, String serviceInstanceId) throws Exception
    {
        logger.info("Fire getServiceInstancesForEdge : Begin");
        ObjectMapper mapper = new ObjectMapper();

        Response<ResponseBody> response = this.aaiService.getServiceInstancesForEdge(customerId, serviceType, serviceInstanceId).execute();
        if (response.isSuccessful()) {
            logger.info("Fire getServiceInstancesForEdge : End");
            String result = new String(response.body().bytes());
            ServiceInstance serviceInstance = mapper.readValue(result, ServiceInstance.class);
            return serviceInstance;
            //System.out.println("Response received : "+response.body().bytes());
        }
        else
        {
            logger.info("Fire getServiceInstancesForEdge : Failed");

        }

        return null;
    }

    public GenericVnf getGenericVnfInfo(String vnfID) throws Exception
    {
        ObjectMapper mapper = new ObjectMapper();
        logger.info("Fire GetGenericVnfInfo : Begin for vnfID"+vnfID);
        Response<ResponseBody> deviceResponse = this.aaiService.getGenericVnfInformation(vnfID).execute();
        if (deviceResponse.isSuccessful()) {
            String result = new String(deviceResponse.body().bytes());
            logger.info("Response received GetGenericVnfInfo");
            //result = result.substring(10, result.length() - 1);
            GenericVnf genericVnf = mapper.readValue(result, new TypeReference<GenericVnf>() {
            });
            return genericVnf;

        } else {
            logger.info("Fire GetGenericVnfInfo : Failed");
        }
        return null;
    }

    private Map<String,String> getDeviceStatus(String deviceId) {
        String result = "";
        String status = "";
        Map<String,String> resultMap = new HashMap<>();
        String deviceDesc = "";
        String deviceName = "";
        ObjectMapper mapper = new ObjectMapper();

        try {
            logger.info("aai getDeviceStatus is starting!");

            Response<ResponseBody> response = this.aaiService.getDeviceInfo(deviceId).execute();


            logger.info("aai getDeviceStatus has finished!");
            if (response.isSuccessful()) {
                result = new String(response.body().bytes());
                DeviceInfo deviceInfo = mapper.readValue(result, new TypeReference<DeviceInfo>() {
                });
                status = deviceInfo.getOperationalStatus();
                deviceDesc = deviceInfo.getDescription();
                deviceName = deviceInfo.getDeviceName();
                if (status.isEmpty()) {
                    status = "activate";
                }
                resultMap.put("status",status);
                resultMap.put("deviceDesc",deviceDesc);
                resultMap.put("deviceName",deviceName);
            } else {
                logger.info(String.format("Can not get getDeviceStatus[code=%s, message=%s]", response.code(), response.message()));
                result = Constant.CONSTANT_FAILED;
            }
        } catch (IOException e) {

            logger.error("getDeviceStatus exception occured:" + e);
            result = Constant.CONSTANT_FAILED;
        }
        return resultMap;
    }

    private Enum generateEnumValue(String deviceStatus, String edgeStatus) {
        StatusEnum value = null;
        if(edgeStatus.equalsIgnoreCase("Online") && deviceStatus.equalsIgnoreCase("Online"))
        {
            value = StatusEnum.GREEN_GREEN;
        }
        else if(edgeStatus.equalsIgnoreCase("Offline") && deviceStatus.equalsIgnoreCase("Online"))
        {
            value = StatusEnum.GREEN_GREY;
        }
        else
        {
            value = StatusEnum.GREY_GREY;
        }
        return value;
    }

    */

}
