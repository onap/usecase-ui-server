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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;
import javax.annotation.Resource;
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
import org.onap.usecaseui.server.service.lcm.ServiceLcmService;
import org.onap.usecaseui.server.service.slicingdomain.aai.AAISliceService;
import org.onap.usecaseui.server.service.slicingdomain.aai.bean.AAIServiceInstance;
import org.onap.usecaseui.server.service.slicingdomain.so.SOSliceService;
import org.onap.usecaseui.server.service.slicingdomain.so.bean.SOOperation;
import org.onap.usecaseui.server.util.RestfulServices;
import org.onap.usecaseui.server.util.nsmf.NsmfCommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Service;
import retrofit2.Response;

@Service("SlicingService")
@org.springframework.context.annotation.Configuration
@EnableAspectJAutoProxy
public class SlicingServiceImpl implements SlicingService {

    private final static Logger logger = LoggerFactory.getLogger(SlicingServiceImpl.class);

    @Resource(name = "ServiceLcmService")
    private ServiceLcmService serviceLcmService;

    private AAISliceService aaiSliceService;

    private SOSliceService soSliceService;

    public SlicingServiceImpl() {
        this(RestfulServices.create(AAISliceService.class), RestfulServices.create(SOSliceService.class));
    }

    public SlicingServiceImpl(AAISliceService aaiSliceService, SOSliceService soSliceService) {
        this.aaiSliceService = aaiSliceService;
        this.soSliceService = soSliceService;
    }

    @Override
    public ServiceResult createSlicingService(SlicingOrder slicingOrder) {

        ServiceCreateResult createResult = new ServiceCreateResult();
        String resultCode;
        String resultMsg;
        Gson gson = new Gson();
        ResultHeader resultHeader = new ResultHeader();
        try {
            // request bean for create slicing service
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
            CreationParameters parameters = new CreationParameters();
            parameters.setRequestInputs(requestInputs);
            CreationService creationService = new CreationService();
            creationService.setName(slicingOrder.getSlicing_order_info().getName());
            creationService.setDescription(CommonConstant.BLANK);
            String slicingPath = System.getProperty("user.dir") + File.separator + "config" + File.separator + "slicing.properties";
            InputStream inputStream = new FileInputStream(new File(slicingPath));
            Properties environment = new Properties();
            environment.load(inputStream);
            String serviceInvariantUuid = environment.getProperty("slicing.serviceInvariantUuid");
            creationService.setServiceInvariantUuid(serviceInvariantUuid);
            String serviceUuid = environment.getProperty("slicing.serviceUuid");
            creationService.setServiceUuid(serviceUuid);
            logger.info("serviceInvariantUuid is {}, serviceUuid is {}.", serviceInvariantUuid, serviceUuid);

            creationService.setGlobalSubscriberId(environment.getProperty("slicing.globalSubscriberId"));
            creationService.setServiceType(environment.getProperty("slicing.serviceType"));
            creationService.setParameters(parameters);

            CreationRequest creationRequest = new CreationRequest();
            creationRequest.setService(creationService);

            String jsonstr = JSON.toJSONString(creationRequest);
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonstr.toString());
            Response<CreateResponse> updateResponse = soSliceService
                .submitOrders(requestBody).execute();

            if (updateResponse.isSuccessful()) {
                CreateResponse createResponse = updateResponse.body();
                logger.info("createSlicingService: submitOrders reponse is:{}",
                    gson.toJson(updateResponse).toString());

                // set create operation result
                createResult.setService_id(createResponse.getService().getServiceId());
                createResult.setOperation_id(createResponse.getService().getOperationId());

                resultMsg = "5G slicing order created normally.";
                resultHeader.setResult_code(NsmfCodeConstant.SUCCESS_CODE);
            } else {
                logger.error(String
                    .format("createSlicingService: Can not submitOrders [code={}, message={}]", updateResponse.code(),
                        updateResponse.message()));
                resultMsg = "5G slicing order created failed.";
                resultHeader.setResult_code(NsmfCodeConstant.ERROR_CODE_UNKNOWN);
            }
        } catch (Exception e) {
            resultMsg = "5G slicing order created failed. Unknown exception occurred!";
            resultHeader.setResult_code(NsmfCodeConstant.ERROR_CODE_UNKNOWN);
            logger.error(e.getMessage());
        }

        logger.info(resultMsg);
        logger.info("createSlicingService: 5G slicing order creation has been finished.");
        resultHeader.setResult_message(resultMsg);
        ServiceResult serviceResult = new ServiceResult(resultHeader, createResult);
        return serviceResult;
    }

    @Override
    public ServiceResult querySlicingOrderList(String status, String pageNo, String pageSize) {

        ServiceResult serviceResult = new ServiceResult();
        ResultHeader resultHeader = new ResultHeader();
        List<OrderInfo> orderList = new ArrayList<OrderInfo>();
        String resultMsg;

        try {
            // TODO
            Response<JSONObject> response = this.aaiSliceService
                .listOrders(NsmfParamConstant.CUSTOM_5G, NsmfParamConstant.SERVICE_TYPE_5G).execute();
            if (response.isSuccessful()) {
                logger.info("querySlicingOrderList: listService reponse is:{}", response.body());
                Type type = new TypeToken<List<AAIServiceInstance>>() {
                }.getType();

                JSONArray array = response.body().getJSONArray("service-instance");
                buildOrderList(orderList, array);

                // return normal result code
                resultMsg = "5G slicing order query result.";
                resultHeader.setResult_code(NsmfCodeConstant.SUCCESS_CODE);
            } else {
                logger.error(String.format("querySlicingOrderList: Can not get listOrders[code={}, message={}]",
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
            logger.error(e.getMessage());
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
        logger.info(resultMsg);
        logger.info("querySlicingOrderList: 5G slicing order query has been finished.");
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
            order.setOrder_creation_time(NsmfCommonUtil.timestamp2Time(object.getString("created-at")));
            order.setOrder_status(object.getString("service-type"));
            order.setService_snssai(object.getString("environment-context"));
            order.setOrder_status(object.getString("orchestration-status"));
            orderList.add(order);
        }
    }

    public void addProgressToOrder(OrderList responseOrderList)  {
        if (responseOrderList.getSlicing_order_list() == null
            || responseOrderList.getSlicing_order_list().size() == 0) {
            logger.error(
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
                logger.error(
                    "addProgressToOrder: null == serviceInstanceOperations for businessId:{}.",
                    businessId);
                continue;
            }
            String operationId = serviceInstanceOperations.getOperationId();
            Response<SOOperation> response = null;
            try {
                response = this.soSliceService.queryOperationProgress(businessId, operationId)
                    .execute();
                if (response.isSuccessful()) {
                    SOOperation soOperation = response.body();
                    Gson gson = new Gson();
                    logger.info("addProgressToOrder: queryOperationProgress reponse is:{}",
                        gson.toJson(soOperation).toString());
                    if (soOperation == null || soOperation.getOperation() == null) {
                        logger.error("addProgressToOrder: soOperation is null or getOperation() is null for businessId {}!", businessId);
                        continue;
                    }
                    String operationResult = soOperation.getOperation().getResult();
                    String operationType = soOperation.getOperation().getOperation();
                    int progress = soOperation.getOperation().getProgress();
                    logger.info("addProgressToOrder: operationResult is:{}, operationType is {}, progress is {}",
                        operationResult, operationType, progress);
                    if (operationResult.equals(NsmfCodeConstant.OPERATION_ERROR_STATUS)) {
                        logger.error("addProgressToOrder: progress is ok, but operationResult is error for businessId {}!", businessId);
                        continue;
                    }
                    orderInfo.setLast_operation_type(operationType);
                    orderInfo.setLast_operation_progress(String.valueOf(progress));
                }
            } catch (IOException e) {
                logger.error(e.getMessage());
                logger.error("addProgressToOrder: catch an IOException for businessId {}!", businessId);
                continue;
            }
        }
    }
}
