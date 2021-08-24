/*
 * Copyright (C) 2017 CTC, Inc. and others. All rights reserved.
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
            StringBuffer hql =new StringBuffer("from IntentInstance a where 1=1");
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
        }
    }


    public int getAllCount(IntentInstance intentInstance,int currentPage,int pageSize) {
        Session session = getSession();
        try{
            StringBuffer count=new StringBuffer("select count(*) from IntentInstance a where 1=1");
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
            count.append(" order by id");
            Query query = session.createQuery(count.toString());
            long q=(long)query.uniqueResult();
            return (int)q;
        } catch (Exception e) {
            logger.error("exception occurred while performing IntentInstanceServiceImpl getAllCount. Details:" + e.getMessage());
            return -1;
        }
    }

    @Override
    public int createIntentInstance(IntentInstance intentInstance) {
        try{

            if (null == intentInstance){
                logger.error("intentInstance is null!");
                return 0;
            }
            String jobId = createIntentInstanceToSO(intentInstance);
            intentInstance.setJobId(jobId);
            intentInstance.setResourceInstanceId("cll-"+intentInstance.getInstanceId());
            Session session = getSession();
            Transaction tx = session.beginTransaction();
            session.save(intentInstance);
            tx.commit();
//            session.flush();
            return 1;
        } catch (Exception e) {
            logger.error("Details:" + e.getMessage());
            return 0;
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
                instance.setProgress(progress);
            }
            catch (Exception e) {
                logger.info("get progress exception:"+e);
            }
        }
        saveProgress(instanceList);

    }

    private void saveProgress(List<IntentInstance> instanceList) {
        Transaction tx = null;
        if(instanceList == null || instanceList.isEmpty()) {
            return;
        }
        try(Session session = getSession()) {
            tx = session.beginTransaction();
            for (IntentInstance instance : instanceList) {
                session.save(instance);
            }
            tx.commit();
            logger.info("update progress ok");

        } catch (Exception e) {
            if(tx!=null){
                tx.rollback();
            }
            logger.error("update progress exception:"+e);

        }
    }

    private int getProgressByJobId(IntentInstance instance) throws IOException {
        Response<OperationProgressInformation> response = soService.queryOperationProgress(instance.getResourceInstanceId(), instance.getJobId()).execute();
        return response.body().getOperationStatus().getProgress();
    }

    private List<IntentInstance> getInstanceByFinishedFlag(String flag) {
        Session session = getSession();
        try{
            StringBuffer sql=new StringBuffer("from IntentInstance where status = '" + flag + "'");

            Query query = session.createQuery(sql.toString());
            List<IntentInstance> q=(List<IntentInstance>) query.list();
            logger.debug(q.toString());
            return q;
        } catch (Exception e) {
            logger.error("exception occurred while performing IntentInstanceServiceImpl getNotFinishedJobId. Details:" + e.getMessage());
            return null;
        }
    }


    @Override
    public List<IntentInstance> getFinishedInstanceInfo() {
        Session session = getSession();
        try{
            StringBuffer count=new StringBuffer("from IntentInstance where status = '1'");

            Query query = session.createQuery(count.toString());
            List<IntentInstance> q=(List<IntentInstance>) query.list();
            logger.debug(q.toString());
            return q;
        } catch (Exception e) {
            logger.error("exception occurred while performing IntentInstanceServiceImpl getNotFinishedJobId. Details:" + e.getMessage());
            return null;
        }
    }

    @Override
    public void getIntentInstanceBandwidth() throws IOException {
        List<IntentInstance> instanceList = getInstanceByFinishedFlag("1");
        for (IntentInstance instance : instanceList) {
            String serviceInstanceId = instance.getResourceInstanceId();
            Response<JSONObject> response = intentApiService.getInstanceNetworkInfo(serviceInstanceId).execute();
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
            JSONObject networkPolicyInfo = intentApiService.getInstanceNetworkPolicyInfo(networkPolicyId).execute().body();
            String maxBandwidth =  networkPolicyInfo.getString("max-bandwidth");
            InstancePerformance instancePerformance = new InstancePerformance();
            instancePerformance.setMaxBandwidth(maxBandwidth);
            instancePerformance.setResourceInstanceId(instance.getResourceInstanceId());
            instancePerformance.setJobId(instance.getJobId());
            instancePerformance.setDate(new Date());

            JSONObject metadatum = intentApiService.getInstanceBandwidth(serviceInstanceId).execute().body();
            String metaval = metadatum.getJSONArray("metadatum").getJSONObject(0).getString("metaval");
            instancePerformance.setBandwidth(metaval);

            try{
                Session session = getSession();
                Transaction tx = session.beginTransaction();
                session.save(instancePerformance);
                tx.commit();
            } catch (Exception e) {
                logger.error("Details:" + e.getMessage());
            }


        }
    }

    @Override
    public void deleteIntentInstance(String instanceId) {
        IntentInstance result = null;

        try(Session session = getSession()) {

            result = (IntentInstance)session.createQuery("from IntentInstance where instanceId = :instanceId")
                    .setParameter("instanceId", instanceId).uniqueResult();
            logger.info("get IntentInstance OK, id=" + instanceId);

        } catch (Exception e) {
            logger.error("getodel occur exception:"+e);

        }
        try {
            String serviceInstanceId = result.getResourceInstanceId();
            deleteInstanceToSO(serviceInstanceId);
            deleteInstance(serviceInstanceId);
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
    private String deleteInstance(String serviceInstanceId) {
        Transaction tx = null;
        String result="0";
        if(serviceInstanceId==null || serviceInstanceId.trim().equals(""))
            return  result;

        try(Session session = getSession()) {
            tx = session.beginTransaction();

            IntentInstance instance = new IntentInstance();
            instance.setInstanceId(serviceInstanceId);
            session.delete(instance);
            tx.commit();
            logger.info("delete instance OK, id=" + serviceInstanceId);

            result="1";
        } catch (Exception e) {
            if(tx!=null){
                tx.rollback();
            }
            logger.error("delete instance occur exception:"+e);

        }
        return result;
    }

    @Override
    public void activeIntentInstance(String instanceId) {
        IntentInstance instance = null;

        try(Session session = getSession()) {

            instance = (IntentInstance)session.createQuery("from IntentInstance where instanceId = :instanceId and status = :status")
                    .setParameter("instanceId", instanceId).setParameter("status", "3").uniqueResult();
            logger.info("get instance OK, id=" + instanceId);

        } catch (Exception e) {
            logger.error("getodel occur exception:"+e);

        }
        if (null == instance) {
            logger.error("intentInstance is null!");
            return;
        }
        try {
            String jobId = createIntentInstanceToSO(instance);
            instance.setStatus("0");
            instance.setJobId(jobId);
            Session session = getSession();
            Transaction tx = session.beginTransaction();
            session.save(instance);
            tx.commit();

        }catch (Exception e) {
            logger.error("active instance to SO error :" + e);
        }
    }

    public void invalidIntentInstance(String instanceId) {
        IntentInstance instance = null;

        try(Session session = getSession()) {
            instance = (IntentInstance)session.createQuery("from IntentInstance where instanceId = :instanceId")
                    .setParameter("instanceId", instanceId).uniqueResult();
            logger.info("get instance OK, id=" + instanceId);

        } catch (Exception e) {
            logger.error("get instance occur exception:"+e);

        }
        if (null == instance) {
            logger.error("intentInstance is null!");
            return;
        }
        try {
            deleteInstanceToSO(instance.getInstanceId());
            instance.setStatus("3");
            Session session = getSession();
            Transaction tx = session.beginTransaction();
            session.save(instance);
            tx.commit();

        }catch (Exception e) {
            logger.error("invalid instance to SO error :" + e);
        }
    }

    @Override
    public Map<String, Object> queryInstancePerformanceData(String instanceId) {
        try(Session session = getSession()) {
            String hql = "from IntentInstance i, InstancePerformance p where i.resourceInstanceId = p.resourceInstanceId order by p.date";
            Query query = session.createQuery(hql);
            List<Object[]> queryResult= query.list();
            List<String> date = new ArrayList<>();
            List<String> bandwidth = new ArrayList<>();
            List<String> maxBandwidth = new ArrayList<>();
            SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
            for (Object[] o : queryResult) {
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
        }
    }

    @Override
    public Object queryAccessNodeInfo() throws IOException {
        Map<String, Object> result = new HashMap<>();
        List<String> accessNodeList = new ArrayList<>();
        List<String> cloudAccessNodeList = new ArrayList<>();
        JSONObject body = intentApiService.queryNetworkRoute().execute().body();
        JSONArray data = body.getJSONArray("data");
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
}
