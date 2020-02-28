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

import com.alibaba.dubbo.common.utils.IOUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import org.aspectj.asm.AsmManager;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
//import org.onap.usecaseui.server.bean.activateEdge.*;
import org.onap.usecaseui.server.bean.lcm.sotne2eservice.*;
import org.onap.usecaseui.server.bean.orderservice.ServiceEstimationBean;
import org.onap.usecaseui.server.service.lcm.SotnServiceTemplateService;
import org.onap.usecaseui.server.service.lcm.domain.aai.AAIService;
import org.onap.usecaseui.server.service.lcm.domain.so.SOService;
import org.onap.usecaseui.server.service.lcm.domain.so.bean.DeleteOperationRsp;
import org.onap.usecaseui.server.service.lcm.domain.so.bean.Operation;
import org.onap.usecaseui.server.service.lcm.domain.so.bean.ServiceOperation;
import org.onap.usecaseui.server.service.lcm.domain.so.exceptions.SOException;
import org.onap.usecaseui.server.util.RestfulServices;
import org.onap.usecaseui.server.util.configfile.ConfigFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Service;
import retrofit2.Response;
import org.onap.usecaseui.server.bean.lcm.sotne2eservice.*;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

@Service("SotnLcmService")
@org.springframework.context.annotation.Configuration
@EnableAspectJAutoProxy
public class SotnServiceTemplateServiceImpl implements SotnServiceTemplateService {
    private static final Logger logger = LoggerFactory.getLogger(DefaultServiceLcmService.class);

    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.openSession();
    }

    private SOService soService;
    private AAIService aaiService;

    public SotnServiceTemplateServiceImpl() {
        this(RestfulServices.create(SOService.class), RestfulServices.create(AAIService.class));
    }

    public SotnServiceTemplateServiceImpl(SOService soService, AAIService aaiService) {
        this.soService = soService;
        this.aaiService = aaiService;
    }

    @Override
    public FileWrapper getSOTNBandWidthData(String subscriptionType, String instanceid) {
        FileWrapper fileresponse = new FileWrapper();
        ServiceInstance serviceInstance = null;
        ModelConfig modelConfig = ConfigFile.readFile();
        String customerId = modelConfig.getSubscriberId();
        try {
            serviceInstance = getServiceInstancesInfo(customerId, subscriptionType, instanceid);
            if (serviceInstance == null)
                return null;
        } catch (Exception e) {
            logger.info("Query Service Instance information failed. No service information found for customer "
                    + customerId + " and Service Type " + subscriptionType);
            return null;
        }
        FileBean file = new FileBean();
        file.setFileName(serviceInstance.getServiceInstanceName() + ".png");
        fileresponse.setFile(file);
        return fileresponse;
    }


    @Override
    public String getSOTNInstantiationstatus(String instanceid) {
        ServiceInstantiationResponse serviceInstantiationResponse = new ServiceInstantiationResponse();
        ServiceInstance infraInstance = null;
        ServiceInstance siteInstance = null;
        ModelConfig modelConfig = ConfigFile.readFile();
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


                List<RelationshipDatum> pnfrelatedprop = temprel.getRelationshipData();
                for (RelationshipDatum temp : pnfrelatedprop) {
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
            }

            List<Relationship> llrelationlist = logicallink.getRelationshipList().getRelationship().stream().filter(llrelation -> llrelation.getRelatedTo()
                    .equalsIgnoreCase("p-interface")).collect(Collectors.toList());


            String externalnode = "";
            for (Relationship llrel : llrelationlist) {
                externalnode = "";
                List<RelationshipDatum> llrelatedprop = llrel.getRelationshipData();
                for (RelationshipDatum temp : llrelatedprop) {
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
        System.out.println(jsonresponse);
        return resourceResponse.toString();
    }


    @Override
    public String ServiceCost(HttpServletRequest request) {
        ServletInputStream inStream = null;
        SotnServiceTemplateInput SotnServiceTemplateInput = new SotnServiceTemplateInput();
        //SotnServiceTemplateInput SotnServiceTemplateoutput = new SotnServiceTemplateInput();
        try {
            inStream = request.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inStream));
            String requestJson = IOUtils.read(reader);
            SotnServiceTemplateInput = new ObjectMapper().readValue(requestJson, SotnServiceTemplateInput.class);
            logger.info("The request body content is: " + requestJson);
        } catch (IOException e) {
            logger.info("The request body parsing failed." + e.getMessage());
            return null;
        } finally {
            if (inStream != null) {
                try {
                    inStream.close();
                } catch (IOException e) {

                }
            }
        }
        int vpncost = 100;
        int siteCost = 10;
        int premimumvpncost = 200;
        if (SotnServiceTemplateInput.getService().getCos().equalsIgnoreCase("premimum")) {
            vpncost = vpncost + premimumvpncost;
        }
        if (SotnServiceTemplateInput.getService().getHighAvailability().equalsIgnoreCase("true")) {
            vpncost = vpncost + premimumvpncost;
        }
        siteCost = siteCost * SotnServiceTemplateInput.getSites().size();
        List<Map<String, Object>> vpnmapList = new LinkedList<Map<String, Object>>();
        Map<String, Object> vpninformation = new HashMap<String, Object>();
        Map<String, Object> response = new HashMap<>();
        List<Map<String, String>> sitesList = new LinkedList<Map<String, String>>();

        try {
            response = new ObjectMapper().readValue(SotnServiceTemplateInput.getService().toString(), HashMap.class);
        } catch (IOException e) {

        }
        response.remove("startTime");
        response.remove("couplingFlag");
        response.remove("specification");
        response.remove("cbs");
        response.remove("ebs");
        response.remove("dualLink");
        response.remove("endTime");
        response.remove("reRoute");
        response.remove("colorAware");
        Map<String, String> cost = new HashMap<>();
        cost.put("serviceCost", String.valueOf(vpncost + siteCost));
        cost.put("vpnCost", String.valueOf(vpncost));
        cost.put("siteCost", String.valueOf(siteCost));
        response.put("cost", cost);
        vpninformation.put("vpnName", SotnServiceTemplateInput.getService().getName());
        vpninformation.put("vpnType", "Hub-Spoke");


        List<SotnSites> sites = new ArrayList<>();
        sites = SotnServiceTemplateInput.getSites();
        for (SotnSites SotnSite : sites) {
            Map<String, String> site = new HashMap<>();
            site.put("siteName", SotnSite.getSotnSiteName());
            site.put("location", SotnSite.getAddress());
            site.put("zipCode", SotnSite.getZipCode());
            site.put("role", "Hub");
            site.put("description", SotnSite.getDescription());
            sitesList.add(site);
        }
        vpninformation.put("sites", sitesList);
        vpnmapList.add(vpninformation);
        response.put("vpnInformations", vpnmapList);
        String jsonresponse = "";
        try {

            jsonresponse = new ObjectMapper().writeValueAsString(response);
            System.out.println(jsonresponse);
        } catch (IOException e) {

        }
        return jsonresponse;
    }

    @Override
    public ServiceOperation instantiateService(HttpServletRequest request) {
        ServletInputStream inStream = null;
        E2EServiceInstanceRequest requestBody = null;
        SotnServiceTemplateInput SotnServiceTemplateInput = new SotnServiceTemplateInput();

        Response<ServiceOperation> sotnserviceresponse = null;
        Operation sotnservice = null;
        ModelConfig modelConfig = readFile();
        Map<String, Model> modelInfo = readConfigToMap(modelConfig);

        try
        {
            inStream = request.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inStream));
            String requestJson=IOUtils.read(reader);
            SotnServiceTemplateInput = new ObjectMapper().readValue(requestJson, SotnServiceTemplateInput.class);
            logger.info("The request body content is: "+ requestJson);
        }catch(IOException e)
        {
            logger.info("The request body parsing failed."+e.getMessage());
            return null;
        }finally {
            if (inStream != null) {
                try {
                    inStream.close();
                } catch (IOException e) {

                }
            }
        }

        String customerid = modelConfig.getSubscriberId();
        String subscriptionType =modelConfig.getSubscriptionType();

        requestBody=createSotnServiceRequest(SotnServiceTemplateInput,modelInfo, customerid, subscriptionType);
        try
        {
            System.out.println("SO request formed : "+new ObjectMapper().writeValueAsString(requestBody));
        }catch (IOException e)
        {

        }

        ServiceOperation sotnserviceoperation = createSotnService(requestBody);
        sotnservice = sotnserviceoperation.getService();
        logger.info("Began to sleep ");
        try {
            Thread.sleep(20000);
        }catch(Exception e)
        {
            logger.info("sleep Interrupted");
        }

        logger.info("wokeup to sleep ");
        List<SotnSites> sites = SotnServiceTemplateInput.getSites();
        for(SotnSites site : sites)
        {
            requestBody=createSiteServiceRequest(SotnServiceTemplateInput,sotnservice, modelInfo, customerid, subscriptionType, site);
            //Thread siteThread = new Thread(new SoSiteCreateThread(soService, requestBody, aaiService, customerid,subscriptionType,sotnservice.getServiceId()));
            //siteThread.start();
            ServiceOperation siteserviceoperation = createSotnService(requestBody);
        }
        try
        {
            System.out.println("SO request formed : "+new ObjectMapper().writeValueAsString(requestBody));
        }catch (IOException e)
        {

        }
        //ServiceOperation siteserviceoperation = createSotnService(requestBody);
        return sotnserviceoperation;
        //return null;
    }

    private Map<String, Object> hashMapper(Map<String, Object> fMap, Map<String, Object> uMap) throws ParseException {
        for (Map.Entry<String, Object> entry : fMap.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            List<Object> list = new ArrayList<Object> ();

            if (value instanceof String) {
                System.out.println(value);
                if (uMap.containsKey(entry.getKey())) {
                    System.out.println("replace---"+value);
                    fMap.remove(entry.getValue());
                    fMap.put(entry.getKey(), entry.getValue());
                }
            } else if (value instanceof Map) {
                Map<String, Object> subMap = (Map<String, Object>)value;
                hashMapper(subMap, uMap);
            } else if(value instanceof Object[]){
                if (uMap.containsKey(entry.getValue())) {
                    fMap.remove(entry.getValue());
                    fMap.put(entry.getKey(), entry.getValue());
                }
            } else {
                throw new IllegalArgumentException(String.valueOf(value));
            }

        }
        return fMap;
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
            if (serviceInstance == null)
                return null;
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
                System.out.println(jsonresponse);
            } catch (IOException e) {

            }

        } else {
            //This is DWAN service
        }

        //----------------------------- GET SERVICE INSTANCE INFORMATION FROM AAI : END -----------------------------------------
        return jsonresponse;
    }


    @Override
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
                logger.info("Query p interafce to get link status failed for pnfname"
                        + pnfname + " and interface Id " + allottedpinterfaceid);
                return null;
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

            }

        }
        //----------------------------- GET SERVICE INSTANCE INFORMATION FROM AAI : END -----------------------------------------
        return jsonresponse;
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
            //System.out.println("Response received : "+response.body().bytes());
        } else {
            logger.info("Fire get complex Object : Failed");

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
            //System.out.println("Response received : "+response.body().bytes());
        } else {
            logger.info("Fire get site resource : Failed");

        }

        return null;
    }

    private Sites getSites() throws IOException {
        logger.info("Fire get sites : Begin");
        ObjectMapper mapper = new ObjectMapper();
        Response<ResponseBody> response = this.aaiService.getSiteResources().execute();
        if (response.isSuccessful()) {
            logger.info("Fire get sites : End");
            String result = new String(response.body().bytes());
            Sites sites = mapper.readValue(result, Sites.class);
            return sites;
            //System.out.println("Response received : "+response.body().bytes());
        } else {
            logger.info("Fire get sites : Failed");

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
            //System.out.println("Response received : "+response.body().bytes());
        } else {
            logger.info("Fire getServiceInstancesForEdge : Failed");

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
            //System.out.println("Response received : "+response.body().bytes());
        } else {
            logger.info("Fire getServiceInstancesForEdge : Failed");

        }


        return null;
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
            //System.out.println("Response received : "+response.body().bytes());
        } else {
            logger.info("Fire getServiceInstances : Failed");

        }
        return null;
    }

    public ServiceOperation createSotnService(E2EServiceInstanceRequest requestBody) {
        Operation result = new Operation();
        try {
            logger.info("SO instantiate SOTN service is starting");
            Response<ServiceOperation> sotnserviceresponse = soService.instantiateSOTNService(requestBody).execute();
            logger.info("SO instantiate SOTN service has finished");
            if (sotnserviceresponse.isSuccessful()) {
                logger.info("SO instantiate SOTN service is successful");
                //result=sotnserviceresponse.body().getService();
                return sotnserviceresponse.body();
            } else {
                logger.error(String.format("Can not instantiate SOTN service[code=%s, message=%s]", sotnserviceresponse.code(), sotnserviceresponse.message()));
                throw new SOException("SO instantiate SOTN service failed!");
            }
        } catch (Exception e) {
            throw new SOException("SO Service is not available!", e);
        }

    }


    public ServiceOperation createSiteService(E2EServiceInstanceRequest requestBody) {
        try {
            logger.info("SO instantiate SOTN Site service is starting");
            Response<ServiceOperation> siteserviceresponse = soService.instantiateSOTNService(requestBody).execute();
            if (siteserviceresponse.isSuccessful()) {
                logger.info("SO instantiate SOTN site service has finished successfully");
                return siteserviceresponse.body();
            } else {
                logger.error(String.format("Can not instantiate SOTN site service[code=%s, message=%s]", siteserviceresponse.code(), siteserviceresponse.message()));
                throw new SOException("SO instantiate SOTN Site service failed!");
            }
        } catch (Exception e) {
            throw new SOException("SO Service is not available!", e);
        }

    }


    public DeleteOperationRsp deleteService(String serviceId, String subscriptionType, HttpServletRequest request) {
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
                System.out.println(request);
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
            System.out.println(request);
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


    public ServiceInstance getServiceInstancesForEdge(String customerId, String serviceType, String serviceInstanceId) throws Exception {
        logger.info("Fire getServiceInstancesForEdge : Begin");
        ObjectMapper mapper = new ObjectMapper();

        Response<ResponseBody> response = this.aaiService.getServiceInstancesForEdge(customerId, serviceType, serviceInstanceId).execute();
        if (response.isSuccessful()) {
            logger.info("Fire getServiceInstancesForEdge : End");
            String result = new String(response.body().bytes());
            ServiceInstance serviceInstance = mapper.readValue(result, ServiceInstance.class);
            return serviceInstance;
            //System.out.println("Response received : "+response.body().bytes());
        } else {
            logger.info("Fire getServiceInstancesForEdge : Failed");

        }

        return null;
    }


    private E2EServiceInstanceRequest createSotnServiceRequest(SotnServiceTemplateInput request, Map<String, Model> modelInfo,
                                                               String customerid, String subscriptionType) {

        E2EServiceInstanceRequest e2eServiceInstanceRequest = new E2EServiceInstanceRequest();
        E2EService e2eService = new E2EService();
        E2EParameters parameters = new E2EParameters();
        e2eServiceInstanceRequest.setService(e2eService);
        e2eService.setName(request.getService().getName());
        e2eService.setDescription(request.getService().getDescription());

        Model servicemodel = modelInfo.get("sotnservice");
        e2eService.setServiceInvariantUuid(servicemodel.getResourceInvariantUuid());         //Need to get from SDC or Configuration
        e2eService.setServiceUuid(servicemodel.getResourceUuid());
        e2eService.setGlobalSubscriberId(customerid);
        e2eService.setServiceType(subscriptionType);
        e2eService.setParameters(parameters);

        servicemodel = modelInfo.get("connectivity");
        ResourceRequest resource = new ResourceRequest();
        resource.setResourceName(servicemodel.getResourceName());
        resource.setResourceInvariantUuid(servicemodel.getResourceInvariantUuid());
        resource.setResourceUuid(servicemodel.getResourceUuid());
        resource.setResourceCustomizationUuid(servicemodel.getResourceCustomizationUuid());
        resource.setParameters(new E2EParameters());

        List<ResourceRequest> resourceList = new LinkedList<ResourceRequest>();
        resourceList.add(resource);
        e2eService.getParameters().setResources(resourceList);

        HashMap<String, String> requestInputs = new HashMap<>();
        requestInputs.put("name", request.getService().getName());
        requestInputs.put("description", request.getService().getDescription());
        requestInputs.put("vpnType", "ethernet");
        e2eService.getParameters().getResources().get(0).getParameters().setRequestInputs(requestInputs);
        HashMap<String, String> connectivityrequestInputsmap = null;
        try {
            connectivityrequestInputsmap =
                    new ObjectMapper().readValue(request.getService().toString(), HashMap.class);
            String seir = connectivityrequestInputsmap.get("eir");
            String scir = connectivityrequestInputsmap.get("eir");
            seir = seir + "000";
            scir = scir + "000";
            connectivityrequestInputsmap.replace("eir", seir);
            connectivityrequestInputsmap.replace("cir", scir);

        } catch (IOException ex) {
            logger.error(String.format("Converting input request to Map failed."));
        }
        String prefix = resource.getResourceName().replaceAll("\\s", "");
        HashMap<String, String> connectivityrequestInputs = new HashMap<String, String>();
        connectivityrequestInputsmap.forEach((k, v) -> connectivityrequestInputs.put(prefix + "_" + k, v));
        e2eService.getParameters().setRequestInputs(connectivityrequestInputs);
        e2eServiceInstanceRequest.setService(e2eService);
        return e2eServiceInstanceRequest;
    }


    private E2EServiceInstanceRequest createSiteServiceRequest(SotnServiceTemplateInput request, Operation sotnservice,
                                                               Map<String, Model> modelInfo, String customerid, String subscriptionType, SotnSites site) {
        E2EServiceInstanceRequest e2eServiceInstanceRequest = new E2EServiceInstanceRequest();
        E2EService e2eService = new E2EService();
        E2EParameters parameters = new E2EParameters();
        e2eServiceInstanceRequest.setService(e2eService);
        e2eService.setName("Site_Service_" + site.getAddress().trim() + request.getService().getName().trim());
        e2eService.setDescription(request.getService().getDescription());

        Model servicemodel = modelInfo.get("siteservice");
        e2eService.setServiceInvariantUuid(servicemodel.getResourceInvariantUuid());         //Need to get from SDC or Configuration
        e2eService.setServiceUuid(servicemodel.getResourceUuid());
        e2eService.setGlobalSubscriberId(customerid);
        e2eService.setServiceType(subscriptionType);
        e2eService.setParameters(parameters);


        servicemodel = modelInfo.get("site");
        ResourceRequest siteresource = new ResourceRequest();
        siteresource.setResourceName(servicemodel.getResourceName());
        siteresource.setResourceInvariantUuid(servicemodel.getResourceInvariantUuid());
        siteresource.setResourceUuid(servicemodel.getResourceUuid());
        siteresource.setResourceCustomizationUuid(servicemodel.getResourceCustomizationUuid());
        siteresource.setParameters(new E2EParameters());
        HashMap<String, String> requestInputs = new HashMap<>();
        requestInputs.put("location-name", site.getAddress());
        siteresource.getParameters().setRequestInputs(requestInputs);


        servicemodel = modelInfo.get("partner");
        ResourceRequest spresource = new ResourceRequest();
        spresource.setResourceName(servicemodel.getResourceName());
        spresource.setResourceInvariantUuid(servicemodel.getResourceInvariantUuid());
        spresource.setResourceUuid(servicemodel.getResourceUuid());
        spresource.setResourceCustomizationUuid(servicemodel.getResourceCustomizationUuid());
        spresource.setParameters(new E2EParameters());

        servicemodel = modelInfo.get("attachment");
        ResourceRequest atatchresource = new ResourceRequest();
        atatchresource.setResourceName(servicemodel.getResourceName());
        atatchresource.setResourceInvariantUuid(servicemodel.getResourceInvariantUuid());
        atatchresource.setResourceUuid(servicemodel.getResourceUuid());
        atatchresource.setResourceCustomizationUuid(servicemodel.getResourceCustomizationUuid());
        atatchresource.setParameters(new E2EParameters());


        List<ResourceRequest> resourceList = new LinkedList<ResourceRequest>();
        resourceList.add(siteresource);
        resourceList.add(spresource);
        resourceList.add(atatchresource);
        e2eService.getParameters().setResources(resourceList);

        HashMap<String, String> servicerequestInputsmap = null;
        String prefix = "elinesotnsitevf0_elinesotnsitevfc0";

        try {
            servicerequestInputsmap =
                    new ObjectMapper().readValue(site.toString(), HashMap.class);  //Assume that only one site is in SOTN service.
        } catch (IOException ex) {
            logger.error(String.format("Converting input request to Map failed."));
        }

        HashMap<String, String> connectivityrequestInputs = new HashMap<String, String>();
        ;
        servicerequestInputsmap.forEach((k, v) -> connectivityrequestInputs.put(prefix + "_"
                + k, v));
        connectivityrequestInputs.put("elinesotnattachmentvf0_elinesotnattachmentvfc0_clientSignal", "signalValue");
        connectivityrequestInputs.put("elinesotnattachmentvf0_elinesotnattachmentvfc0_cVLAN", site.getVlan());
        connectivityrequestInputs.put("elinesotnattachmentvf0_elinesotnattachmentvfc0_sotnVpnName", request.getService().getName());
        connectivityrequestInputs.put("elinesotnsitevf0_elinesotnsitevfc0_role", "dsvpn-hub");
        e2eService.getParameters().setRequestInputs(connectivityrequestInputs);

        e2eServiceInstanceRequest.setService(e2eService);
        return e2eServiceInstanceRequest;
    }

    private Model getModelInformation(Map<String, Model> modelinfo, String modelType) {

        return modelinfo.get(modelType);
    }


    private Map<String, Model> readConfigToMap(ModelConfig modelConfig) {

        //ModelConfig modelConfig = readFile();
//        Map<String, Model> modelinfo = modelConfig.getResourcemodelinformation().stream()
//                .collect(Collectors.toMap(ModelInfor::getModelType, ModelInfor::getModel));
        return null;
    }



    public ModelConfig readFile() {
        JSONParser parser = new JSONParser();
        String jsonPath = "/home/modelconfig.json";
        String jsonPath_2 = "";
        String jsonString = null;
        ObjectMapper mapper = new ObjectMapper();

        try {

            Object object = parser.parse(new FileReader(jsonPath));
            // System.out.println("mukesh"+object.toString());
            ModelConfig modelInformation = mapper.readValue(object.toString(), new TypeReference<ModelConfig>() {
            });

            return modelInformation;
        } catch (ParseException | IOException ex) {
            logger.error("Exception occured while reading configuration file:" + ex);
            return null;
        }
    }


    public ModelConfig readFile_unni() {
        JSONParser parser = new JSONParser();
        String jsonPath = "/home/modelconfigunni.json";
        String jsonPath_2 = "";
        String jsonString = null;
        ObjectMapper mapper = new ObjectMapper();

        try {

            Object object = parser.parse(new FileReader(jsonPath));
            // System.out.println("mukesh"+object.toString());
            ModelConfig modelInformation = mapper.readValue(object.toString(), new TypeReference<ModelConfig>() {
            });

            return modelInformation;
        } catch (ParseException | IOException ex) {
            logger.error("Exception occured while reading configuration file:" + ex);
            return null;
        }
    }

    public Pinterface getTerminationPoint(String pnfName, String tpId) throws Exception {
        logger.info("Fire getTerminationPoint : Begin");
        ObjectMapper mapper = new ObjectMapper();
        //Response<ResponseBody> response = this.aaiService.getPinterfaceByPnfName(pnfName,tpId).execute();
        Response<ResponseBody> response = this.aaiService.getTerminationPoint(pnfName, tpId).execute();
        if (response.isSuccessful()) {
            logger.info("Fire getTerminationPoint : End");
            String result = new String(response.body().bytes());
            Pinterface pinterface = mapper.readValue(result, Pinterface.class);
            return pinterface;
            //System.out.println("Response received : "+response.body().bytes());
        } else {
            logger.info("Fire getTerminationPoint : Failed");

        }

        return null;
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
            //System.out.println("Response received : "+response.body().bytes());
        } else {
            logger.info("Fire getSOTNPinterfaceByVpnId : Failed");

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
            //System.out.println("Response received : "+response.body().bytes());
        } else {
            logger.info("Fire getSOTNLinkbyName : Failed");

        }

        return null;
    }


    public Pnf getSOTNPnf(String pnfname) throws Exception {

        logger.info("Fire get SOTN PnF by Name : Begin");
        ObjectMapper mapper = new ObjectMapper();
        Response<ResponseBody> response = this.aaiService.getPnfInfo(pnfname).execute();
        if (response.isSuccessful()) {
            logger.info("Fire getSOTNLinkbyName : End");
            String result = new String(response.body().bytes());
            Pnf pnf = mapper.readValue(result, Pnf.class);
            return pnf;
            //System.out.println("Response received : "+response.body().bytes());
        } else {
            logger.info("Fire get SOTN PnF by Name : Failed" + pnfname);

        }

        return null;
    }


// ---------------nre method-------------------------



    public ServiceOperation instantiate_CCVPN_Service(HashMap<String, Object> reqt) {
        ServletInputStream inStream = null;
        E2EServiceInstanceRequest requestBody = null;
        Response<ServiceOperation> sotnserviceresponse = null;
        Operation sotnservice = null;
        ModelConfig modelConfig = readFile_unni();
        Model servicemodel = modelConfig.getServiceModel().getModel();
        //Map<String, Model> modelInfo = readConfigToMap(modelConfig);
        List<ModelInfor> resourceModel = modelConfig.getResourcemodelinformation();

//        try {
//            inStream = request.getInputStream();
//            BufferedReader    reader = new BufferedReader(new InputStreamReader(inStream));
//            String requestJson = IOUtils.read(reader);
//            logger.info("The request body content is: " + requestJson);
//        } catch (IOException e) {
//            logger.info("The request body parsing failed." + e.getMessage());
//            return null;
//        } finally {
//            if (inStream != null) {
//                try {
//                    inStream.close();
//                } catch (IOException e) {
//
//                }
//            }
//        }

        String customerid = modelConfig.getSubscriberId();
        String subscriptionType = modelConfig.getSubscriptionType();

        requestBody = create_CCVPN_Request_Body(reqt, resourceModel, customerid, subscriptionType, servicemodel);
        try {
            System.out.println("SO request formed : " + new ObjectMapper().writeValueAsString(requestBody));
        } catch (IOException e) {

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
        //return null;
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

//
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

}









