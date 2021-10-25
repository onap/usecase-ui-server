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
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.onap.usecaseui.server.bean.intent.InstancePerformance;
import org.onap.usecaseui.server.bean.intent.IntentInstance;
import org.onap.usecaseui.server.service.intent.IntentApiService;
import org.onap.usecaseui.server.service.intent.IntentInstanceService;
import org.onap.usecaseui.server.service.lcm.domain.so.SOService;
import org.onap.usecaseui.server.service.lcm.domain.so.bean.OperationProgressInformation;
import org.onap.usecaseui.server.util.Page;
import org.onap.usecaseui.server.util.RestfulServices;
import org.onap.usecaseui.server.util.UuiCommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Service;
import retrofit2.Response;

import javax.transaction.Transactional;
import java.io.IOException;
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
    public Page<IntentInstance> queryIntentInstance(IntentInstance intentInstance, int currentPage, int pageSize) {
        Page<IntentInstance> page = new Page<IntentInstance>();
        int allRow =this.getAllCount(intentInstance,currentPage,pageSize);
        int offset = page.countOffset(currentPage, pageSize);
        Session session = getSession();
        try{
            StringBuffer hql =new StringBuffer("from IntentInstance a where deleteState = 0");
            if (null != intentInstance) {
                if(UuiCommonUtil.isNotNullOrEmpty(intentInstance.getInstanceId())) {
                    String ver =intentInstance.getInstanceId();
                    hql.append(" and a.instance_id = '"+ver+"'");
                }
                if(UuiCommonUtil.isNotNullOrEmpty(intentInstance.getJobId())) {
                    String ver =intentInstance.getJobId();
                    hql.append(" and a.job_id = '"+ver+"'");
                }
                if(UuiCommonUtil.isNotNullOrEmpty(intentInstance.getStatus())) {
                    String ver =intentInstance.getStatus();
                    hql.append(" and a.status = '"+ver+"'");
                }
            }
            hql.append(" order by id");
            logger.info("AlarmsHeaderServiceImpl queryIntentInstance: intentInstance={}", intentInstance);
            Query query = session.createQuery(hql.toString());
            query.setFirstResult(offset);
            query.setMaxResults(pageSize);
            List<IntentInstance> list= query.list();
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


    public int getAllCount(IntentInstance intentInstance,int currentPage,int pageSize) {
        Session session = getSession();
        try{
            StringBuffer count=new StringBuffer("select count(*) from IntentInstance a where deleteState = 0");
            if (null != intentInstance) {
                if(UuiCommonUtil.isNotNullOrEmpty(intentInstance.getInstanceId())) {
                    String ver =intentInstance.getInstanceId();
                    count.append(" and a.instance_id = '"+ver+"'");
                }
                if(UuiCommonUtil.isNotNullOrEmpty(intentInstance.getJobId())) {
                    String ver =intentInstance.getJobId();
                    count.append(" and a.job_id = '"+ver+"'");
                }
                if(UuiCommonUtil.isNotNullOrEmpty(intentInstance.getStatus())) {
                    String ver =intentInstance.getStatus();
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
    public int createIntentInstance(IntentInstance intentInstance) {
        Session session = getSession();
        Transaction tx = null;
        try{

            if (null == intentInstance){
                logger.error("intentInstance is null!");
                return 0;
            }
            String jobId = createIntentInstanceToSO(intentInstance);
            if (null == jobId){
                logger.error("create Instance error：jobId is null");
                return 0;
            }
            intentInstance.setJobId(jobId);
            intentInstance.setResourceInstanceId("cll-"+intentInstance.getInstanceId());

            tx = session.beginTransaction();
            session.save(intentInstance);
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

    private String createIntentInstanceToSO(IntentInstance intentInstance) throws IOException {
        Map<String, Object> params = new HashMap<>();
        params.put("name", intentInstance.getName());
        params.put("modelInvariantUuid", "6790ab0e-034f-11eb-adc1-0242ac120002");
        params.put("modelUuid", "6790ab0e-034f-11eb-adc1-0242ac120002");
        params.put("globalSubscriberId", "IBNCustomer");
        params.put("subscriptionServiceType", "IBN");
        params.put("serviceType", "CLL");
        Map<String, Object> additionalProperties = new HashMap<>();
        additionalProperties.put("enableSdnc", "false");
        additionalProperties.put("serviceInstanceID", "cll-" + intentInstance.getInstanceId());
        List<Map<String, Object>> transportNetworks = new ArrayList<>();
        Map<String, Object> transportNetwork = new HashMap<>();
        transportNetwork.put("id", "");
        Map<String, Object> sla = new HashMap<>();
        sla.put("latency", "2");
        sla.put("maxBandwidth", intentInstance.getAccessPointOneBandWidth());
        List<Map<String, Object>> connectionLinks = new ArrayList<>();
        Map<String, Object> connectionLink = new HashMap<>();
        connectionLink.put("name", "");
        connectionLink.put("transportEndpointA", intentInstance.getAccessPointOneName());
        connectionLink.put("transportEndpointB", intentInstance.getCloudPointName());
        connectionLinks.add(connectionLink);
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
        List<IntentInstance> instanceList = getInstanceByFinishedFlag("0");
        for (IntentInstance instance: instanceList) {
            try {

                int progress = getProgressByJobId(instance);
                if (progress >=100) {
                    instance.setStatus("1");
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
        List<IntentInstance> instanceList = getInstanceByFinishedFlag("0");
        for (IntentInstance instance: instanceList) {
            try {

                int flag = getCreateStatusByJobId(instance);
                if (flag > 0) {
                    instance.setStatus(flag + "");
                }
            }
            catch (Exception e) {
                logger.info("get progress exception:"+e);
            }
        }
        saveProgress(instanceList);

    }

    private void saveProgress(List<IntentInstance> instanceList) {
        if(instanceList == null || instanceList.isEmpty()) {
            return;
        }
        Session session = getSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            for (IntentInstance instance : instanceList) {
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

    private int getProgressByJobId(IntentInstance instance) throws IOException {
        Response<OperationProgressInformation> response = soService.queryOperationProgress(instance.getResourceInstanceId(), instance.getJobId()).execute();
        logger.debug(response.toString());
        if (response.isSuccessful()) {
            return response.body().getOperationStatus().getProgress();
        }
        return -1;
    }

    private int getCreateStatusByJobId(IntentInstance instance) throws IOException {
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

    private List<IntentInstance> getInstanceByFinishedFlag(String flag) {
        Session session = getSession();
        try{
            StringBuffer sql=new StringBuffer("from IntentInstance where deleteState = 0 and status = '" + flag + "'");

            Query query = session.createQuery(sql.toString());
            List<IntentInstance> q=(List<IntentInstance>) query.list();
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
    public List<IntentInstance> getFinishedInstanceInfo() {
        Session session = getSession();
        try{
            StringBuffer count=new StringBuffer("from IntentInstance where status = '1' and deleteState = 0");

            Query query = session.createQuery(count.toString());
            List<IntentInstance> q=(List<IntentInstance>) query.list();
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
        List<IntentInstance> instanceList = getInstanceByFinishedFlag("1");
        for (IntentInstance instance : instanceList) {
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
            int maxBandwidth =  networkPolicyInfo.getIntValue("max-bandwidth");
            InstancePerformance instancePerformance = new InstancePerformance();
            instancePerformance.setMaxBandwidth(maxBandwidth);
            instancePerformance.setResourceInstanceId(instance.getResourceInstanceId());
            instancePerformance.setJobId(instance.getJobId());
            instancePerformance.setDate(new Date());

            Response<JSONObject> metadatumResponse = intentApiService.getInstanceBandwidth(serviceInstanceId).execute();
            if (!metadatumResponse.isSuccessful()) {
                logger.error("get Intent-Instance metadatum error:" + metadatumResponse.toString());
                continue;
            }
            JSONObject metadatum = metadatumResponse.body();
            int metaval = metadatum.getJSONArray("metadatum").getJSONObject(0).getIntValue("metaval");
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
        IntentInstance result = null;
        Session session = getSession();
        try {

            result = (IntentInstance)session.createQuery("from IntentInstance where deleteState = 0 and instanceId = :instanceId")
                    .setParameter("instanceId", instanceId).uniqueResult();
            logger.info("get IntentInstance OK, id=" + instanceId);

        } catch (Exception e) {
            logger.error("getodel occur exception:"+e);

        } finally {
            session.close();
        }
        try {
            String serviceInstanceId = result.getResourceInstanceId();
            deleteInstanceToSO(serviceInstanceId);
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
        additionalProperties.put("enableSdnc", "false");
        params.put("additionalProperties", additionalProperties);
        okhttp3.RequestBody requestBody = okhttp3.RequestBody.create(okhttp3.MediaType.parse("application/json"), JSON.toJSONString(params));
        intentApiService.deleteIntentInstance(requestBody).execute();
    }
    private String deleteInstance(IntentInstance instance) {
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
        IntentInstance instance = null;
        Session session = getSession();
        Transaction tx = null;
        try {

            instance = (IntentInstance)session.createQuery("from IntentInstance where deleteState = 0 and instanceId = :instanceId and status = :status")
                    .setParameter("instanceId", instanceId).setParameter("status", "3").uniqueResult();
            logger.info("get instance OK, id=" + instanceId);

            if (null == instance) {
                logger.error("intentInstance is null!");
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
        IntentInstance instance = null;
        Session session = getSession();
        Transaction tx = null;
        try {
            instance = (IntentInstance)session.createQuery("from IntentInstance where deleteState = 0 and instanceId = :instanceId")
                    .setParameter("instanceId", instanceId).uniqueResult();
            logger.info("get instance OK, id=" + instanceId);

            if (null == instance) {
                logger.error("intentInstance is null!");
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
            String hql = "from IntentInstance i, InstancePerformance p where i.resourceInstanceId = p.resourceInstanceId and  i.instanceId = :instanceId and i.deleteState = 0 order by p.date";
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
                cloudAccessNodeList.add(nodeInfo.getString("route-id"));
            }
            else {
                accessNodeList.add(nodeInfo.getString("route-id"));
            }
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
            String hql = "from IntentInstance i where i.instanceId in (:ids)";
            Query query = session.createQuery(hql).setParameter("ids", ids);
            List<IntentInstance> queryResult= query.list();
            if (queryResult != null && queryResult.size() > 0) {
                for (IntentInstance instance : queryResult) {
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
}
