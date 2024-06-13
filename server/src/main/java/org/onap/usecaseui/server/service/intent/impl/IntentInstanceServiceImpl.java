/*
 * Copyright (C) 2021 CTC, Inc. and others. All rights reserved.
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
package org.onap.usecaseui.server.service.intent.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.onap.usecaseui.server.bean.csmf.ServiceCreateResult;
import org.onap.usecaseui.server.bean.csmf.SlicingOrder;
import org.onap.usecaseui.server.bean.csmf.SlicingOrderDetail;
import org.onap.usecaseui.server.bean.intent.InstancePerformance;
import org.onap.usecaseui.server.bean.intent.CCVPNInstance;
import org.onap.usecaseui.server.bean.intent.IntentInstance;
import org.onap.usecaseui.server.bean.nsmf.common.ServiceResult;
import org.onap.usecaseui.server.constant.IntentConstant;
import org.onap.usecaseui.server.service.csmf.SlicingService;
import org.onap.usecaseui.server.service.intent.IntentApiService;
import org.onap.usecaseui.server.service.intent.IntentInstanceService;
import org.onap.usecaseui.server.service.lcm.domain.so.SOService;
import org.onap.usecaseui.server.service.nsmf.ResourceMgtService;
import org.onap.usecaseui.server.util.ExportUtil;
import org.onap.usecaseui.server.util.Page;
import org.onap.usecaseui.server.util.RestfulServices;
import org.onap.usecaseui.server.util.UuiCommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;

import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service("IntentInstanceService")
@Transactional
@org.springframework.context.annotation.Configuration
@EnableAspectJAutoProxy
public class IntentInstanceServiceImpl implements IntentInstanceService {
    private static final Logger logger = LoggerFactory.getLogger(IntentInstanceServiceImpl.class);

    @Autowired
    private SessionFactory sessionFactory;

    @Resource(name = "ResourceMgtService")
    private ResourceMgtService resourceMgtService;

    @Resource(name = "SlicingService")
    private SlicingService slicingService;

    private IntentApiService intentApiService;

    private SOService soService;

    private final static int MAX_BANDWIDTH = 6000;
    private final static int MIN_BANDWIDTH = 100;

    private final static List<String> GB_COMPANY = Arrays.asList(new String[] {"gbps", "gb"});
    private final static List<String> MB_COMPANY = Arrays.asList(new String[] {"mbps", "mb"});

    public IntentInstanceServiceImpl() {
        this(RestfulServices.create(IntentApiService.class),RestfulServices.create(SOService.class));
    }
    public IntentInstanceServiceImpl(IntentApiService intentApiService, SOService soService) {
        this.intentApiService = intentApiService;
        this.soService = soService;
    }

    private Session getSession() {
        return sessionFactory.openSession();
    }

    @Override
    public Page<CCVPNInstance> queryIntentInstance(CCVPNInstance instance, int currentPage, int pageSize) {
        Page<CCVPNInstance> page = new Page<CCVPNInstance>();
        int allRow =this.getAllCount(instance,currentPage,pageSize);
        int offset = page.countOffset(currentPage, pageSize);
        Session session = getSession();
        try{
            StringBuffer hql =new StringBuffer("from CCVPNInstance a where deleteState = 0");
            if (null != instance) {
                if(UuiCommonUtil.isNotNullOrEmpty(instance.getInstanceId())) {
                    String ver =instance.getInstanceId();
                    hql.append(" and a.instanceId = '"+ver+"'");
                }
                if(UuiCommonUtil.isNotNullOrEmpty(instance.getJobId())) {
                    String ver =instance.getJobId();
                    hql.append(" and a.jobId = '"+ver+"'");
                }
                if(UuiCommonUtil.isNotNullOrEmpty(instance.getStatus())) {
                    String ver =instance.getStatus();
                    hql.append(" and a.status = '"+ver+"'");
                }
            }
            hql.append(" order by id");
            logger.info("AlarmsHeaderServiceImpl queryIntentInstance: instance={}", instance);
            Query query = session.createQuery(hql.toString());
            query.setFirstResult(offset);
            query.setMaxResults(pageSize);
            List<CCVPNInstance> list= query.list();
            page.setPageNo(currentPage);
            page.setPageSize(pageSize);
            page.setTotalRecords(allRow);
            page.setList(list);
            return page;
        } catch (Exception e) {
            logger.error("exception occurred while performing AlarmsHeaderServiceImpl queryAlarmsHeader. Details:" + e.getMessage());
            return null;
        } finally {
            session.close();
        }
    }


    public int getAllCount(CCVPNInstance instance, int currentPage, int pageSize) {
        Session session = getSession();
        try{
            StringBuffer count=new StringBuffer("select count(*) from CCVPNInstance a where deleteState = 0");
            if (null != instance) {
                if(UuiCommonUtil.isNotNullOrEmpty(instance.getInstanceId())) {
                    String ver =instance.getInstanceId();
                    count.append(" and a.instanceId = '"+ver+"'");
                }
                if(UuiCommonUtil.isNotNullOrEmpty(instance.getJobId())) {
                    String ver =instance.getJobId();
                    count.append(" and a.jobId = '"+ver+"'");
                }
                if(UuiCommonUtil.isNotNullOrEmpty(instance.getStatus())) {
                    String ver =instance.getStatus();
                    count.append(" and a.status = '"+ver+"'");
                }
            }
            Query query = session.createQuery(count.toString());
            long q=(long)query.uniqueResult();
            return (int)q;
        } catch (Exception e) {
            logger.error("exception occurred while performing IntentInstanceServiceImpl getAllCount. Details:" + e.getMessage());
            return -1;
        } finally {
            session.close();
        }
    }

    @Override
    public int createCCVPNInstance(CCVPNInstance instance) {
        Session session = getSession();
        Transaction tx = null;
        try{

            if (null == instance){
                logger.error("instance is null!");
                return 0;
            }
            String jobId = createIntentInstanceToSO(instance);
            if (null == jobId){
                logger.error("create Instance error：jobId is null");
                return 0;
            }
            instance.setJobId(jobId);
            instance.setResourceInstanceId("cll-"+instance.getInstanceId());
            saveIntentInstanceToAAI(null, instance);

            tx = session.beginTransaction();
            session.save(instance);
            tx.commit();
            return 1;
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            logger.error("Details:" + e.getMessage());
            return 0;
        } finally {
            session.close();
        }
    }

    public String createIntentInstanceToSO(CCVPNInstance instance) throws IOException {
        Map<String, Object> params = new HashMap<>();
        params.put("name", instance.getName());
        params.put("modelInvariantUuid", "6790ab0e-034f-11eb-adc1-0242ac120002");
        params.put("modelUuid", "6790ab0e-034f-11eb-adc1-0242ac120002");
        params.put("globalSubscriberId", "IBNCustomer");
        params.put("subscriptionServiceType", "IBN");
        params.put("serviceType", "CLL");
        Map<String, Object> additionalProperties = new HashMap<>();
        additionalProperties.put("enableSdnc", "true");
        additionalProperties.put("serviceInstanceID", "cll-" + instance.getInstanceId());
        List<Map<String, Object>> transportNetworks = new ArrayList<>();
        Map<String, Object> transportNetwork = new HashMap<>();
        transportNetwork.put("id", "");
        Map<String, Object> sla = new HashMap<>();
        sla.put("latency", "2");
        sla.put("maxBandwidth", instance.getAccessPointOneBandWidth());
        List<Map<String, Object>> connectionLinks = new ArrayList<>();
        Map<String, Object> connectionLink = new HashMap<>();
        connectionLink.put("name", "");
        connectionLink.put("transportEndpointA", instance.getAccessPointOneName());
        connectionLink.put("transportEndpointB", instance.getCloudPointName());
        connectionLinks.add(connectionLink);
        if (instance.getProtectStatus() == 1) {
            sla.put("protectionType", instance.getProtectionType());
            connectionLink.put("transportEndpointBProtection", instance.getProtectionCloudPointName());
        }
        transportNetwork.put("sla", sla);
        transportNetwork.put("connectionLinks", connectionLinks);
        transportNetworks.add(transportNetwork);
        additionalProperties.put("transportNetworks", transportNetworks);
        params.put("additionalProperties",additionalProperties);

        okhttp3.RequestBody requestBody = okhttp3.RequestBody.create(okhttp3.MediaType.parse("application/json"), JSON.toJSONString(params));
        Response<JSONObject> response = intentApiService.createIntentInstance(requestBody).execute();
        if (response.isSuccessful()) {
            return response.body().getString("jobId");
        }
        return null;
    }

    @Override
    public void getIntentInstanceProgress() {
        List<CCVPNInstance> instanceList = getInstanceByFinishedFlag("0");
        for (CCVPNInstance instance: instanceList) {
            try {

                int progress = getProgressByJobId(instance);
                instance.setProgress(progress);
                if (progress >=100) {
                    instance.setStatus("1");
                    saveIntentInstanceToAAI(IntentConstant.INTENT_INSTANCE_ID_PREFIX + "-" + instance.getInstanceId(),instance);
                }
            }
            catch (Exception e) {
                logger.info("get progress exception:"+e);
            }
        }
        saveProgress(instanceList);

    }
    @Override
    public void getIntentInstanceCreateStatus() {
        List<CCVPNInstance> instanceList = getInstanceByFinishedFlag("0");
        for (CCVPNInstance instance: instanceList) {
            try {

                int flag = getCreateStatusByJobId(instance);
                if (flag > 0) {
                    instance.setStatus(flag + "");
                    saveIntentInstanceToAAI(IntentConstant.INTENT_INSTANCE_ID_PREFIX + "-" + instance.getInstanceId(),instance);
                }
            }
            catch (Exception e) {
                logger.info("get progress exception:"+e);
            }
        }
        saveProgress(instanceList);

    }

    private void saveProgress(List<CCVPNInstance> instanceList) {
        if(instanceList == null || instanceList.isEmpty()) {
            return;
        }
        Session session = getSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            for (CCVPNInstance instance : instanceList) {
                session.update(instance);
                session.flush();
            }
            tx.commit();
            logger.info("update progress ok");

        } catch (Exception e) {
            if(tx!=null){
                tx.rollback();
            }
            logger.error("update progress exception:"+e);

        } finally {
            session.close();
        }
    }

    private int getProgressByJobId(CCVPNInstance instance) throws IOException {
        Response<JSONObject> response = intentApiService.queryOperationProgress(instance.getResourceInstanceId(), instance.getJobId()).execute();
        logger.debug(response.toString());
        if (response.isSuccessful()) {
            if (response.body().containsKey("operation")) {
                return response.body().getJSONObject("operation").getInteger("progress");
            }
        }
        return -1;
    }

    private int getCreateStatusByJobId(CCVPNInstance instance) throws IOException {
        if (instance == null || instance.getResourceInstanceId() == null) {
            return -1;
        }
        Response<JSONObject> response = intentApiService.getInstanceInfo(instance.getResourceInstanceId()).execute();
        logger.debug(response.toString());
        if (response.isSuccessful()) {
            String status = response.body().getString("orchestration-status");
            if ("created".equals(status)) {
                return 1;
            }
            return 0;
        }
        logger.error("getIntentInstance Create Statue Error:" + response.toString());
        return -1;
    }

    private List<CCVPNInstance> getInstanceByFinishedFlag(String flag) {
        Session session = getSession();
        try{
            StringBuffer sql=new StringBuffer("from CCVPNInstance where deleteState = 0 and status = '" + flag + "'");

            Query query = session.createQuery(sql.toString());
            List<CCVPNInstance> q=(List<CCVPNInstance>) query.list();
            logger.debug(q.toString());
            return q;
        } catch (Exception e) {
            logger.error("exception occurred while performing IntentInstanceServiceImpl getNotFinishedJobId. Details:" + e.getMessage());
            return null;
        } finally {
            session.close();
        }
    }


    @Override
    public List<CCVPNInstance> getFinishedInstanceInfo() {
        Session session = getSession();
        try{
            StringBuffer count=new StringBuffer("from CCVPNInstance where status = '1' and deleteState = 0");

            Query query = session.createQuery(count.toString());
            List<CCVPNInstance> q=(List<CCVPNInstance>) query.list();
            logger.debug(q.toString());
            return q;
        } catch (Exception e) {
            logger.error("exception occurred while performing IntentInstanceServiceImpl getNotFinishedJobId. Details:" + e.getMessage());
            return null;
        } finally {
            session.close();
        }
    }

    @Override
    public void getIntentInstanceBandwidth() throws IOException {
        List<CCVPNInstance> instanceList = getInstanceByFinishedFlag("1");
        for (CCVPNInstance instance : instanceList) {
            String serviceInstanceId = instance.getResourceInstanceId();
            Response<JSONObject> response = intentApiService.getInstanceNetworkInfo(serviceInstanceId).execute();
            if (!response.isSuccessful()) {
                logger.error("get Intent-Instance Bandwidth error:" + response.toString());
                continue;
            }
            JSONObject responseBody = response.body();
            JSONObject allottedResource = responseBody.getJSONObject("allotted-resources").getJSONArray("allotted-resource").getJSONObject(0);
            JSONArray relationshipList = allottedResource.getJSONObject("relationship-list").getJSONArray("relationship");
            String networkPolicyId = null;
            for (int i = 0; i<relationshipList.size();i++) {
                if ("network-policy".equals(relationshipList.getJSONObject(i).getString("related-to"))) {
                    JSONArray datas = relationshipList.getJSONObject(i).getJSONArray("relationship-data");
                    for (int j = 0; j<relationshipList.size();j++) {
                        if ("network-policy.network-policy-id".equals(datas.getJSONObject(j).getString("relationship-key"))) {
                            networkPolicyId = datas.getJSONObject(j).getString("relationship-value");
                            break;
                        }
                    }
                    break;
                }
            }
            if (networkPolicyId== null) {
                logger.error("get network Policy Id exception. serviceInstanceId:" + instance.getResourceInstanceId());
                continue;
            }

            Response<JSONObject> networkPolicyInfoResponse = intentApiService.getInstanceNetworkPolicyInfo(networkPolicyId).execute();
            if (!networkPolicyInfoResponse.isSuccessful()) {
                logger.error("get Intent-Instance networkPolicyInfo error:" + networkPolicyInfoResponse.toString());
                continue;
            }
            JSONObject networkPolicyInfo = networkPolicyInfoResponse.body();
            int maxBandwidth =  networkPolicyInfo.getIntValue("max-bandwidth") * 1000;
            InstancePerformance instancePerformance = new InstancePerformance();
            instancePerformance.setMaxBandwidth(maxBandwidth);
            instancePerformance.setResourceInstanceId(instance.getResourceInstanceId());
            instancePerformance.setJobId(instance.getJobId());
            instancePerformance.setDate(new Date());

            Response<JSONObject> metadatumResponse = intentApiService.getInstanceBandwidth(serviceInstanceId).execute();
            if (!metadatumResponse.isSuccessful()) {
                logger.error("get Intent-Instance metadatum error:" + metadatumResponse.toString());
                continue;
            }else {
                logger.debug("get Intent-Instance metadatum ok: instance id:" + instance.getInstanceId() + ", metadatum info:" + metadatumResponse.toString());
            }
            JSONObject metadatum = metadatumResponse.body();
            JSONArray metadatumArr = metadatum.getJSONArray("metadatum");
            int metaval = -1;
            for (int i = 0; i < metadatumArr.size(); i++) {
                if (metaval == -1 || metaval > metadatumArr.getJSONObject(i).getIntValue("metaval")) {
                    metaval = metadatumArr.getJSONObject(i).getIntValue("metaval");
                }
            }
            instancePerformance.setBandwidth(metaval);

            Session session = getSession();
            Transaction tx = null;
            try{
                tx = session.beginTransaction();
                session.save(instancePerformance);
                tx.commit();
            } catch (Exception e) {
                if(tx!=null){
                    tx.rollback();
                }
                logger.error("Details:" + e.getMessage());
            } finally {
                session.close();
            }


        }
    }

    @Override
    public void deleteIntentInstance(String instanceId) {
        CCVPNInstance result = null;
        Session session = getSession();
        try {

            result = (CCVPNInstance)session.createQuery("from CCVPNInstance where deleteState = 0 and instanceId = :instanceId")
                    .setParameter("instanceId", instanceId).uniqueResult();
            logger.info("get CCVPNInstance OK, id=" + instanceId);

        } catch (Exception e) {
            logger.error("getodel occur exception:"+e);

        } finally {
            session.close();
        }
        try {
            String serviceInstanceId = result.getResourceInstanceId();
            deleteInstanceToSO(serviceInstanceId);
            deleteIntentInstanceToAAI(IntentConstant.INTENT_INSTANCE_ID_PREFIX + "-"+instanceId);
            deleteInstance(result);
        }catch (Exception e) {
            logger.error("delete instance to SO error :" + e);
        }
    }


    private void deleteInstanceToSO(String serviceInstanceId) throws IOException {
        JSONObject params = new JSONObject();
        params.put("serviceInstanceID", serviceInstanceId);
        params.put("globalSubscriberId", "IBNCustomer");
        params.put("subscriptionServiceType", "IBN");
        params.put("serviceType", "CLL");
        JSONObject additionalProperties = new JSONObject();
        additionalProperties.put("enableSdnc", "true");
        params.put("additionalProperties", additionalProperties);
        okhttp3.RequestBody requestBody = okhttp3.RequestBody.create(okhttp3.MediaType.parse("application/json"), JSON.toJSONString(params));
        intentApiService.deleteIntentInstance(requestBody).execute();
    }
    private String deleteInstance(CCVPNInstance instance) {
        Transaction tx = null;
        String result="0";
        Session session = getSession();
        try {
            tx = session.beginTransaction();

            session.delete(instance);
            tx.commit();
            logger.info("delete instance OK, id=" + instance.getInstanceId());

            result="1";
        } catch (Exception e) {
            if(tx!=null){
                tx.rollback();
            }
            logger.error("delete instance occur exception:"+e);

        } finally {
            session.close();
        }
        return result;
    }

    @Override
    public void activeIntentInstance(String instanceId) {
        CCVPNInstance instance = null;
        Session session = getSession();
        Transaction tx = null;
        try {

            instance = (CCVPNInstance)session.createQuery("from CCVPNInstance where deleteState = 0 and instanceId = :instanceId and status = :status")
                    .setParameter("instanceId", instanceId).setParameter("status", "3").uniqueResult();
            logger.info("get instance OK, id=" + instanceId);

            if (null == instance) {
                logger.error("instance is null!");
                return;
            }

            String jobId = createIntentInstanceToSO(instance);
            instance.setStatus("0");
            instance.setJobId(jobId);
            tx = session.beginTransaction();
            session.save(instance);
            tx.commit();

        }catch (Exception e) {
            if(tx!=null){
                tx.rollback();
            }
            logger.error("active instance to SO error :" + e);
        } finally {
            session.close();
        }
    }

    public void invalidIntentInstance(String instanceId) {
        CCVPNInstance instance = null;
        Session session = getSession();
        Transaction tx = null;
        try {
            instance = (CCVPNInstance)session.createQuery("from CCVPNInstance where deleteState = 0 and instanceId = :instanceId")
                    .setParameter("instanceId", instanceId).uniqueResult();
            logger.info("get instance OK, id=" + instanceId);

            if (null == instance) {
                logger.error("instance is null!");
                return;
            }
            deleteInstanceToSO(instance.getInstanceId());
            instance.setStatus("3");
            tx = session.beginTransaction();
            session.save(instance);
            session.flush();
            tx.commit();

        }catch (Exception e) {
            if(tx!=null){
                tx.rollback();
            }
            logger.error("invalid instance to SO error :" + e);
        } finally {
            session.close();
        }
    }

    @Override
    public Map<String, Object> queryInstancePerformanceData(String instanceId) {
        Session session = getSession();
        try {
            String hql = "from CCVPNInstance i, InstancePerformance p where i.resourceInstanceId = p.resourceInstanceId and  i.instanceId = :instanceId and i.deleteState = 0 order by p.date";
            Query query = session.createQuery(hql).setParameter("instanceId", instanceId);
            List<Object[]> queryResult= query.list();
            List<String> date = new ArrayList<>();
            List<Integer> bandwidth = new ArrayList<>();
            List<Integer> maxBandwidth = new ArrayList<>();
            SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for (int i = queryResult.size() > 50? queryResult.size() - 50 : 0; i < queryResult.size(); i++) {
                Object[] o = queryResult.get(i);
                InstancePerformance performance = (InstancePerformance) o[1];
                date.add(ft.format(performance.getDate()));
                bandwidth.add(performance.getBandwidth());
                maxBandwidth.add(performance.getMaxBandwidth());
            }
            Map<String, Object> xAxis = new HashMap<>();
            xAxis.put("data",date);
            Map<String, Object> bandwidthData = new HashMap<>();
            bandwidthData.put("data",bandwidth);
            Map<String, Object> maxBandwidthData = new HashMap<>();
            maxBandwidthData.put("data",maxBandwidth);
            List<Map<String, Object>> series = new ArrayList<>();
            series.add(bandwidthData);
            series.add(maxBandwidthData);

            Map<String, Object> result = new HashMap<>();
            result.put("xAxis", xAxis);
            result.put("series", series);

            return result;
        }catch (Exception e) {
            logger.error("invalid instance to SO error :" + e);
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public Object queryAccessNodeInfo() throws IOException {
        Map<String, Object> result = new HashMap<>();
        List<String> accessNodeList = new ArrayList<>();
        List<String> cloudAccessNodeList = new ArrayList<>();
        Response<JSONObject> response = intentApiService.queryNetworkRoute().execute();
        if (!response.isSuccessful()) {
            logger.error(response.toString());
            throw new RuntimeException("Query Access Node Info Error");
        }
        JSONObject body = response.body();
        JSONArray data = body.getJSONArray("network-route");
        for (int i = 0; i<data.size(); i++) {
            JSONObject nodeInfo = data.getJSONObject(i);
            if ("ROOT".equals(nodeInfo.getString("type"))) {
                cloudAccessNodeList.add(nodeInfo.getString("route-id")+"("+nodeInfo.getString("data-source")+")");
            }
            else {
                accessNodeList.add(nodeInfo.getString("route-id")+"("+nodeInfo.getString("data-source")+")");
            }
            IntentConstant.NetWorkNodeAlias.put(nodeInfo.getString("route-id"), nodeInfo.getString("route-id")+"("+nodeInfo.getString("data-source")+")");
        }
        result.put("accessNodeList",accessNodeList);
        result.put("cloudAccessNodeList",cloudAccessNodeList);
        return result;
    }

    @Override
    public JSONObject getInstanceStatus(JSONArray ids) {
        Session session = getSession();
        try {
            JSONObject result = new JSONObject();
            JSONArray instanceInfos = new JSONArray();
            String hql = "from CCVPNInstance i where i.instanceId in (:ids)";
            Query query = session.createQuery(hql).setParameter("ids", ids);
            List<CCVPNInstance> queryResult= query.list();
            if (queryResult != null && queryResult.size() > 0) {
                for (CCVPNInstance instance : queryResult) {
                    JSONObject instanceInfo = new JSONObject();
                    instanceInfo.put("id", instance.getInstanceId());
                    instanceInfo.put("status", instance.getStatus());
                    instanceInfos.add(instanceInfo);
                }
            }
            result.put("IntentInstances",instanceInfos);
            return result;

        } catch (Exception e) {
            logger.error("get Instance status error : " + e.getMessage());
            throw e;
        } finally {
            session.close();
        }
    }


    public String formatBandwidth(String strValue) {
        String ret;
        Pattern pattern = Pattern.compile("(\\d+)([\\w ]*)");
        Matcher matcher = pattern.matcher(strValue);


        int dataRate = 100;
        if (matcher.matches()) {
            dataRate = Integer.parseInt(matcher.group(1));
            String company = matcher.group(2).trim().toLowerCase();
            if (GB_COMPANY.contains(company)) {
                dataRate = dataRate * 1000;
            }
            else if (!MB_COMPANY.contains(company)) {
                dataRate = 100;
            }
            dataRate = dataRate < MIN_BANDWIDTH ? MIN_BANDWIDTH : (dataRate > MAX_BANDWIDTH ? MAX_BANDWIDTH : dataRate);
        }
        ret = dataRate + "";
        return ret;
    }


    public String formatCloudPoint(String cloudPoint) {
        String cloudPointAlias = "";
        switch (cloudPoint) {
            case "Cloud one" :
                cloudPointAlias = "tranportEp_dst_ID_212_1";
                break;
        }
        return cloudPointAlias;
    }

    public String formatAccessPoint(String accessPoint) {
        String accessPointAlias = "";
        switch (accessPoint) {
            case "Access one" :
                accessPointAlias = "tranportEp_src_ID_111_1";
                break;
            case "Access two" :
                accessPointAlias = "tranportEp_src_ID_111_2";
                break;
            case "Access three" :
                accessPointAlias = "tranportEp_src_ID_113_1";
                break;
        }
        return accessPointAlias;
    }

    public void addCustomer() throws IOException {
        Properties environment = getProperties();
        String globalCustomerId = environment.getProperty("ccvpn.globalCustomerId");
        Response<JSONObject> queryCustomerResponse = intentApiService.queryCustomer(globalCustomerId).execute();
        if (queryCustomerResponse.isSuccessful()) {
            return;
        }
        String subscriberName = environment.getProperty("ccvpn.subscriberName");
        String subscriberType = environment.getProperty("ccvpn.subscriberType");
        Map<String, Object> params = new HashMap<>();
        params.put("global-customer-id", globalCustomerId);
        params.put("subscriber-name", subscriberName);
        params.put("subscriber-type", subscriberType);
        okhttp3.RequestBody requestBody = okhttp3.RequestBody.create(okhttp3.MediaType.parse("application/json"), JSON.toJSONString(params));
        intentApiService.addCustomer(globalCustomerId, requestBody).execute();
    }

    @Override
    public IntentInstance createIntentInstance(Object body,String businessInstanceId, String businessInstance, String type) {
        IntentInstance instance = new IntentInstance();
        if (IntentConstant.MODEL_TYPE_CCVPN.equals(type)) {
            assembleIntentInstanceFormCCVPNInfo(instance, body);
        }
        else if (IntentConstant.MODEL_TYPE_5GS.equals(type)) {
            assembleIntentInstanceFormSliceInfo(instance, body);
        }
        instance.setIntentSource(type);
        instance.setBusinessInstanceId(businessInstanceId);
        instance.setBusinessInstance(businessInstance);
        Session session = getSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            session.save(instance);
            tx.commit();
            return instance;
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            logger.error("createIntentInstance Details:" + e.getMessage());
            return null;
        } finally {
            session.close();
        }
    }

    private IntentInstance assembleIntentInstanceFormCCVPNInfo(IntentInstance instance, Object body) {
        JSONObject jsonObject = new JSONObject((Map) body);
        String intent_content = jsonObject.getString("intentContent");
        jsonObject.remove("intentContent");
        instance.setIntentConfig(jsonObject.toJSONString());
        instance.setIntentContent(intent_content);
        instance.setIntentName(jsonObject.getString("name"));
        return instance;
    }

    private IntentInstance assembleIntentInstanceFormSliceInfo(IntentInstance instance, Object body) {
        if(body instanceof Map){
            Map map = (Map) body;
            JSONObject jsonObject = new JSONObject(map);
            JSONObject slicingOrderInfo = jsonObject.getJSONObject("slicing_order_info");
            String intent_content = slicingOrderInfo.getString("intentContent");
            slicingOrderInfo.remove("intentContent");
            instance.setIntentConfig(slicingOrderInfo.toJSONString());
            instance.setIntentContent(intent_content);
            instance.setIntentName(slicingOrderInfo.getString("name"));
            return instance;
        }
        return new IntentInstance();
    }


    @Override
    public void deleteIntent(int id) {
        Transaction tx = null;
        Session session = getSession();
        try {
            IntentInstance intentInstance = (IntentInstance)session.createQuery("from IntentInstance where id = :id")
                    .setParameter("id", id).uniqueResult();
            if (IntentConstant.MODEL_TYPE_CCVPN.equals(intentInstance.getIntentSource())) {
                deleteIntentInstance(intentInstance.getBusinessInstanceId());
            } else {
                resourceMgtService.terminateSlicingService(intentInstance.getBusinessInstanceId());
            }


            tx = session.beginTransaction();
            session.delete(intentInstance);
            tx.commit();
            logger.info("delete IntentInstance OK, id=" + intentInstance.getId());
        } catch (Exception e) {
            if(tx!=null){
                tx.rollback();
            }
            logger.error("delete IntentInstance occur exception:"+e);

        } finally {
            session.close();
        }
    }

    @Override
    public void verifyIntent(int id) {
        Session session = getSession();
        IntentInstance instance = new IntentInstance();
        try {
            String hql = "from IntentInstance where id = :id";
            Query query = session.createQuery(hql).setParameter("id", id);
            instance = (IntentInstance) query.uniqueResult();

        } catch (Exception e) {
            logger.error("verifyIntentInstance error. Details:" + e.getMessage());
        } finally {
            session.close();
        }
    }

    @Override
    public Page<IntentInstance> getIntentInstanceList(int currentPage, int pageSize) {
        Page<IntentInstance> page = new Page<IntentInstance>();
        int allRow = getIntentInstanceAllCount();
        int offset = page.countOffset(currentPage, pageSize);
        Session session = getSession();
        try{
            String hql = "from IntentInstance order by id";
            Query query = session.createQuery(hql);
            query.setFirstResult(offset);
            query.setMaxResults(pageSize);
            List<IntentInstance> list= query.list();
            page.setPageNo(currentPage);
            page.setPageSize(pageSize);
            page.setTotalRecords(allRow);
            page.setList(list);
            return page;
        } catch (Exception e) {
            logger.error("exception occurred while performing IntentInstanceServiceImpl getIntentInstanceList. Details:" + e.getMessage());
            return null;
        } finally {
            session.close();
        }
    }

    @Override
    public ServiceResult createSlicingServiceWithIntent(Object slicingOrderBody) {

        SlicingOrder slicingOrder = JSONObject.parseObject(JSONObject.toJSONString(slicingOrderBody), SlicingOrder.class);
        ServiceResult serviceResult = slicingService.createSlicingService(slicingOrder);
        ServiceCreateResult createResult = (ServiceCreateResult) serviceResult.getResult_body();
        try {
            saveSlicingServiceToAAI(createResult.getService_id(), createResult.getOperation_id(), slicingOrder);
        } catch (IOException e) {
            logger.error("save 5g slice to AAI fail!");
            throw new RuntimeException("save 5g slice to AAI fail!", e);
        }
        createIntentInstance(slicingOrderBody,createResult.getService_id(), slicingOrder.getSlicing_order_info().getName(), IntentConstant.MODEL_TYPE_5GS);
        return serviceResult;
    }

    @Override
    public int updateCCVPNInstance(CCVPNInstance instance) {
        Session session = getSession();
        Transaction tx = null;
        try{
            if (null == instance){
                logger.error("instance is null!");
                return 0;
            }
            instance.setResourceInstanceId("cll-"+instance.getInstanceId());

            CCVPNInstance ccvpnInstance = (CCVPNInstance)session.createQuery("from CCVPNInstance where instanceId = :instanceId")
                    .setParameter("instanceId", instance.getInstanceId()).uniqueResult();
            ccvpnInstance.setAccessPointOneBandWidth(instance.getAccessPointOneBandWidth());
            saveIntentInstanceToAAI(IntentConstant.INTENT_INSTANCE_ID_PREFIX + "-" + ccvpnInstance.getInstanceId(), ccvpnInstance);

            tx = session.beginTransaction();
            session.update(ccvpnInstance);
            tx.commit();
            return 1;
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            logger.error("Details:" + e.getMessage());
            return 0;
        } finally {
            session.close();
        }
    }

    public int getIntentInstanceAllCount() {
        Session session = getSession();
        try{
            String count="select count(*) from IntentInstance";
            Query query = session.createQuery(count);
            long q=(long)query.uniqueResult();
            return (int)q;
        } catch (Exception e) {
            logger.error("exception occurred while performing IntentInstanceServiceImpl getAllCount. Details:" + e.getMessage());
            return -1;
        } finally {
            session.close();
        }
    }

    public void addSubscription() throws IOException {
        Properties environment = getProperties();
        String globalCustomerId = environment.getProperty("ccvpn.globalCustomerId");
        String serviceType = environment.getProperty("ccvpn.serviceType");
        Response<JSONObject> querySubscription = intentApiService.querySubscription(globalCustomerId, serviceType).execute();
        if (querySubscription.isSuccessful()) {
            return;
        }
        Map<String, Object> params = new HashMap<>();
        params.put("service-type", serviceType);
        okhttp3.RequestBody requestBody = okhttp3.RequestBody.create(okhttp3.MediaType.parse("application/json"), JSON.toJSONString(params));
        intentApiService.addSubscription(globalCustomerId, serviceType, requestBody).execute();
    }

    public Properties getProperties() throws IOException {
        String slicingPath = System.getProperty("user.dir") + File.separator + "config" + File.separator + "ccvpn.properties";
        InputStream inputStream = new FileInputStream(new File(slicingPath));
        Properties environment = new Properties();
        environment.load(inputStream);
        return environment;
    }


    public void saveIntentInstanceToAAI(String serviceInstanceId, CCVPNInstance instance) throws IOException {
        addCustomer();
        addSubscription();
        Properties environment = getProperties();
        String globalCustomerId = environment.getProperty("ccvpn.globalCustomerId");
        String serviceType = environment.getProperty("ccvpn.serviceType");
        String resourceVersion = null;
        if (serviceInstanceId != null) {
            Response<JSONObject> queryServiceInstance = intentApiService.queryServiceInstance(globalCustomerId, serviceType, serviceInstanceId).execute();
            if (queryServiceInstance.isSuccessful()) {
                JSONObject body = queryServiceInstance.body();
                resourceVersion  = body.getString("resource-version");
            }
        } else {
            serviceInstanceId = IntentConstant.INTENT_INSTANCE_ID_PREFIX + "-" + instance.getInstanceId();
        }
        JSONObject environmentContext = JSONObject.parseObject(JSONObject.toJSONString(instance));
        environmentContext.put("resourceInstanceId",instance.getResourceInstanceId());

        Map<String, Object> params = new HashMap<>();
        params.put("service-instance-id", serviceInstanceId);
        params.put("service-instance-name", instance.getName());
        params.put("service-type", IntentConstant.MODEL_TYPE_CCVPN);
        params.put("environment-context", environmentContext.toJSONString());
        params.put("service-instance-location-id", instance.getResourceInstanceId());
        params.put("bandwidth-total", instance.getAccessPointOneBandWidth());
        params.put("data-owner", IntentConstant.INTENT_INSTANCE_DATA_OWNER);
        if (resourceVersion != null) {
            params.put("resource-version",resourceVersion);
        }
        okhttp3.RequestBody requestBody = okhttp3.RequestBody.create(okhttp3.MediaType.parse("application/json"), JSON.toJSONString(params));
        intentApiService.saveServiceInstance(globalCustomerId,serviceType,serviceInstanceId,requestBody).execute();

    }
    public void deleteIntentInstanceToAAI(String serviceInstanceId) throws IOException {
        addCustomer();
        addSubscription();
        Properties environment = getProperties();
        String globalCustomerId = environment.getProperty("ccvpn.globalCustomerId");
        String serviceType = environment.getProperty("ccvpn.serviceType");
        if (serviceInstanceId == null) {
            return;
        }
        Response<JSONObject> queryServiceInstance = intentApiService.queryServiceInstance(globalCustomerId, serviceType, serviceInstanceId).execute();
        if (queryServiceInstance.isSuccessful()) {
            JSONObject body = queryServiceInstance.body();
            String resourceVersion  = body.getString("resource-version");
            intentApiService.deleteServiceInstance(globalCustomerId,serviceType,serviceInstanceId,resourceVersion).execute();
        }
    }

    @Override
    public void saveSlicingServiceToAAI(String serviceId, String operationId, SlicingOrder slicingOrder) throws IOException {
        addCustomer();
        addSubscription();
        Properties environment = getProperties();
        String globalCustomerId = environment.getProperty("ccvpn.globalCustomerId");
        String serviceType = environment.getProperty("ccvpn.serviceType");
        SlicingOrderDetail slicingOrderInfo = slicingOrder.getSlicing_order_info();
        JSONObject environmentContext = JSONObject.parseObject(JSONObject.toJSONString(slicingOrderInfo));

        Map<String, Object> params = new HashMap<>();
        params.put("service-instance-id", serviceId);
        params.put("service-instance-name", slicingOrderInfo.getName());
        params.put("service-type", IntentConstant.MODEL_TYPE_5GS);
        params.put("environment-context", environmentContext.toJSONString());
        params.put("service-operation-id", operationId);
        params.put("data-rate-uplink", slicingOrderInfo.getExpDataRateUL());
        params.put("data-rate-downlink", slicingOrderInfo.getExpDataRateDL());
        params.put("latency", slicingOrderInfo.getLatency());
        params.put("max-number-of-ues", slicingOrderInfo.getMaxNumberofUEs());
        params.put("mobility", slicingOrderInfo.getUEMobilityLevel());
        params.put("resource-sharing-level", slicingOrderInfo.getResourceSharingLevel());
        params.put("data-owner", IntentConstant.INTENT_INSTANCE_DATA_OWNER);
        okhttp3.RequestBody requestBody = okhttp3.RequestBody.create(okhttp3.MediaType.parse("application/json"), JSON.toJSONString(params));
        intentApiService.saveServiceInstance(globalCustomerId,serviceType,serviceId,requestBody).execute();
    }

    @Override
    public void exportIntentContent(HttpServletResponse response) {
        Session session = getSession();
        try{
            String hql = "select i.intentContent from IntentInstance i order by id";
            Query<String> query = session.createQuery(hql, String.class);
            query.setMaxResults(1000);
            List<String> intentContents = query.getResultList();
            ExportUtil.exportExcel(response,"intentContent",intentContents);
        } catch (Exception e) {
            logger.error("An exception occurred with exportIntentContent. Details:" + e.getMessage());
        } finally {
            session.close();
        }
    }

}
