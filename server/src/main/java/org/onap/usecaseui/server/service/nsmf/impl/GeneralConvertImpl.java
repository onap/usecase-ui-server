/*
 * Copyright (C) 2019 CMCC, Inc. and others. All rights reserved.
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
package org.onap.usecaseui.server.service.nsmf.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.onap.usecaseui.server.constant.nsmf.NsmfParamConstant;
import org.onap.usecaseui.server.service.slicingdomain.aai.AAISliceClient;
import org.onap.usecaseui.server.service.slicingdomain.aai.bean.AAIServiceAndInstance;
import org.onap.usecaseui.server.service.slicingdomain.aai.bean.Relationship;
import org.onap.usecaseui.server.service.slicingdomain.aai.bean.RelationshipData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import retrofit2.Response;

@RequiredArgsConstructor
@Service("GeneralConvertService")
public class GeneralConvertImpl {

    private static final Logger logger = LoggerFactory.getLogger(GeneralConvertImpl.class);

    private final AAISliceClient aaiSliceClient;

    public AAIServiceAndInstance queryServiceUtil(JSONObject object) {
        if (object.containsKey("relationship-list")) {
            AAIServiceAndInstance aaiServiceAndInstance = new AAIServiceAndInstance();
            aaiServiceAndInstance.setModelInvariantId(object.getString("model-invariant-id"));
            aaiServiceAndInstance.setModelVersionId(object.getString("model-version-id"));
            if (object.containsKey("workload-context")) {
                aaiServiceAndInstance.setWorkloadContext(object.getString("workload-context"));
            }

            JSONArray array = object.getJSONObject("relationship-list").getJSONArray("relationship");
            List<Relationship> relationshipList = new ArrayList<Relationship>();
            for (int i = 0; i < array.size(); i++) {
                Relationship relationship = new Relationship();
                JSONObject objectShip = array.getJSONObject(i);
                JSONArray arrayData = objectShip.getJSONArray("relationship-data");
                relationship.setRelatedTo(objectShip.getString("related-to"));
                List<RelationshipData> RelationshipDataList = new ArrayList<RelationshipData>();
                for (int j = 0; j < arrayData.size(); j++) {
                    RelationshipData relationshipData = new RelationshipData();
                    JSONObject objectData = arrayData.getJSONObject(j);
                    relationshipData.setRelationshipKey(objectData.getString("relationship-key"));
                    relationshipData.setRelationshipValue(objectData.getString("relationship-value"));
                    RelationshipDataList.add(relationshipData);
                }
                relationshipList.add(relationship);
                relationship.setRelationshipData(RelationshipDataList);

            }
            aaiServiceAndInstance.setRelationshipList(relationshipList);
            return aaiServiceAndInstance;
        } else {
            return null;
        }

    }

    List<String> getAreaTaList(String sourceCoverageAreaTAList) {
        if (sourceCoverageAreaTAList == null || sourceCoverageAreaTAList.length() == 0) {
            logger.error("getAreaTaList: sourceCoverageAreaTAList is null or length is 0");
            return null;
        }
        String[] strArr = sourceCoverageAreaTAList.split("\\|");
        return Arrays.asList(strArr);
    }

    public AAIServiceAndInstance listServiceByIdUtil(JSONObject object) {
        AAIServiceAndInstance aaiServiceAndInstance = new AAIServiceAndInstance();
        aaiServiceAndInstance.setServiceInstanceId(object.getString("service-instance-id"));
        aaiServiceAndInstance.setServiceInstanceName(object.getString("service-instance-name"));
        aaiServiceAndInstance.setEnvironmentContext(object.getString("environment-context"));
        aaiServiceAndInstance.setOrchestrationStatus(object.getString("orchestration-status"));
        aaiServiceAndInstance.setServiceType(object.getString("service-type"));
        return aaiServiceAndInstance;

    }

    String getUseInterval(String serviceId) {
        AAIServiceAndInstance aaiServiceAndInstance = new AAIServiceAndInstance();
        try {
            // TODO
            Response<JSONObject> response = this.aaiSliceClient
                .queryOrderByService(NsmfParamConstant.CUSTOM_5G, NsmfParamConstant.SERVICE_TYPE_5G, serviceId)
                .execute();
            if (response.isSuccessful()) {
                JSONObject object = response.body();
                logger.info("getUseInterval: queryOrderByService response is:{}", response.body());
                aaiServiceAndInstance = queryServiceUtil(object);
                String orderId = getOrderIdFromRelation(aaiServiceAndInstance);
                Response<JSONObject> orderResponse = this.aaiSliceClient
                    .queryOrderByOrderId(NsmfParamConstant.CUSTOM_5G, NsmfParamConstant.SERVICE_TYPE_5G, orderId)
                    .execute();
                if (orderResponse.isSuccessful()) {
                    logger.info("getUseInterval: queryOrderByOrderId response is:{}", orderResponse.body());
                    JSONObject orderObject = orderResponse.body();
                    AAIServiceAndInstance aaiOrderServiceAndInstance = queryServiceUtil(orderObject);
                    String useInterval = aaiOrderServiceAndInstance.getWorkloadContext();
                    return useInterval;
                } else {
                    logger.error(String
                        .format("getUseInterval: Can not get queryOrderByOrderId[code={}, message={}]", response.code(),
                            response.message()));
                    return "";
                }

            } else {
                logger.error(String
                    .format("getUseInterval: Can not get queryOrderByService[code={}, message={}]", response.code(),
                        response.message()));
                return "";
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            return "";
        }
    }

    String getOrderIdFromRelation(AAIServiceAndInstance aaiServiceAndInstance) {
        //获取第一个元素，总共一个切片业务对应一个订单
        String orderId = "";
        if (aaiServiceAndInstance.getRelationshipList() != null
            && aaiServiceAndInstance.getRelationshipList().size() > 0) {
            Relationship relationship = aaiServiceAndInstance.getRelationshipList().get(0);

            if (null != relationship) {
                List<org.onap.usecaseui.server.service.slicingdomain.aai.bean.RelationshipData> relationshipDataList = relationship
                    .getRelationshipData();

                for (org.onap.usecaseui.server.service.slicingdomain.aai.bean.RelationshipData relationshipData : relationshipDataList) {
                    String relationKey = relationshipData.getRelationshipKey();
                    if (relationKey.equals("service-instance.service-instance-id")) {
                        orderId = relationshipData.getRelationshipValue();
                    }
                }
            }
        }

        return orderId;
    }
}
