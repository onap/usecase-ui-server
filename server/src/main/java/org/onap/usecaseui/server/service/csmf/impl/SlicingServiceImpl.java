/*
 * Copyright (C) 2020 CMCC, Inc. and others. All rights reserved.
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
package org.onap.usecaseui.server.service.csmf.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import org.onap.usecaseui.server.bean.ServiceInstanceOperations;
import org.onap.usecaseui.server.bean.csmf.CreateResponse;
import org.onap.usecaseui.server.bean.csmf.CreationParameters;
import org.onap.usecaseui.server.bean.csmf.CreationRequest;
import org.onap.usecaseui.server.bean.csmf.CreationRequestInputs;
import org.onap.usecaseui.server.bean.csmf.CreationService;
import org.onap.usecaseui.server.bean.csmf.OrderInfo;
import org.onap.usecaseui.server.bean.csmf.OrderList;
import org.onap.usecaseui.server.bean.csmf.ServiceCreateResult;
import org.onap.usecaseui.server.bean.csmf.SlicingOrder;
import org.onap.usecaseui.server.bean.nsmf.common.PagedResult;
import org.onap.usecaseui.server.bean.nsmf.common.ResultHeader;
import org.onap.usecaseui.server.bean.nsmf.common.ServiceResult;
import org.onap.usecaseui.server.constant.CommonConstant;
import org.onap.usecaseui.server.constant.csmf.CsmfParamConstant;
import org.onap.usecaseui.server.constant.nsmf.NsmfCodeConstant;
import org.onap.usecaseui.server.constant.nsmf.NsmfParamConstant;
import org.onap.usecaseui.server.service.csmf.SlicingService;
import org.onap.usecaseui.server.service.csmf.config.SlicingProperties;
import org.onap.usecaseui.server.service.lcm.ServiceLcmService;
import org.onap.usecaseui.server.service.slicingdomain.aai.AAISliceClient;
import org.onap.usecaseui.server.service.slicingdomain.aai.bean.AAIServiceInstance;
import org.onap.usecaseui.server.service.slicingdomain.so.SOSliceClient;
import org.onap.usecaseui.server.service.slicingdomain.so.bean.SOOperation;
import org.onap.usecaseui.server.util.nsmf.NsmfCommonUtil;
import org.springframework.stereotype.Service;
import retrofit2.Response;

@Slf4j
@RequiredArgsConstructor
@Service("SlicingService")
public class SlicingServiceImpl implements SlicingService {

    private static final Gson gson = new Gson();
    private final ServiceLcmService serviceLcmService;
    private final AAISliceClient aaiSliceClient;
    private final SOSliceClient soSliceClient;
    private final SlicingProperties slicingProperties;

    @Override
    public ServiceResult createSlicingService(SlicingOrder slicingOrder) {

        String resultCode;
        String resultMsg;

        ResultHeader resultHeader = new ResultHeader();
        ServiceCreateResult createResult = new ServiceCreateResult();
        try {
            // request bean for create slicing service
            CreationRequestInputs requestInputs = mapCreationRequest(slicingOrder);
            CreationService creationService = buildCreationService(slicingOrder, requestInputs);

            CreationRequest creationRequest = new CreationRequest();
            creationRequest.setService(creationService);

            String jsonstr = gson.toJson(creationRequest);
            log.info("createSlicingService:creationRequest request is:{}", jsonstr);
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonstr.toString());
            Response<CreateResponse> updateResponse = soSliceClient
                .submitOrders(requestBody).execute();

            if (updateResponse.isSuccessful()) {
                CreateResponse createResponse = updateResponse.body();
                log.info("createSlicingService: submitOrders reponse is:{}",
                    gson.toJson(createResponse).toString());

                // set create operation result
                createResult.setService_id(createResponse.getService().getServiceId());
                createResult.setOperation_id(createResponse.getService().getOperationId());

                resultMsg = "5G slicing order created normally.";
                resultHeader.setResult_code(NsmfCodeConstant.SUCCESS_CODE);
            } else {
                log.error(String
                    .format("createSlicingService: Can not submitOrders [code={}, message={}]", updateResponse.code(),
                        updateResponse.message()));
                resultMsg = "5G slicing order created failed.";
                resultHeader.setResult_code(NsmfCodeConstant.ERROR_CODE_UNKNOWN);
            }
        } catch (Exception e) {
            // set error message
            resultMsg = "5G slicing order created failed. Unknown exception occurred!";
            resultHeader.setResult_code(NsmfCodeConstant.ERROR_CODE_UNKNOWN);
            log.error(e.getMessage());
        }

        log.info(resultMsg);
        log.info("createSlicingService: 5G slicing order creation has been finished.");
        resultHeader.setResult_message(resultMsg);
        ServiceResult serviceResult = new ServiceResult(resultHeader, createResult);
        return serviceResult;
    }

    private CreationRequestInputs mapCreationRequest(SlicingOrder slicingOrder) {
        CreationRequestInputs requestInputs = new CreationRequestInputs();
        requestInputs.setExpDataRateDL(slicingOrder.getSlicing_order_info().getExpDataRateDL());
        requestInputs.setExpDataRateUL(slicingOrder.getSlicing_order_info().getExpDataRateUL());
        requestInputs.setLatency(slicingOrder.getSlicing_order_info().getLatency());
        requestInputs.setMaxNumberofUEs(slicingOrder.getSlicing_order_info().getMaxNumberofUEs());
        requestInputs.setUEMobilityLevel(slicingOrder.getSlicing_order_info().getUEMobilityLevel());
        requestInputs.setResourceSharingLevel(slicingOrder.getSlicing_order_info().getResourceSharingLevel());
        requestInputs.setCoverageAreaList(slicingOrder.getSlicing_order_info().getCoverageArea());
        //use default value
        requestInputs.setUseInterval("20");
        return requestInputs;
    }

    private CreationService buildCreationService(SlicingOrder slicingOrder, CreationRequestInputs requestInputs)
            throws FileNotFoundException, IOException {
        CreationParameters parameters = new CreationParameters();
        parameters.setRequestInputs(requestInputs);
        CreationService creationService = new CreationService();
        creationService.setName(slicingOrder.getSlicing_order_info().getName());
        creationService.setDescription(CommonConstant.BLANK);

        String slicingPath = System.getProperty("user.dir") + File.separator + "config" + File.separator + "slicing.properties";
        try {
            // kept for compatibility reasons
            InputStream inputStream = new FileInputStream(new File(slicingPath));
            setEnvironmentProperties(creationService, inputStream);
        } catch (FileNotFoundException e) {
            addConfigurationProperties(creationService);
        }

        creationService.setParameters(parameters);
        return creationService;
    }

    private void addConfigurationProperties(CreationService creationService) {
        creationService.setServiceInvariantUuid(slicingProperties.getServiceInvariantUuid());
        creationService.setServiceUuid(slicingProperties.getServiceUuid());
        creationService.setGlobalSubscriberId(slicingProperties.getGlobalSubscriberId());
        creationService.setServiceType(slicingProperties.getServiceType());
    }

    private void setEnvironmentProperties(CreationService creationService, InputStream inputStream)
            throws IOException {
        Properties environment = new Properties();
        environment.load(inputStream);
        String serviceInvariantUuid = environment.getProperty("slicing.serviceInvariantUuid");
        creationService.setServiceInvariantUuid(serviceInvariantUuid);
        String serviceUuid = environment.getProperty("slicing.serviceUuid");
        creationService.setServiceUuid(serviceUuid);
        log.info("serviceInvariantUuid is {}, serviceUuid is {}.", serviceInvariantUuid, serviceUuid);

        creationService.setGlobalSubscriberId(environment.getProperty("slicing.globalSubscriberId"));
        creationService.setServiceType(environment.getProperty("slicing.serviceType"));
    }

    @Override
    public ServiceResult querySlicingOrderList(String status, String pageNo, String pageSize) {

        ServiceResult serviceResult = new ServiceResult();
        ResultHeader resultHeader = new ResultHeader();
        List<OrderInfo> orderList = new ArrayList<OrderInfo>();
        String resultMsg;

        try {
            // TODO
            Response<JSONObject> response = this.aaiSliceClient
                .listOrders(NsmfParamConstant.CUSTOM_5G, NsmfParamConstant.SERVICE_TYPE_5G).execute();
            if (response.isSuccessful()) {
                log.info("querySlicingOrderList: listService reponse is:{}", response.body());
                Type type = new TypeToken<List<AAIServiceInstance>>() {
                }.getType();

                JSONArray array = response.body().getJSONArray("service-instance");
                buildOrderList(orderList, array);

                // return normal result code
                resultMsg = "5G slicing order query result.";
                resultHeader.setResult_code(NsmfCodeConstant.SUCCESS_CODE);
            } else {
                log.error(String.format("querySlicingOrderList: Can not get listOrders[code={}, message={}]",
                    response.code(),
                    response.message()));
                resultMsg = "\"5G slicing order query failed!";
                if (response.code() == NsmfCodeConstant.RESOURCE_NOT_FOUND_CODE) {
                    resultHeader.setResult_code(NsmfCodeConstant.SUCCESS_CODE);
                } else {
                    resultHeader.setResult_code(NsmfCodeConstant.ERROR_CODE_UNKNOWN);
                }
            }
        } catch (
            Exception e) {
            resultMsg = "5G slicing order query failed. Unknown exception occurred!";
            resultHeader.setResult_code(NsmfCodeConstant.ERROR_CODE_UNKNOWN);
            log.error(e.getMessage());
        }

        // Filter by status and paginate
        List<Object> filterOrderList = new ArrayList<Object>();
        if (!CsmfParamConstant.ALL.equals(status)) {
            filterOrderList = orderList.stream().filter(a -> a.getOrder_status().equals(status))
                .collect(Collectors.toList());
        } else {
            filterOrderList.addAll(orderList);
        }
        PagedResult pagedOrderList = NsmfCommonUtil
            .getPagedList(filterOrderList, Integer.parseInt(pageNo), Integer.parseInt(pageSize));

        // return API result for calling [Query Slicing Order List]
        OrderList responseOrderList = new OrderList(orderList.size(), pagedOrderList.getPagedList());
        addProgressToOrder(responseOrderList);
        log.info(resultMsg);
        log.info("querySlicingOrderList: 5G slicing order query has been finished.");
        resultHeader.setResult_message(resultMsg);
        serviceResult.setResult_header(resultHeader);
        serviceResult.setResult_body(responseOrderList);
        return serviceResult;
    }

    void buildOrderList(List<OrderInfo> orderList, JSONArray array) throws ParseException {
        for (int i = 0; i < array.size(); i++) {
            JSONObject object = array.getJSONObject(i);
            OrderInfo order = new OrderInfo();
            order.setOrder_id(object.getString("service-instance-id"));
            order.setOrder_name(object.getString("service-instance-name"));
            String createdAt = object.getString("created-at");
            // do not fail when createdAt is null
            if(createdAt != null) {
                String timestamp = NsmfCommonUtil.timestamp2Time(createdAt);
                order.setOrder_creation_time(timestamp);
            }
            order.setOrder_status(object.getString("service-type"));
            order.setService_snssai(object.getString("environment-context"));
            order.setOrder_status(object.getString("orchestration-status"));
            orderList.add(order);
        }
    }

    public void addProgressToOrder(OrderList responseOrderList)  {
        if (responseOrderList.getSlicing_order_list() == null
            || responseOrderList.getSlicing_order_list().size() == 0) {
            log.error(
                "addProgressToOrder: responseOrderList.getSlicing_order_list() is null or responseOrderList.getSlicing_order_list() size is 0.");
            return;
        }

        int i = 0;
        for (OrderInfo orderInfo : responseOrderList.getSlicing_order_list()) {
            i++;
            orderInfo.setOrder_index(String.valueOf(i));
            String businessId = orderInfo.getOrder_id();
            ServiceInstanceOperations serviceInstanceOperations = serviceLcmService
                .getServiceInstanceOperationById(businessId);
            if (null == serviceInstanceOperations || serviceInstanceOperations.getOperationId() == null) {
                log.error(
                    "addProgressToOrder: null == serviceInstanceOperations for businessId:{}.",
                    businessId);
                continue;
            }
            String operationId = serviceInstanceOperations.getOperationId();
            Response<SOOperation> response = null;
            try {
                response = this.soSliceClient.queryOperationProgress(businessId, operationId)
                    .execute();
                if (response.isSuccessful()) {
                    SOOperation soOperation = response.body();
                    Gson gson = new Gson();
                    log.info("addProgressToOrder: queryOperationProgress reponse is:{}",
                        gson.toJson(soOperation).toString());
                    if (soOperation == null || soOperation.getOperation() == null) {
                        log.error("addProgressToOrder: soOperation is null or getOperation() is null for businessId {}!", businessId);
                        continue;
                    }
                    String operationResult = soOperation.getOperation().getResult();
                    String operationType = soOperation.getOperation().getOperation();
                    int progress = soOperation.getOperation().getProgress();
                    log.info("addProgressToOrder: operationResult is:{}, operationType is {}, progress is {}",
                        operationResult, operationType, progress);
                    if (operationResult.equals(NsmfCodeConstant.OPERATION_ERROR_STATUS)) {
                        log.error("addProgressToOrder: progress is ok, but operationResult is error for businessId {}!", businessId);
                        continue;
                    }
                    orderInfo.setLast_operation_type(operationType);
                    orderInfo.setLast_operation_progress(String.valueOf(progress));
                }
            } catch (IOException e) {
                log.error(e.getMessage());
                log.error("addProgressToOrder: catch an IOException for businessId {}!", businessId);
                continue;
            }
        }
    }
}
