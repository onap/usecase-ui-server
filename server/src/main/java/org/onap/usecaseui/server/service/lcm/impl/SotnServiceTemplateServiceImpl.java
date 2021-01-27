/**
 * Copyright (C) 2020 Huawei, Inc. and others. All rights reserved.
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
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Setter;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import org.onap.usecaseui.server.bean.activateEdge.ComplexObj;
import org.onap.usecaseui.server.bean.activateEdge.ServiceInstance;
import org.onap.usecaseui.server.bean.activateEdge.ServiceInstantiationResponse;
import org.onap.usecaseui.server.bean.activateEdge.SiteResource;
import org.onap.usecaseui.server.bean.lcm.sotne2eservice.AllottedResource;
import org.onap.usecaseui.server.bean.lcm.sotne2eservice.Connectivity;
import org.onap.usecaseui.server.bean.lcm.sotne2eservice.E2EParameters;
import org.onap.usecaseui.server.bean.lcm.sotne2eservice.E2EService;
import org.onap.usecaseui.server.bean.lcm.sotne2eservice.E2EServiceDelete;
import org.onap.usecaseui.server.bean.lcm.sotne2eservice.E2EServiceInstanceRequest;
import org.onap.usecaseui.server.bean.lcm.sotne2eservice.Edge;
import org.onap.usecaseui.server.bean.lcm.sotne2eservice.LogicalLink;
import org.onap.usecaseui.server.bean.lcm.sotne2eservice.Model;
import org.onap.usecaseui.server.bean.lcm.sotne2eservice.ModelConfig;
import org.onap.usecaseui.server.bean.lcm.sotne2eservice.ModelInfor;
import org.onap.usecaseui.server.bean.lcm.sotne2eservice.Node;
import org.onap.usecaseui.server.bean.lcm.sotne2eservice.Pinterface;
import org.onap.usecaseui.server.bean.lcm.sotne2eservice.Pnf;
import org.onap.usecaseui.server.bean.lcm.sotne2eservice.ResourceRequest;
import org.onap.usecaseui.server.bean.lcm.sotne2eservice.ResourceResponse;
import org.onap.usecaseui.server.bean.lcm.sotne2eservice.Uni;
import org.onap.usecaseui.server.bean.lcm.sotne2eservice.Vnfs;
import org.onap.usecaseui.server.bean.lcm.sotne2eservice.VpnBinding;
import org.onap.usecaseui.server.bean.lcm.sotne2eservice.VpnInformation;
import org.onap.usecaseui.server.bean.orderservice.ServiceEstimationBean;
import org.onap.usecaseui.server.service.lcm.SotnServiceTemplateService;
import org.onap.usecaseui.server.service.lcm.domain.aai.AAIService;
import org.onap.usecaseui.server.service.lcm.domain.aai.bean.Relationship;
import org.onap.usecaseui.server.service.lcm.domain.aai.bean.RelationshipData;
import org.onap.usecaseui.server.service.lcm.domain.so.SOService;
import org.onap.usecaseui.server.service.lcm.domain.so.bean.DeleteOperationRsp;
import org.onap.usecaseui.server.service.lcm.domain.so.bean.Operation;
import org.onap.usecaseui.server.service.lcm.domain.so.bean.ServiceOperation;
import org.onap.usecaseui.server.service.lcm.domain.so.exceptions.SOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import retrofit2.Response;

import javax.servlet.ServletInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("SotnLcmService")
@org.springframework.context.annotation.Configuration
@EnableAspectJAutoProxy
@Setter
public class SotnServiceTemplateServiceImpl implements SotnServiceTemplateService {

    private static final Logger logger = LoggerFactory.getLogger(SotnServiceTemplateServiceImpl.class);

    private SOService soService;
    private AAIService aaiService;




    private Map<String, Model> readConfigToMap(ModelConfig modelConfig) {

        //ModelConfig modelConfig = readFile();
//        Map<String, Model> modelinfo = modelConfig.getResourcemodelinformation().stream()
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
    public ModelConfig readFile_unni(){
        ObjectMapper mapper = new ObjectMapper();
        try {
            Resource resource = new ClassPathResource("modelconfigunni.json");
            ModelConfig modelInformation = mapper.readValue(resource.getInputStream(), ModelConfig.class);
            logger.info("subscriber id is: {}.", modelInformation.getSubscriberId());
            return modelInformation;
        } catch (IOException ex) {
            logger.error("Exception occured while reading configuration file: {}", ex);
            return null;
        }
    }

    public ServiceOperation instantiate_CCVPN_Service(HashMap<String, Object> reqt) {
        ServletInputStream inStream = null;
        E2EServiceInstanceRequest requestBody = null;
        Response<ServiceOperation> sotnserviceresponse = null;
        Operation sotnservice = null;
        ModelConfig modelConfig = readFile_unni();
        Model servicemodel = modelConfig.getServiceModel().getModel();
        //Map<String, Model> modelInfo = readConfigToMap(modelConfig);
        List<ModelInfor> resourceModel = modelConfig.getResourcemodelinformation();

        String customerid = modelConfig.getSubscriberId();
        String subscriptionType = modelConfig.getSubscriptionType();

        requestBody = create_CCVPN_Request_Body(reqt, resourceModel, customerid, subscriptionType, servicemodel);
        try {
            logger.info("SO request formed : " + new ObjectMapper().writeValueAsString(requestBody));
        } catch (IOException e) {
	    logger.info("IOException : " + e.getMessage());
        }

        ServiceOperation sotnserviceoperation = createSotnService(requestBody);
        sotnservice = sotnserviceoperation.getService();
        logger.info("Began to sleep for ");
        try {
            Thread.sleep(1);
        } catch (Exception e) {
            logger.info("sleep Interrupted");
        }

        logger.info("wokeup to sleep ");
        return sotnserviceoperation;
    }

    public ServiceOperation createSotnService(E2EServiceInstanceRequest requestBody) {
        Operation result = new Operation();
        try {
              logger.info("SO instantiate SOTN service is starting");
            Response<ServiceOperation> sotnserviceresponse = soService.instantiateSOTNService(requestBody).execute();
              logger.info("SO instantiate SOTN service has finished");
            if (sotnserviceresponse.isSuccessful()) {
                logger.info("SO instantiate SOTN service is successful");
                return sotnserviceresponse.body();
            } else {
                logger.error(String.format("Can not instantiate SOTN service[code=%s, message=%s]", sotnserviceresponse.code(), sotnserviceresponse.message()));
                throw new SOException("SO instantiate SOTN service failed!");
            }
        } catch (Exception e) {
            throw new SOException("SO Service is not available!", e);
        }

    }

    private E2EServiceInstanceRequest create_CCVPN_Request_Body(Map request, List<ModelInfor> resourceModel,
                                                                String customerid, String subscriptionType, Model servicemodel  ) {

        E2EServiceInstanceRequest e2eServiceInstanceRequest = new E2EServiceInstanceRequest();
        E2EService e2eService = new E2EService();
        E2EParameters parameters = new E2EParameters();
        e2eServiceInstanceRequest.setService(e2eService);
        e2eService.setName(request.get("name").toString());
        e2eService.setDescription(request.get("description").toString());

        e2eService.setServiceInvariantUuid(servicemodel.getResourceInvariantUuid());         //Need to get from SDC or Configuration
        e2eService.setServiceUuid(servicemodel.getResourceUuid());
        e2eService.setGlobalSubscriberId(customerid);
        e2eService.setServiceType(subscriptionType);
        e2eService.setParameters(parameters);


        // modelInfo.forEach ((k,v) -

        //        Iterator<Map.Entry<String, Model>> it = modelInfo.entrySet().iterator();
        //
        //        while (it.hasNext()) {

        //List<Object> ResourceModelList = modelInfo.get("resourcemodelinformation");
        List<ResourceRequest> resourceList = new LinkedList<ResourceRequest>();
        ResourceRequest resource = null;

        for (ModelInfor singleresourceModel : resourceModel) {


            resource = new ResourceRequest();
            resource.setResourceName(singleresourceModel.getModel().getResourceName());
            resource.setResourceInvariantUuid(singleresourceModel.getModel().getResourceInvariantUuid());
            resource.setResourceCustomizationUuid(singleresourceModel.getModel().getResourceCustomizationUuid());
            resource.setResourceUuid(singleresourceModel.getModel().getResourceInvariantUuid());
            resource.setParameters(new E2EParameters());
            System.out.println(resource);

            resourceList.add(resource);
            System.out.println("listaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
            System.out.println(resourceList);
            e2eService.getParameters().setResources(resourceList);

        }
        //List<ResourceRequest> resourceList = new LinkedList<ResourceRequest>()

        //            System.out.println("listaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        //            System.out.println(resourceList);
        //            e2eService.getParameters().setResources(resourceList);

        //        List<ResourceRequest> resourceList = new LinkedList<ResourceRequest>();
        //        resourceList.add(resource);
        //        e2eService.getParameters().setResources(resourceList);

        HashMap<String, Object> requestInputsmap = new HashMap();
        requestInputsmap.put("l2vpn", request.get("l2vpn"));
        requestInputsmap.put("sotnUni", request.get("sotnUni"));
        e2eService.getParameters().setRequestInputs(requestInputsmap);
        e2eServiceInstanceRequest.setService(e2eService);
        return e2eServiceInstanceRequest;
    }


    public ServiceInstance getServiceInstancesInfo(String customerId, String serviceType, String serviceInstanceId) throws Exception {
        logger.info("Fire getServiceInstances : Begin");
        ObjectMapper mapper = new ObjectMapper();
        Response<ResponseBody> response = this.aaiService.getServiceInstancesForEdge(customerId, serviceType, serviceInstanceId).execute();
        if (response.isSuccessful()) {
            logger.info("Fire getServiceInstances : End");
            String result = new String(response.body().bytes());
            ServiceInstance serviceInstance = mapper.readValue(result, ServiceInstance.class);
            return serviceInstance;
        } else {
            logger.info("Fire getServiceInstances : Failed");
        }
        return null;
    }

    private Connectivity getConnectivityInfo(String connectivityinstanceid) throws IOException {
        logger.info("Fire getServiceInstancesForEdge : Begin");
        ObjectMapper mapper = new ObjectMapper();
        Response<ResponseBody> response = this.aaiService.getConnectivityInformation(connectivityinstanceid).execute();
        if (response.isSuccessful()) {
            logger.info("Fire getServiceInstancesForEdge : End");
            String result = new String(response.body().bytes());
            Connectivity connectivity = mapper.readValue(result, Connectivity.class);
            return connectivity;
        } else {
           logger.info("Fire getServiceInstancesForEdge : Failed");
        }
        return null;
    }

    public Pinterface getTerminationPoint(String pnfName, String tpId) throws Exception {
        logger.info("Fire getTerminationPoint : Begin");
        ObjectMapper mapper = new ObjectMapper();
        // Response<ResponseBody> response = this.aaiService.getPinterfaceByPnfName(pnfName,tpId).execute();
        Response<ResponseBody> response = this.aaiService.getTerminationPoint(pnfName, tpId).execute();
        if (response.isSuccessful()) {
            logger.info("Fire getTerminationPoint : End");
            String result = new String(response.body().bytes());
            Pinterface pinterface = mapper.readValue(result, Pinterface.class);
            return pinterface;
        } else {
            logger.info("Fire getTerminationPoint : Failed");
        }
        return null;
    }
    private AllottedResource getAllottedResource(String globalCustomerId, String serviceType, String siteserviceinstanceid, String allottedResourceId) throws IOException {
        logger.info("Fire getServiceInstancesForEdge : Begin");
        ObjectMapper mapper = new ObjectMapper();
        Response<ResponseBody> response = this.aaiService.getAllotedResourceFor5G(globalCustomerId, serviceType, siteserviceinstanceid, allottedResourceId).execute();
        if (response.isSuccessful()) {
            logger.info("Fire getServiceInstancesForEdge : End");
            String result = new String(response.body().bytes());
            AllottedResource allottedResource = mapper.readValue(result, AllottedResource.class);
            return allottedResource;
        } else {
            logger.info("Fire getServiceInstancesForEdge : Failed");
        }
        return null;
    }

    private SiteResource getSiteResource(String siteResourceID) throws IOException {
        logger.info("Fire get site resource : Begin");
        ObjectMapper mapper = new ObjectMapper();
        Response<ResponseBody> response = this.aaiService.getSiteResourceInfo(siteResourceID).execute();
        if (response.isSuccessful()) {
            logger.info("Fire get site resource : End");
            String result = new String(response.body().bytes());
            SiteResource resource = mapper.readValue(result, SiteResource.class);
            return resource;
        } else {
            logger.info("Fire get site resource : Failed");
        }

        return null;
    }

    private ComplexObj getComplexData(String complexID) throws IOException {
        logger.info("Fire get complex Object : Begin");
        ObjectMapper mapper = new ObjectMapper();
        Response<ResponseBody> response = this.aaiService.getComplexObject(complexID).execute();
        if (response.isSuccessful()) {
            logger.info("Fire get complex Object : End");
            String result = new String(response.body().bytes());
            ComplexObj complexObj = mapper.readValue(result, ComplexObj.class);
            return complexObj;
        } else {
           logger.info("Fire get complex Object : Failed");
        }
        return null;
    }


    public String getSOTNSiteInformationTopology(String subscriptionType, String instanceid) {

        ModelConfig modelConfig = readFile();
        Map<String, Model> modelInfo = readConfigToMap(modelConfig);
        String customerId = modelConfig.getSubscriberId();

        ServiceInstance serviceInstance = null;
        ServiceInstance siteservice = null;
        Map<String, Object> connectivityparams;
        List<String> vpnparams = new ArrayList<String>(Arrays.asList("cir", "eir", "cbs", "ebs", "colorAware", "couplingFlag", "ethtSvcName"));
        List<Map<String, Object>> sites = new LinkedList<Map<String, Object>>();
        String jsonresponse = "";
        String pnfname = "";
        String allottedpinterfaceid = "";
        String linkstatus = "";
        AllottedResource allottedResource = new AllottedResource();

        //----------------------------- GET SERVICE INSTANCE INFORMATION FROM AAI : BEGIN ---------------------------------------
        try {
            serviceInstance = getServiceInstancesInfo(customerId, subscriptionType, instanceid);
            if (serviceInstance == null)
                return null;
        } catch (Exception e) {
            logger.info("Query Service Instance information failed. No service information found for customer "
                    + customerId + " and Service Type " + subscriptionType);
            return null;
        }

        List<Relationship> relationship = serviceInstance.getRelationshipList().getRelationship().stream().filter(relation -> relation.getRelatedTo()
                .equalsIgnoreCase("allotted-resource")).collect(Collectors.toList());
        if (relationship.size() > 0 && relationship != null) {
            // This is SOTN service
            connectivityparams = new HashMap<String, Object>();
            relationship = serviceInstance.getRelationshipList().getRelationship();
            Relationship relation = relationship.stream()
                    .filter(relate -> "connectivity".equalsIgnoreCase(relate.getRelatedTo()))
                    .findAny()
                    .orElse(null);
            try {
                String connectivityinstanceid = relation.getRelatedLink().substring(relation.getRelatedLink().lastIndexOf("/") + 1);
                Connectivity connectivity = getConnectivityInfo(connectivityinstanceid);
                connectivityparams = new ObjectMapper().readValue(connectivity.toString(), HashMap.class);
            } catch (IOException e) {
                logger.info("IO Exception occured " + e.getMessage());
            }
            //nodeId-10.10.10.10-ltpId-147
            allottedpinterfaceid = "nodeId-" + connectivityparams.get("accessNodeId").toString() + "-ltpId-" + connectivityparams.get("accessLtpId").toString();
            pnfname = connectivityparams.get("accessNodeId").toString();
            try {
                Pinterface pinterface = getTerminationPoint(pnfname, allottedpinterfaceid);
                linkstatus = pinterface.getOperationalStatus();
                if (linkstatus.equalsIgnoreCase("overloaded"))
                    linkstatus = "up";
            } catch (Exception e) {
		logger.info("Exception: "+ e.getMessage());
            }

            List<Relationship> servicerelationList = relationship.stream()
                    .filter(relate -> "service-instance".equalsIgnoreCase(relate.getRelatedTo())).collect(Collectors.toList());
            for (Relationship servicerelation : servicerelationList) {
                String siteserviceinstanceid = servicerelation.getRelatedLink().substring(servicerelation.getRelatedLink().lastIndexOf("/") + 1);
                String cvlan = "";
                String externalltp = "";
                Map<String, Object> site;
                try {
                    siteservice = getServiceInstancesInfo(customerId, subscriptionType, siteserviceinstanceid);
                    if (siteservice == null)
                        return null;
                } catch (Exception e) {
                    logger.info("Query Service Instance information failed. No service information found for customer "
                            + customerId + " and Service Type " + subscriptionType);
                    return null;
                }
                List<Relationship> ssrelationship = siteservice.getRelationshipList().getRelationship().stream()
                        .filter(siterelation -> siterelation.getRelatedTo().equalsIgnoreCase("site-resource") ||
                                siterelation.getRelatedTo().equalsIgnoreCase("allotted-resource")).collect(Collectors.toList());

                if (ssrelationship.size() > 0) {
                    Relationship allotrel = ssrelationship.stream()
                            .filter(relate -> "allotted-resource".equalsIgnoreCase(relate.getRelatedTo()))
                            .findAny()
                            .orElse(null);
                    String allottedResourceId = allotrel.getRelatedLink().substring(allotrel.getRelatedLink().lastIndexOf("/") + 1);
                    try {
                        allottedResource = getAllottedResource(customerId, subscriptionType, siteserviceinstanceid, allottedResourceId);
                        cvlan = allottedResource.getCvlan();
                        externalltp = allottedResource.getAccessLtpId();

                    } catch (Exception e) {
                        logger.info("Query Allotted resource for site service" + siteserviceinstanceid + " allotted resource Id " + allottedResourceId);
                    }


                    Relationship siterel = ssrelationship.stream()
                            .filter(relate -> "site-resource".equalsIgnoreCase(relate.getRelatedTo()))
                            .findAny()
                            .orElse(null);
                    String siteResourceID = siterel.getRelatedLink().substring(siterel.getRelatedLink().lastIndexOf("/") + 1);
                    try {
                        SiteResource resource = getSiteResource(siteResourceID);
                        site = new HashMap<String, Object>();
                        site.put("siteId", resource.getSiteResourceId());
                        site.put("siteName", resource.getSiteResourceName());
                        site.put("description", resource.getDescription());
                        site.put("role", "dsvpn-hub");
                        List<Relationship> complexRelationship = resource.getRelationshipList().getRelationship()
                                .stream().filter(rel -> rel.getRelatedTo().equalsIgnoreCase("complex"))
                                .collect(Collectors.toList());
                        for (Relationship complexrelation : complexRelationship) {
                            String complexID = complexrelation.getRelatedLink().substring(complexrelation.getRelatedLink().lastIndexOf("/") + 1);
                            ComplexObj complex = getComplexData(complexID);    //getSiteResourceInfo
                            site.put("address", complex.getCity());
                            site.put("location", complex.getCity());
                            site.put("zipCode", complex.getPostalCode());
                        }
                        Map<String, Object> attr = new HashMap<String, Object>();
                        attr.put("cvlan", cvlan);
                        attr.put("Access node Id", connectivityparams.get("accessNodeId").toString());
                        attr.put("Access LTP Id", externalltp);
                        attr.put("Location", site.get("location"));
                        attr.put("Site Name", site.get("siteName"));
                        attr.put("Zip Code", site.get("zipCode"));
                        attr.put("Link Status", linkstatus);
                        attr.put("Inter-Domain LTP Id", connectivityparams.get("accessLtpId").toString());
                        site.put("attribute", attr);
                        sites.add(site);
                    } catch (Exception e) {
                        logger.info("Query Service Instance information failed. No service information found for customer "
                                + customerId + " and Service Type " + subscriptionType);
                        return null;
                    }
                } else {
                    String requestinput = siteservice.getInputparameters();
                    E2EServiceInstanceRequest e2eserviceRequest = new E2EServiceInstanceRequest();
                    ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                    try {
                        e2eserviceRequest = mapper.readValue(requestinput.toString(), new TypeReference<E2EServiceInstanceRequest>() {
                        });
                    } catch (IOException e) {
			logger.info("IOException: "+ e.getMessage());
                    }

                    HashMap<String, ?> requestInputs = e2eserviceRequest.getService().getParameters().getRequestInputs();
                    site = new HashMap<String, Object>();
                    Map<String, Object> attr = new HashMap<String, Object>();
                    for (Map.Entry<String, ?> e : requestInputs.entrySet()) {
                        if (e.getKey().contains("name")) {
                            site.put("siteName", (String) requestInputs.get(e.getKey()));

                        } else if (e.getKey().contains("description")) {
                            site.put("description", (String) requestInputs.get(e.getKey()));

                        } else if (e.getKey().contains("role")) {
                            site.put("role", (String) requestInputs.get(e.getKey()));

                        } else if (e.getKey().contains("address")) {
                            site.put("location", (String) requestInputs.get(e.getKey()));

                        } else if (e.getKey().contains("postcode")) {
                            site.put("zipCode", (String) requestInputs.get(e.getKey()));

                        } else if (e.getKey().contains("cVLAN")) {
                            attr.put("cvlan", (String) requestInputs.get(e.getKey()));

                        }
                    }
                    attr.put("Location", site.get("location"));
                    attr.put("Site Name", site.get("siteName"));
                    attr.put("Zip Code", site.get("zipCode"));
                    attr.put("Link Status", linkstatus);
                    site.put("attribute", attr);
                    sites.add(site);
                }
            }
            try {
                jsonresponse = new ObjectMapper().writeValueAsString(sites);
                System.out.println(jsonresponse);
            } catch (IOException e) {
		logger.info("IOException: "+ e.getMessage());
            }

        }
        //----------------------------- GET SERVICE INSTANCE INFORMATION FROM AAI : END -----------------------------------------
        return jsonresponse;
    }

    public DeleteOperationRsp deleteService(String serviceId, String subscriptionType) {
        ModelConfig modelConfig = readFile();
        Map<String, Model> modelInfo = readConfigToMap(modelConfig);
        String customerId = modelConfig.getSubscriberId();
        E2EServiceDelete deleteRequest = new E2EServiceDelete();
        deleteRequest.setServiceType(subscriptionType);
        deleteRequest.setGlobalSubscriberId(customerId);
        String requestStr = deleteRequest.toString();
        Integer sleeptime = Integer.parseInt(modelConfig.getDeleteSleepTime());
        //Get the service information from AAI - Begin
        ServiceInstance serviceInstance = new ServiceInstance();
        try {
            serviceInstance = getServiceInstancesInfo(customerId, subscriptionType, serviceId);
            if (serviceInstance == null) {
                logger.info("Query Service Instance information failed. No service information found for customer "
                        + customerId + " and Service Type " + subscriptionType);
                return null;
            }

        } catch (Exception e) {
            logger.info("Query Service Instance information failed. No service information found for customer "
                    + customerId + " and Service Type " + subscriptionType);
            return null;
        }

        List<Relationship> ssrelationship = serviceInstance.getRelationshipList().getRelationship().stream()
                .filter(siterelation -> siterelation.getRelatedTo().equalsIgnoreCase("service-instance"))
                .collect(Collectors.toList());

        //Get the service information from AAI - Begin
        for (Relationship siterelation : ssrelationship) {

            String siteserviceId = siterelation.getRelatedLink().substring(siterelation.getRelatedLink().lastIndexOf("/") + 1);
            try {
                logger.info("so begin terminate site service " + siteserviceId);
                RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), requestStr);
                Response<DeleteOperationRsp> response = soService.terminateService(siteserviceId, requestBody).execute();
                logger.info("so terminate has finished");
                if (response.isSuccessful()) {
                    logger.info("so terminated site service " + siteserviceId + "successfully...");

                } else {
                    logger.error(String.format("Can not terminate service " + siteserviceId + " [code=%s, message=%s]", response.code(), response.message()));
                    throw new SOException("SO terminate service failed!");
                }
            } catch (IOException e) {
                throw new SOException("SO Service is not available!", e);
            }
        }
        try {
            logger.info("Began to sleep for " + sleeptime);
            Thread.sleep(sleeptime);
        } catch (InterruptedException e) {
            logger.error(String.format("Thread Interruppted from sleep while deleting service subscription"));
        }
        try {
            logger.info("so begin terminate Connectivity service " + serviceId);
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), requestStr);
            Response<DeleteOperationRsp> response = soService.terminateService(serviceId, requestBody).execute();
            logger.info("so terminate has finished");
            if (response.isSuccessful()) {
                logger.info("so terminated connectivity service " + serviceId + "successfully...");

            } else {
                logger.error(String.format("Can not terminate service " + serviceId + " [code=%s, message=%s]", response.code(), response.message()));
                throw new SOException("SO terminate service failed!");
            }
            return response.body();
        } catch (IOException e) {
            throw new SOException("SO Service is not available!", e);
        }
    }

    public VpnBinding getSOTNPinterfaceByVpnId(String vpnId) throws Exception {

        logger.info("Fire getSOTNPinterfaceByVpnId : Begin");
        ObjectMapper mapper = new ObjectMapper();
        Response<ResponseBody> response = this.aaiService.getPinterfaceByVpnId(vpnId).execute();

        //Response<ResponseBody> response = this.aaiService.getSOTNPinterfaceByVpnId(vpnId).execute();
        if (response.isSuccessful()) {
            logger.info("Fire getSOTNPinterfaceByVpnId : End");
            String result = new String(response.body().bytes());
            VpnBinding vpnBinding = mapper.readValue(result, VpnBinding.class);
            return vpnBinding;
        } else {
           logger.info("Fire getSOTNPinterfaceByVpnId : Failed");
        }
        return null;
    }


    public Pnf getSOTNPnf(String pnfname) throws Exception {
	logger.info("Fire getSOTNPnf : Begin");
        ObjectMapper mapper = new ObjectMapper();
        Response<ResponseBody> response = this.aaiService.getPnfInfo(pnfname).execute();
        if (response.isSuccessful()) {
            logger.info("Fire getSOTNPnf : End");
            String result = new String(response.body().bytes());
            Pnf pnf = mapper.readValue(result, Pnf.class);
            return pnf;
        } else {
           logger.info("Fire get SOTN PnF by Name : Failed" + pnfname);
        }
        return null;
    }

    public LogicalLink getSOTNLinkbyName(String linkName) throws Exception {
        logger.info("Fire getSOTNLinkbyName : Begin");
        ObjectMapper mapper = new ObjectMapper();
        Response<ResponseBody> response = this.aaiService.getSpecificLogicalLink(linkName).execute();
        if (response.isSuccessful()) {
            logger.info("Fire getSOTNLinkbyName : End");
            String result = new String(response.body().bytes());
            LogicalLink logicalLink = mapper.readValue(result, LogicalLink.class);
            return logicalLink;
        } else {
           logger.info("Fire getSOTNLinkbyName : Failed");
        }
        return null;
    }

    public Uni getUNIInfo(String uniId) throws Exception {
        logger.info("Fire getUNIInfo : Begin");
        ObjectMapper mapper = new ObjectMapper();
        Response<ResponseBody> response = this.aaiService.getUNIInfo(uniId).execute();
        if (response.isSuccessful()) {
            logger.info("Fire getUNIInfo : Begin");
            String result = new String(response.body().bytes());
            Uni uni = mapper.readValue(result, Uni.class);
            return uni;
        } else {
	   logger.info("Fire getUNIInfo : Failed");
	}
        return null;
    }

    public Vnfs getVnfs(String vnfId) throws Exception {
        logger.info("Fire getVnfs : Begin");
        ObjectMapper mapper = new ObjectMapper();
        Response<ResponseBody> response = this.aaiService.getVNFsDetail(vnfId).execute();
        if (response.isSuccessful()) {
	    logger.info("Fire getVnfs : End");
            String result = new String(response.body().bytes());
            Vnfs vnf = mapper.readValue(result, Vnfs.class);
            return vnf;
        } else {
            logger.info("Fire getVnfs : Failed");
        }
        return null;
    }

    public Node getNode(String id, String label, String image) {
        Node node = new Node();
        node.setId(id);
        node.setShape("circularImage");
        node.setImage("./assets/images/"+image);
        node.setLabel(label);
        node.setColor("Green");
        return node;
    }

    public Edge getEdge(String fromId, String toId) {
        Edge edge = new Edge();
        edge.setFrom(fromId);
        edge.setTo(toId);
        return edge;
    }

    @Override
    public String getVPNBindingInformationTopology(String subscriptionType, String instanceid, String vpnId) throws Exception {

        List<Node> nodes = new ArrayList<Node>();
        List<Edge> edges = new ArrayList<Edge>();
        List<String> vpnparams = new ArrayList<String>(Arrays.asList("cir", "eir", "cbs", "ebs", "colorAware", "couplingFlag", "ethtSvcName"));
        String jsonresponse = "";
        Node connode = new Node();
        try {
            //---------------------------------Query VPN : Begin------------------------------
            VpnBinding vpnBinding = getSOTNPinterfaceByVpnId(vpnId);
            VpnInformation vpnInformation = vpnBinding.getVpnBinding().get(0);
            Node vpn = getNode(vpnInformation.getVpnId(), "VPN Binding", "vpnbinding.png");
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> props = mapper.convertValue(vpnInformation, Map.class);
            props.remove("relationship-list");
            props.remove("resource-version");
            props.remove("vpn-region");
            props.remove("vpn-id");
            vpn.setDataNode(new ObjectMapper().writeValueAsString(props));
            nodes.add(vpn);
            //Query VPN : End

            //Query P Interfaces
            List<Relationship> vpnpinterfacerelation = vpnInformation.getRelationshipList().getRelationship().stream()
                    .filter(relate -> "p-interface".equalsIgnoreCase(relate.getRelatedTo())).collect(Collectors.toList());
            for (Relationship temprel : vpnpinterfacerelation) {
                String pinterfaceid = temprel.getRelatedLink().substring(temprel.getRelatedLink().lastIndexOf("/") + 1);
                String parentaccessnode = "";

                RelationshipData[] pnfrelatedprop = temprel.getRelationshipData();
                for (RelationshipData temp : pnfrelatedprop) {
                    if ("pnf.pnf-name".equalsIgnoreCase(temp.getRelationshipKey())) {
                        parentaccessnode = temp.getRelationshipValue();
                        break;
                    }
                }
                Pinterface pinterface = new Pinterface();
                pinterface = getTerminationPoint(parentaccessnode, pinterfaceid);
                Node vpnpinterface = getNode(pinterface.getInterfaceName(), "P-interface", "tpoint.png");
                ObjectMapper vpnpintmapper = new ObjectMapper();
                Map<String, Object> vpnpintprops = vpnpintmapper.convertValue(pinterface, Map.class);
                vpnpintprops.remove("relationship-list");
                vpnpintprops.remove("resource-version");
                vpnpintprops.remove("in-maint");
                vpnpinterface.setDataNode(new ObjectMapper().writeValueAsString(vpnpintprops));
                nodes.add(vpnpinterface);
                edges.add(getEdge(vpn.getId(),vpnpinterface.getId()));
            }
        } catch (Exception ex) {
	    logger.info("Exception: "+ ex);
        }
        ResourceResponse resourceResponse = new ResourceResponse();
        resourceResponse.setNodes(nodes);
        resourceResponse.setEdges(edges);
        System.out.println(jsonresponse);
        return resourceResponse.toString();
    }

    @Override
    public String getServiceInformationTopology(String subscriptionType, String instanceid) throws Exception {

        List<Node> nodes = new ArrayList<Node>();
        List<Edge> edges = new ArrayList<Edge>();
        ServiceInstance serviceInstance = null;
        Connectivity connectivity = new Connectivity();
        Node connode = new Node();

        //----------------------------- GET SERVICE INSTANCE INFORMATION FROM AAI : BEGIN ---------------------------------------
        try {
            ModelConfig modelConfig = readFile();
            String customerId = modelConfig.getSubscriberId();

            serviceInstance = getServiceInstancesInfo(customerId, subscriptionType, instanceid);
            if (serviceInstance == null) {
                return null;
            }
            else {
                ObjectMapper serviceMapper = new ObjectMapper();
                Map<String, Object> serviceProps = serviceMapper.convertValue(serviceInstance, Map.class);
                serviceProps.remove("relationship-list");
                serviceProps.remove("input-parameters");
                serviceProps.remove("resource-version");
                Node serviceNode = getNode(serviceInstance.getServiceInstanceId(), "Service", "service.png");
                serviceNode.setDataNode(new ObjectMapper().writeValueAsString(serviceProps));
                nodes.add(serviceNode);
            }
        } catch (Exception e) {
            logger.info("Exception: "+ e);
            return null;
        }
        //----------------------------- GET SERVICE INSTANCE INFORMATION FROM AAI : END ---------------------------------------

        //-------------------------------GET GENERIC VNFS : BEGIN ----------------------------------------

        List<Relationship> relationship = serviceInstance.getRelationshipList().getRelationship().stream().filter(relation -> relation.getRelatedTo()
                .equalsIgnoreCase("generic-vnf")).collect(Collectors.toList());
        if (relationship.size() > 0 && relationship != null) {
            relationship = serviceInstance.getRelationshipList().getRelationship();
            String relatedLinkID = relationship.get(0).getRelatedLink();
            Vnfs vnf = getVnfs(relatedLinkID.substring(relatedLinkID.lastIndexOf("/") + 1));
            relationship = vnf.getRelationshipList().getRelationship();

            ObjectMapper serviceMapper = new ObjectMapper();
            Map<String, Object> vnfProps = serviceMapper.convertValue(vnf, Map.class);
            vnfProps.remove("relationship-list");
            vnfProps.remove("in-maint");
            vnfProps.remove("resource-version");
            Node vnfNode = getNode(vnf.getVnfInstanceId(), "Vnf", "VNF.png");
            vnfNode.setDataNode(new ObjectMapper().writeValueAsString(vnfProps));
            nodes.add(vnfNode);
            edges.add(getEdge(serviceInstance.getServiceInstanceId(),vnf.getVnfInstanceId()));

            Relationship relation = relationship.stream()
                    .filter(relate -> "connectivity".equalsIgnoreCase(relate.getRelatedTo()))
                    .findAny()
                    .orElse(null);
            try {
                String connectivityinstanceid = relation.getRelatedLink().substring(relation.getRelatedLink().lastIndexOf("/") + 1);
                connectivity = getConnectivityInfo(connectivityinstanceid);
                Map<String, Object> connectivityparams = new ObjectMapper().readValue(connectivity.toString(), HashMap.class);
                connode = getNode(connectivityparams.get("connectivityId").toString(), "Connectivity", "connectivity.png");
                ObjectMapper conMapper = new ObjectMapper();
                Map<String, Object> conprops = conMapper.convertValue(connectivity, Map.class);
                conprops.remove("relationship-list");
                conprops.remove("resource-version");
                connode.setDataNode(new ObjectMapper().writeValueAsString(conprops));
                nodes.add(connode);
                edges.add(getEdge(vnf.getVnfInstanceId(), connectivityparams.get("connectivityId").toString()));
            } catch (IOException e) {
                logger.info("IO Exception occured " + e.getMessage());
            }

            //Query Connectivity : End
            List<Relationship> relationship1 = vnf.getRelationshipList().getRelationship().stream().filter(relation1 -> relation1.getRelatedTo()
                    .equalsIgnoreCase("uni")).collect(Collectors.toList());
            if (relationship1.size() > 0 && relationship1 != null) {
                for (Relationship rel : relationship1) {
                    try {
                        String uniLink = rel.getRelatedLink();
                        String uniId = uniLink.substring(uniLink.lastIndexOf("/")+1);
                        Uni uniInfo = getUNIInfo(uniId);
                        Node uuinode = getNode(uniInfo.getId(), uniInfo.getId(), "edge.png");
                        ObjectMapper uuiMapper = new ObjectMapper();
                        Map<String, Object> uuiprops = uuiMapper.convertValue(uniInfo, Map.class);
                        uuiprops.remove("relationship-list");
                        uuiprops.remove("resource-version");
                        uuinode.setDataNode(new ObjectMapper().writeValueAsString(uuiprops));
                        nodes.add(uuinode);
                        edges.add(getEdge(vnf.getVnfInstanceId(), uniInfo.getId()));

                        List<Relationship> unipinterfaceralation = uniInfo.getRelationshipList().getRelationship().stream()
                                .filter(relate -> "p-interface".equalsIgnoreCase(relate.getRelatedTo())).collect(Collectors.toList());
                        for (Relationship temprel : unipinterfaceralation) {
                            String pinterfaceid = temprel.getRelatedLink().substring(temprel.getRelatedLink().lastIndexOf("/") + 1);
                            String parentaccessnode = "";

                            RelationshipData[] pnfrelatedprop = temprel.getRelationshipData();
                            for (RelationshipData temp : pnfrelatedprop) {
                                if ("pnf.pnf-name".equalsIgnoreCase(temp.getRelationshipKey())) {
                                    parentaccessnode = temp.getRelationshipValue();
                                    break;
                                }
                            }
                            try {

                                Pinterface pinterface = getTerminationPoint(parentaccessnode, pinterfaceid);

                                ObjectMapper unipintmapper = new ObjectMapper();
                                Map<String, Object> unipintprops = unipintmapper.convertValue(pinterface, Map.class);
                                unipintprops.remove("relationship-list");
                                unipintprops.remove("resource-version");
                                unipintprops.remove("in-maint");

                                Node unipinterface = getNode(pinterface.getInterfaceName(), "P-interface", "tpoint.png");
                                unipinterface.setDataNode(new ObjectMapper().writeValueAsString(unipintprops));
                                nodes.add(unipinterface);
                                edges.add(getEdge(uniInfo.getId(), unipinterface.getId()));

                            } catch (Exception e) {
				logger.info("Exception:"+e.getMessage());
                            }
                        }

                    } catch (IOException e) {
                        logger.info("IO Exception occured " + e.getMessage());
                    }
                }
                //Query UNI : End
            }

            //---------------------------------Query VPN : Begin------------------------------
            Relationship vpnrelation = connectivity.getRelationshipList().getRelationship().stream()
                    .filter(relate -> "vpn-binding".equalsIgnoreCase(relate.getRelatedTo()))
                    .findAny()
                    .orElse(null);
            String vpnid = vpnrelation.getRelatedLink().substring(vpnrelation.getRelatedLink().lastIndexOf("/") + 1);
            VpnBinding vpnBinding = new VpnBinding();
            VpnInformation vpnInformation = new VpnInformation();
            try {
                vpnBinding = getSOTNPinterfaceByVpnId(vpnid);
                vpnInformation = vpnBinding.getVpnBinding().get(0);
            } catch (Exception e) {
		logger.info("Exception:"+e.getMessage());
            }

            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> props = mapper.convertValue(vpnInformation, Map.class);
            props.remove("relationship-list");
            props.remove("resource-version");
            props.remove("vpn-region");
            props.remove("vpn-id");
            Node vpn = getNode(vpnInformation.getVpnId(), "VPN Binding", "vpnbinding.png");
            vpn.setDataNode(new ObjectMapper().writeValueAsString(props));
            nodes.add(vpn);
            edges.add(getEdge(connode.getId(), vpn.getId()));
            //Query VPN : End

            //Query P Interfaces
            List<Relationship> vpnpinterfacerelation = vpnInformation.getRelationshipList().getRelationship().stream()
                    .filter(relate -> "p-interface".equalsIgnoreCase(relate.getRelatedTo())).collect(Collectors.toList());
            for (Relationship temprel : vpnpinterfacerelation) {
                String pinterfaceid = temprel.getRelatedLink().substring(temprel.getRelatedLink().lastIndexOf("/") + 1);
                String parentaccessnode = "";

                RelationshipData[] pnfrelatedprop = temprel.getRelationshipData();
                for (RelationshipData temp : pnfrelatedprop) {
                    if ("pnf.pnf-name".equalsIgnoreCase(temp.getRelationshipKey())) {
                        parentaccessnode = temp.getRelationshipValue();
                        break;
                    }
                }
                try {
                    Pinterface pinterface = getTerminationPoint(parentaccessnode, pinterfaceid);
                    edges.add(getEdge(vpn.getId(), pinterface.getInterfaceName()));
                } catch (Exception e) {
		    logger.info("Exception:"+e.getMessage());
                }
            }
        }
        ResourceResponse resourceResponse = new ResourceResponse();
        resourceResponse.setNodes(nodes);
        resourceResponse.setEdges(edges);
	logger.info("Service Topology:"+resourceResponse.toString());
        return resourceResponse.toString();
    }

    @Override
    public String getSOTNResourceInformationTopology(String subscriptionType, String instanceid) throws Exception {

        //--------------------------------------------------------------------------------------------------------------------------
        ModelConfig modelConfig = readFile();
        Map<String, Model> modelInfo = readConfigToMap(modelConfig);
        String customerId = modelConfig.getSubscriberId();
        ResourceResponse resourceResponse = new ResourceResponse();
        List<Node> nodes = new ArrayList<Node>();
        List<Edge> edges = new ArrayList<Edge>();
        List<String> vpnparams = new ArrayList<String>(Arrays.asList("cir", "eir", "cbs", "ebs", "colorAware", "couplingFlag", "ethtSvcName"));
        List<String> tpparams = new ArrayList<String>(Arrays.asList("ethtSvcName", "accessProviderId", "accessClientId", "accessTopologyId", "accessNodeId", "accessLtpId"));
        ServiceInstance serviceInstance = null;
        ServiceInstance siteservice = null;
        Map<String, Object> connectivityparams;
        String jsonresponse = "";
        AllottedResource allottedResource = new AllottedResource();
        Connectivity connectivity = new Connectivity();
        Node allottednode = new Node();
        Node connode = new Node();

        String allottedpinterfaceid = "";
        ObjectMapper jsonmapper = new ObjectMapper();
        List<Relationship> tpinterface = new ArrayList<>();
        LogicalLink logicallink = new LogicalLink();
        Pnf extpnf = new Pnf();
        String logicallinkparentid = "";
        boolean ext_tp_found = false;
        //----------------------------- GET SERVICE INSTANCE INFORMATION FROM AAI : BEGIN ---------------------------------------
        try {
            serviceInstance = getServiceInstancesInfo(customerId, subscriptionType, instanceid);
            if (serviceInstance == null)
                return null;
        } catch (Exception e) {
            logger.info("Query Service Instance information failed. No service information found for customer "
                    + customerId + " and Service Type " + subscriptionType);
            return null;
        }


        List<Relationship> relationship = serviceInstance.getRelationshipList().getRelationship().stream().filter(relation -> relation.getRelatedTo()
                .equalsIgnoreCase("allotted-resource")).collect(Collectors.toList());
        if (relationship.size() > 0 && relationship != null) {
            // This is SOTN service
            //Query Connectivity : Begin
            connectivityparams = new HashMap<String, Object>();
            relationship = serviceInstance.getRelationshipList().getRelationship();
            Relationship relation = relationship.stream()
                    .filter(relate -> "connectivity".equalsIgnoreCase(relate.getRelatedTo()))
                    .findAny()
                    .orElse(null);
            try {
                String connectivityinstanceid = relation.getRelatedLink().substring(relation.getRelatedLink().lastIndexOf("/") + 1);
                connectivity = getConnectivityInfo(connectivityinstanceid);
                connectivityparams = new ObjectMapper().readValue(connectivity.toString(), HashMap.class);

                connode.setId(connectivityparams.get("connectivityId").toString());
                connode.setShape("circularImage");
                connode.setImage("./assets/treeTopology/connectivity.png");
                connode.setLabel("Connectivity");
                connode.setColor("Green");
                Map<String, Object> datanode = new HashMap<String, Object>();
                for (String key : vpnparams) {
                    if (key.equalsIgnoreCase("ebs"))
                        datanode.put("Service Type", connectivityparams.get(key));
                    else
                        datanode.put(key, connectivityparams.get(key));
                }

                connode.setDataNode(new ObjectMapper().writeValueAsString(datanode));
                nodes.add(connode);

            } catch (IOException e) {
               logger.info("IO Exception occured " + e.getMessage());
            }
            //Query Connectivity : End


            //Query VPN : Begin
            Relationship vpnrelation = connectivity.getRelationshipList().getRelationship().stream()
                    .filter(relate -> "vpn-binding".equalsIgnoreCase(relate.getRelatedTo()))
                    .findAny()
                    .orElse(null);
            String vpnid = vpnrelation.getRelatedLink().substring(vpnrelation.getRelatedLink().lastIndexOf("/") + 1);
            VpnBinding vpnBinding = new VpnBinding();
            VpnInformation vpnInformation = new VpnInformation();
            try {
                vpnBinding = getSOTNPinterfaceByVpnId(vpnid);
                vpnInformation = vpnBinding.getVpnBinding().get(0);
            } catch (Exception e) {
		logger.info("Exception occured " + e.getMessage());
            }
            Node vpn = new Node();
            vpn.setId(vpnInformation.getVpnId());
            vpn.setShape("circularImage");
            vpn.setImage("./assets/treeTopology/vpnbinding.png");
            vpn.setLabel("VPN Binding");
            vpn.setColor("Green");
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> props = mapper.convertValue(vpnInformation, Map.class);
            props.remove("relationship-list");
            props.remove("resource-version");
            props.remove("vpn-region");
            props.remove("vpn-id");
            vpn.setDataNode(new ObjectMapper().writeValueAsString(props));
            nodes.add(vpn);
            Edge connectivitytovpnedge = new Edge();
            connectivitytovpnedge.setFrom(connode.getId());
            connectivitytovpnedge.setTo(vpn.getId());
            edges.add(connectivitytovpnedge);

            //Query VPN : End

            //Query access node : Begin

            String srcnodeid = vpnInformation.getSrcAccessNodeId();
            Pnf srcpnf = new Pnf();
            srcpnf = getSOTNPnf(srcnodeid);
            Node srcpnfnode = new Node();
            Node dstpnfnode = new Node();
            srcpnfnode.setId(srcpnf.getPnfId());
            srcpnfnode.setShape("circularImage");
            srcpnfnode.setImage("./assets/treeTopology/accessnode.png");
            srcpnfnode.setLabel("Abstract Node");
            srcpnfnode.setColor("Green");
            ObjectMapper srcpnfmapper = new ObjectMapper();
            Map<String, Object> srcpnfprop = srcpnfmapper.convertValue(srcpnf, Map.class);
            srcpnfprop.remove("relationship-list");
            srcpnfprop.remove("resource-version");
            srcpnfprop.remove("in-maint");
            srcpnfprop.remove("admin-status");
            srcpnfnode.setDataNode(new ObjectMapper().writeValueAsString(srcpnfprop));
            nodes.add(srcpnfnode);

            String dstnodeid = vpnInformation.getDstAccessNodeId();
            boolean foundnode = false;
            for (Node node : nodes) {
                if (node.getId().equalsIgnoreCase(dstnodeid)) {
                    foundnode = true;
                    break;
                }
            }
            if (!foundnode) {
                Pnf dstpnf = new Pnf();
                dstpnf = getSOTNPnf(dstnodeid);

                dstpnfnode.setId(dstpnf.getPnfId());
                dstpnfnode.setShape("circularImage");
                dstpnfnode.setImage("./assets/treeTopology/accessnode.png");
                dstpnfnode.setLabel("Abstract Node");
                dstpnfnode.setColor("Green");
                ObjectMapper dstpnfmapper = new ObjectMapper();
                Map<String, Object> dstpnfprop = dstpnfmapper.convertValue(dstpnf, Map.class);
                dstpnfprop.remove("relationship-list");
                dstpnfprop.remove("resource-version");
                dstpnfprop.remove("in-maint");
                dstpnfnode.setDataNode(new ObjectMapper().writeValueAsString(srcpnfprop));
                nodes.add(dstpnfnode);
            }

            //Query P Interfaces


            List<Relationship> vpnpinterfacerelation = vpnInformation.getRelationshipList().getRelationship().stream()
                    .filter(relate -> "p-interface".equalsIgnoreCase(relate.getRelatedTo())).collect(Collectors.toList());
            for (Relationship temprel : vpnpinterfacerelation) {
                String pinterfaceid = temprel.getRelatedLink().substring(temprel.getRelatedLink().lastIndexOf("/") + 1);
                String parentaccessnode = "";


                RelationshipData[] pnfrelatedprop = temprel.getRelationshipData();
                for (RelationshipData temp : pnfrelatedprop) {
                    if ("pnf.pnf-name".equalsIgnoreCase(temp.getRelationshipKey())) {
                        parentaccessnode = temp.getRelationshipValue();
                        break;
                    }
                }
                try {
                    Pinterface pinterface = new Pinterface();
                    pinterface = getTerminationPoint(parentaccessnode, pinterfaceid);
                    Node vpnpinterface = new Node();
                    vpnpinterface.setId(pinterface.getInterfaceName());
                    vpnpinterface.setShape("circularImage");
                    vpnpinterface.setImage("./assets/treeTopology/tpoint.png");
                    vpnpinterface.setLabel("Termination Point");
                    vpnpinterface.setColor("Green");
                    ObjectMapper vpnpintmapper = new ObjectMapper();
                    Map<String, Object> vpnpintprops = vpnpintmapper.convertValue(pinterface, Map.class);
                    vpnpintprops.remove("relationship-list");
                    vpnpintprops.remove("resource-version");
                    vpnpintprops.remove("in-maint");
                    vpnpinterface.setDataNode(new ObjectMapper().writeValueAsString(vpnpintprops));
                    nodes.add(vpnpinterface);
                    Edge iallotedtopnfedge = new Edge();
                    iallotedtopnfedge.setFrom(vpn.getId());
                    iallotedtopnfedge.setTo(vpnpinterface.getId());
                    edges.add(iallotedtopnfedge);
                    String toedge = "";
                    for (Node node : nodes) {
                        if (node.getId().equalsIgnoreCase(parentaccessnode)) {
                            toedge = node.getId();
                            break;
                        }
                    }
                    Edge tptopnfedge = new Edge();
                    tptopnfedge.setFrom(toedge);
                    tptopnfedge.setTo(vpnpinterface.getId());
                    edges.add(tptopnfedge);
                    int logicallinkcount = 0;
                    logicallinkcount = pinterface.getRelationshipList().getRelationship().stream().filter(prelation -> prelation.getRelatedTo()
                            .equalsIgnoreCase("logical-link")).collect(Collectors.toList()).size();
                    if (logicallinkcount > 0) {
                        tpinterface = pinterface.getRelationshipList().getRelationship().stream().filter(prelation -> prelation.getRelatedTo()
                                .equalsIgnoreCase("logical-link")).collect(Collectors.toList());
                        logicallinkparentid = pinterface.getInterfaceName();
                    }

                } catch (Exception e) {
		    logger.info("Exception occured " + e.getMessage());
                }
            }
            //Query logical Link : Begin

            Relationship logicallinkrelation = tpinterface.get(0);
            String linkname = logicallinkrelation.getRelatedLink().substring(logicallinkrelation.getRelatedLink().lastIndexOf("/") + 1);
            try {
                logicallink = getSOTNLinkbyName(linkname);
                Node logiclink = new Node();
                logiclink.setId(logicallink.getLinkName());
                logiclink.setShape("circularImage");
                logiclink.setImage("./assets/treeTopology/logicallink.png");
                logiclink.setLabel("Logical Link");
                logiclink.setColor("Green");
                ObjectMapper linkmapper = new ObjectMapper();
                Map<String, Object> linkpropprops = linkmapper.convertValue(logicallink, Map.class);
                linkpropprops.remove("relationship-list");
                linkpropprops.remove("resource-version");
                linkpropprops.remove("in-maint");
                logiclink.setDataNode(new ObjectMapper().writeValueAsString(linkpropprops));
                nodes.add(logiclink);
                Edge tptologicallinkedge = new Edge();
                tptologicallinkedge.setFrom(logicallinkparentid);
                tptologicallinkedge.setTo(logiclink.getId());
                edges.add(tptologicallinkedge);
            } catch (Exception e) {
		logger.info("Exception occured " + e.getMessage());
            }

            List<Relationship> llrelationlist = logicallink.getRelationshipList().getRelationship().stream().filter(llrelation -> llrelation.getRelatedTo()
                    .equalsIgnoreCase("p-interface")).collect(Collectors.toList());


            String externalnode = "";
            for (Relationship llrel : llrelationlist) {
                externalnode = "";
                RelationshipData[] llrelatedprop = llrel.getRelationshipData();
                for (RelationshipData temp : llrelatedprop) {
                    if ("pnf.pnf-name".equalsIgnoreCase(temp.getRelationshipKey())) {
                        externalnode = temp.getRelationshipValue();
                        break;
                    }
                }
                if (!dstnodeid.equalsIgnoreCase(externalnode)) {
                    Pinterface extpinterface = new Pinterface();
                    String pinterfaceid = llrel.getRelatedLink().substring(llrel.getRelatedLink().lastIndexOf("/") + 1);
                    extpinterface = getTerminationPoint(externalnode, pinterfaceid);
                    Node extpinterfacenode = new Node();
                    extpinterfacenode.setId(extpinterface.getInterfaceName());
                    extpinterfacenode.setShape("circularImage");
                    extpinterfacenode.setImage("./assets/treeTopology/tpoint.png");
                    extpinterfacenode.setLabel("Termination Point");
                    extpinterfacenode.setColor("Green");
                    ObjectMapper vpnpintmapper = new ObjectMapper();
                    Map<String, Object> vpnpintprops = vpnpintmapper.convertValue(extpinterface, Map.class);
                    vpnpintprops.remove("relationship-list");
                    vpnpintprops.remove("resource-version");
                    vpnpintprops.remove("in-maint");
                    extpinterfacenode.setDataNode(new ObjectMapper().writeValueAsString(vpnpintprops));
                    nodes.add(extpinterfacenode);
                    Edge iallotedtopnfedge = new Edge();
                    iallotedtopnfedge.setFrom(logicallink.getLinkName());
                    iallotedtopnfedge.setTo(extpinterfacenode.getId());
                    edges.add(iallotedtopnfedge);
                    for (Node node : nodes) {
                        if (node.getId().equalsIgnoreCase(externalnode)) {
                            ext_tp_found = true;
                            break;
                        }
                    }

                    if (!ext_tp_found) {
                        extpnf = getSOTNPnf(externalnode);
                        Node extpnfnode = new Node();
                        extpnfnode.setId(extpnf.getPnfId());
                        extpnfnode.setShape("circularImage");
                        extpnfnode.setImage("./assets/treeTopology/accessnode.png");
                        extpnfnode.setLabel("External Abstract Node");
                        extpnfnode.setColor("Green");
                        ObjectMapper linkmapper = new ObjectMapper();
                        Map<String, Object> exttpprops = linkmapper.convertValue(extpnf, Map.class);
                        exttpprops.remove("relationship-list");
                        exttpprops.remove("resource-version");
                        exttpprops.remove("in-maint");
                        exttpprops.remove("admin-status");
                        extpnfnode.setDataNode(new ObjectMapper().writeValueAsString(exttpprops));
                        nodes.add(extpnfnode);
                        Edge exttpedge = new Edge();
                        exttpedge.setFrom(extpinterface.getInterfaceName());
                        exttpedge.setTo(extpnf.getPnfId());
                        edges.add(exttpedge);
                    }
                }
            }


            Relationship externalaairel = logicallink.getRelationshipList().getRelationship().stream()
                    .filter(relate -> "ext-aai-network".equalsIgnoreCase(relate.getRelatedTo()))
                    .findAny()
                    .orElse(null);
            if (null != externalaairel) {
                Node externalaai = new Node();
                externalaai.setId("EXT_AAI");
                externalaai.setShape("circularImage");
                externalaai.setImage("./assets/treeTopology/extaai.png");
                externalaai.setLabel("External AAI");
                externalaai.setColor("Green");
                Map<String, Object> extaaiprops = new HashMap<>();
                String extaainame = externalaairel.getRelatedLink().substring(externalaairel.getRelatedLink().lastIndexOf("/") + 1);
                extaaiprops.put("Name", extaainame);
                externalaai.setDataNode(new ObjectMapper().writeValueAsString(extaaiprops));
                nodes.add(externalaai);
                Edge extaaiedge = new Edge();
                extaaiedge.setFrom(extpnf.getPnfId());
                extaaiedge.setTo(externalaai.getId());
                edges.add(extaaiedge);
            }
            //Query logical Link : End
        }
        resourceResponse.setNodes(nodes);
        resourceResponse.setEdges(edges);
        logger.info("jsonresponse: "+jsonresponse);
        return resourceResponse.toString();
    }


    @Override
    public String getService(String subscriptionType, String instanceid) {
        ServiceEstimationBean serviceEstimationBean = new ServiceEstimationBean();
        ServiceInstance serviceInstance = null;
        ServiceInstance siteservice = null;
        ModelConfig modelConfig = readFile();
        Map<String, Model> modelInfo = readConfigToMap(modelConfig);
        String customerId = modelConfig.getSubscriberId();
        Map<String, Object> connectivityparams;
        List<String> vpnparams = new ArrayList<String>(Arrays.asList("cir", "eir", "ebs", "ethtSvcName"));
        List<Map<String, Object>> sites = new LinkedList<Map<String, Object>>();
        List<Map<String, Object>> vpnmap = new LinkedList<Map<String, Object>>();
        Map<String, Object> vpninformation = new HashMap<String, Object>();
        String jsonresponse = "";
        //----------------------------- GET SERVICE INSTANCE INFORMATION FROM AAI : BEGIN ---------------------------------------
        try {
            serviceInstance = getServiceInstancesInfo(customerId, subscriptionType, instanceid);
            if (serviceInstance == null) {
                logger.info("Query site Service Instance information failed. No service information found for customer "
                        + customerId + " and Service Type " + subscriptionType);
                return null;
            } else {
                logger.info("Instance Name is:"+serviceInstance.getServiceInstanceName()+", Instance ID is: "+ serviceInstance.getServiceInstanceId());
            }
        } catch (Exception e) {
            logger.info("Query Service Instance information failed. No service information found for customer "
                    + customerId + " and Service Type " + subscriptionType);
            return null;
        }

        List<Relationship> relationship = serviceInstance.getRelationshipList().getRelationship().stream().filter(relation -> relation.getRelatedTo().equalsIgnoreCase("allotted-resource")).collect(Collectors.toList());
        if (relationship.size() > 0 && relationship != null) {
            // This is SOTN service
            connectivityparams = new HashMap<String, Object>();
            relationship = serviceInstance.getRelationshipList().getRelationship();
            for (Relationship relation : relationship) {
                String strRelationship = relation.getRelatedTo().toLowerCase();
                switch (strRelationship) {
                    case "connectivity":
                        try {
                            String connectivityinstanceid = relation.getRelatedLink().substring(relation.getRelatedLink().lastIndexOf("/") + 1);
                            Connectivity connectivity = getConnectivityInfo(connectivityinstanceid);
                            connectivityparams = new ObjectMapper().readValue(connectivity.toString(), HashMap.class);

                        } catch (IOException e) {
                            logger.info("IO Exception occured " + e.getMessage());
                        }

                        break;
                    //case "allotted-resource":
                    case "service-instance":
                        String siteserviceinstanceid = relation.getRelatedLink().substring(relation.getRelatedLink().lastIndexOf("/") + 1);
                        String cvlan = "";
                        Map<String, Object> site;
                        try {
                            siteservice = getServiceInstancesInfo(customerId, subscriptionType, siteserviceinstanceid);
                            if (siteservice == null) {
                                logger.info("Query site Service Instance information failed. No service information found for customer "
                                        + customerId + " and Service Type " + subscriptionType);
                                return null;
                            }
                        } catch (Exception e) {
                            logger.info("Query site Instance information failed. No service information found for customer "
                                    + customerId + " and Service Type " + subscriptionType);
                            return null;
                        }
                        List<Relationship> ssrelationship = siteservice.getRelationshipList().getRelationship().stream()
                                .filter(siterelation -> siterelation.getRelatedTo().equalsIgnoreCase("site-resource") ||
                                        siterelation.getRelatedTo().equalsIgnoreCase("allotted-resource")).collect(Collectors.toList());

                        if (ssrelationship.size() > 0) {
                            Relationship allotrel = ssrelationship.stream()
                                    .filter(relate -> "allotted-resource".equalsIgnoreCase(relate.getRelatedTo()))
                                    .findAny()
                                    .orElse(null);
                            String allottedResourceId = allotrel.getRelatedLink().substring(allotrel.getRelatedLink().lastIndexOf("/") + 1);
                            try {
                                AllottedResource allottedResource = getAllottedResource(customerId, subscriptionType, siteserviceinstanceid, allottedResourceId);
                                cvlan = allottedResource.getCvlan();
                            } catch (Exception e) {
                            }

                            Relationship siterel = ssrelationship.stream()
                                    .filter(relate -> "site-resource".equalsIgnoreCase(relate.getRelatedTo()))
                                    .findAny()
                                    .orElse(null);
                            String siteResourceID = siterel.getRelatedLink().substring(siterel.getRelatedLink().lastIndexOf("/") + 1);
                            try {
                                SiteResource resource = getSiteResource(siteResourceID);
                                site = new HashMap<String, Object>();
                                site.put("siteId", resource.getSiteResourceId());
                                site.put("siteName", resource.getSiteResourceName());
                                site.put("description", resource.getDescription());
                                site.put("role", "dsvpn-hub");
                                site.put("cvlan", cvlan);
                                List<Relationship> complexRelationship = resource.getRelationshipList().getRelationship()
                                        .stream().filter(rel -> rel.getRelatedTo().equalsIgnoreCase("complex"))
                                        .collect(Collectors.toList());
                                for (Relationship complexrelation : complexRelationship) {
                                    String complexID = complexrelation.getRelatedLink().substring(complexrelation.getRelatedLink().lastIndexOf("/") + 1);
                                    ComplexObj complex = getComplexData(complexID);    //getSiteResourceInfo
                                    site.put("address", complex.getCity());
                                    site.put("location", complex.getCity());
                                    site.put("zipCode", complex.getPostalCode());
                                }
//                                Map<String, String> attr = new HashMap<String, String>();
//                                attr.put("cvlan",cvlan);
//                                attr.put("accessnodeid",connectivityparams.get("accessNodeId").toString());
//                                attr.put("accessltpid",connectivityparams.get("accessLtpId").toString());
//                                site.put("attribute", attr);
                                sites.add(site);
                            } catch (Exception e) {
                                logger.info("Query Service Instance information failed. No service information found for customer "
                                        + customerId + " and Service Type " + subscriptionType);
                                return null;
                            }
                        } else {
                            String requestinput = siteservice.getInputparameters();
                            E2EServiceInstanceRequest e2eserviceRequest = new E2EServiceInstanceRequest();
                            ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                            try {
                                e2eserviceRequest = mapper.readValue(requestinput.toString(), new TypeReference<E2EServiceInstanceRequest>() {
                                });
                            } catch (IOException e) {
                                logger.info("Query remove Service Instance information failed" + e.getMessage());
                            }

                            HashMap<String, ?> requestInputs = e2eserviceRequest.getService().getParameters().getRequestInputs();
                            site = new HashMap<String, Object>();
                            //Map<String, Object> attr = new HashMap<String, Object>();
                            for (Map.Entry<String, ?> e : requestInputs.entrySet()) {
                                if (e.getKey().contains("name")) {
                                    site.put("siteName", (String) requestInputs.get(e.getKey()));

                                } else if (e.getKey().contains("description")) {
                                    site.put("description", (String) requestInputs.get(e.getKey()));

                                } else if (e.getKey().contains("role")) {
                                    site.put("role", (String) requestInputs.get(e.getKey()));

                                } else if (e.getKey().contains("address")) {
                                    site.put("location", (String) requestInputs.get(e.getKey()));

                                } else if (e.getKey().contains("postcode")) {
                                    site.put("zipCode", (String) requestInputs.get(e.getKey()));

                                } else if (e.getKey().contains("cVLAN")) {
                                    site.put("cvlan", (String) requestInputs.get(e.getKey()));

                                }
                            }
                            //site.put("attribute", attr);
                            sites.add(site);
                        }


//                        //------------------------------------------------------------- Old logic begin --------------
//                        try
//                        {
//                            String allottedResourceId = relation.getRelatedLink().substring(relation.getRelatedLink().lastIndexOf("/") + 1);
//                            String siteserviceintanceid = "";
//                            List<RelationshipDatum> relationshipDataum = relation.getRelationshipData();
//                            for(RelationshipDatum datum : relationshipDataum)
//                            {
//                                if(datum.getRelationshipKey().equalsIgnoreCase("service-instance.service-instance-id")){
//                                siteserviceintanceid=datum.getRelationshipValue();
//                                break;
//                                }
//                            }
//                            AllottedResource allottedResource = getAllottedResource(customerId,subscriptionType,siteserviceintanceid, allottedResourceId);
//                            List<Relationship> allottedRelationship = allottedResource.getRelationshipList().getRelationship();
//                            for(Relationship allrelation : allottedRelationship)
//                            {
//                                String strAllRelationship = allrelation.getRelatedTo().toLowerCase();
//                                if("service-instance".equalsIgnoreCase(strAllRelationship))
//                                {
//                                    String allottedServiceId = allrelation.getRelatedLink().substring(allrelation.getRelatedLink().lastIndexOf("/") + 1);
//                                    if(!allottedServiceId.equalsIgnoreCase(instanceid))
//                                    {
//                                        try {
//                                            ServiceInstance allottedserviceInstance = getServiceInstancesInfo(customerId, subscriptionType, allottedServiceId);
//                                            if (serviceInstance != null)
//                                            {
//                                                List<Relationship> siteRelationship = allottedserviceInstance.getRelationshipList()
//                                                        .getRelationship().stream().filter(siterelation -> siterelation.getRelatedTo()
//                                                                .equalsIgnoreCase("site-resource")).collect(Collectors.toList());
//                                                for(Relationship siterelation : siteRelationship) {
//                                                    String siteResourceID = siterelation.getRelatedLink().substring(siterelation.getRelatedLink().lastIndexOf("/") + 1);
//                                                    SiteResource resource = getSiteResource(siteResourceID);    //getSiteResourceInfo
//                                                    Map<String, String> site = new HashMap<String, String>();
//                                                    site.put("siteId",resource.getSiteResourceId());
//                                                    site.put("siteName",resource.getSiteResourceName());
//                                                    site.put("description",resource.getDescription());
//
//                                                    List<Relationship> complexRelationship = resource.getRelationshipList().getRelationship();
//                                                    for(Relationship complexrelation : siteRelationship) {
//                                                        String complexID = complexrelation.getRelatedLink().substring(complexrelation.getRelatedLink().lastIndexOf("/") + 1);
//                                                        ComplexObj complex = getComplexData(complexID);    //getSiteResourceInfo
//                                                        site.put("address",complex.getCity());
//                                                        site.put("zipCode",complex.getPostalCode());
//                                                    }
//                                                    sites.add(site);
//                                                }
//
//                                            }
//
//                                        } catch (Exception e) {
//                                            logger.info("Query Service Instance information failed. No service information found for customer "
//                                                    + customerId + " and Service Type " + subscriptionType + "and Service Instance ID  "+instanceid);
//                                        }
//                                    }
//                                }
//                            }
//                            connectivityparams = new ObjectMapper().readValue(allottedResource.toString(), HashMap.class);
//                        }catch (IOException e)
//                        {
//
//                        }
//
//                        //-------------------------------------------------------- old logic end here.--------------------------------------------

                        break;
                    default:
                        break;

                }
            }

            //create response bean
            //connectivityparams.remove()
            Map<String, Object> response = new HashMap<String, Object>();
            for (String key : vpnparams) {
                if (key.equalsIgnoreCase("ebs"))
                    response.put("Service Type", connectivityparams.get(key));
                else if (key.equalsIgnoreCase("ethtSvcName"))
                    response.put("Service Name", connectivityparams.get(key));
                else
                    response.put(key, connectivityparams.get(key));
            }
            vpninformation.put("vpnName", connectivityparams.get("ethtSvcName"));
            vpninformation.put("vpnId", connectivityparams.get("connectivityId"));
            vpninformation.put("vpnType", "Hub-Spoke");
            vpninformation.put("vpnBandwidth", connectivityparams.get("cbs"));
            vpninformation.put("vpnThreshold", connectivityparams.get("eir"));
            vpninformation.put("sites", sites);
            vpnmap.add(vpninformation);
            response.put("vpnInformations", vpnmap);

            try {

                jsonresponse = new ObjectMapper().writeValueAsString(response);
                logger.info("jsonresponse:"+ jsonresponse);
            } catch (IOException e) {
		logger.info("IO Exception occured " + e.getMessage());
            }

        } else {
            //This is DWAN service
	    logger.info("There is no logic for get Service");
        }

        //----------------------------- GET SERVICE INSTANCE INFORMATION FROM AAI : END -----------------------------------------
        return jsonresponse;
    }

    @Override
    public String getSOTNInstantiationstatus(String instanceid) {
        ServiceInstantiationResponse serviceInstantiationResponse = new ServiceInstantiationResponse();
        ServiceInstance infraInstance = null;
        ServiceInstance siteInstance = null;
        ModelConfig modelConfig = readFile();
        String subscriptionType = modelConfig.getSubscriptionType();
        String customerId = modelConfig.getSubscriberId();
        int activatedsitecount = 0;
        try {
            infraInstance = getServiceInstancesInfo(customerId, subscriptionType, instanceid);
            if (infraInstance == null)
                return null;
        } catch (Exception e) {
             logger.info("Query Service Instance information failed. No service information found for customer "
                 + customerId + " and Service Type " + subscriptionType);
            return null;
        }
        if (infraInstance.getOrchestrationstatus().equalsIgnoreCase("Assigned") || infraInstance.getOrchestrationstatus().equalsIgnoreCase("Active")) {
            activatedsitecount = activatedsitecount + 1;
            List<Relationship> infrarelationlist = infraInstance.getRelationshipList().getRelationship().stream().filter(relation -> relation.getRelatedTo().equalsIgnoreCase("service-instance"))
                    .collect(Collectors.toList());
            for (Relationship relation : infrarelationlist) {
                String siteservice = relation.getRelatedLink().substring(relation.getRelatedLink().lastIndexOf("/") + 1);
                try {
                    siteInstance = getServiceInstancesInfo(customerId, subscriptionType, siteservice);
                    if (infraInstance == null) {
                        serviceInstantiationResponse.setStatus("0");
                        return serviceInstantiationResponse.toString();
                    }
                } catch (Exception e) {
                    logger.info("Query Service Instance information failed. No service information found for customer "
                      + customerId + " and Service Type " + subscriptionType);
                    serviceInstantiationResponse.setStatus("0");
                    return serviceInstantiationResponse.toString();
                }
                if (siteInstance.getOrchestrationstatus().equalsIgnoreCase("Assigned") || siteInstance.getOrchestrationstatus().equalsIgnoreCase("Active")) {
                    activatedsitecount = activatedsitecount + 1;
                } else {
                    break;
                }
            }
        } else {
            serviceInstantiationResponse.setStatus("0");
        }
        if (activatedsitecount == 3)
            serviceInstantiationResponse.setStatus("1");
        return serviceInstantiationResponse.toString();
    }
}
