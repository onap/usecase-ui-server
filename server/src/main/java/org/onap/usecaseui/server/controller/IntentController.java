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
package org.onap.usecaseui.server.controller;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Resource;

import com.alibaba.fastjson.JSONArray;
import org.apache.commons.collections.MapUtils;
import org.onap.usecaseui.server.bean.HttpResponseResult;
import org.onap.usecaseui.server.bean.intent.CCVPNInstance;
import org.onap.usecaseui.server.bean.intent.IntentModel;
import org.onap.usecaseui.server.bean.intent.IntentResponseBody;
import org.onap.usecaseui.server.constant.IntentConstant;
import org.onap.usecaseui.server.service.csmf.SlicingService;
import org.onap.usecaseui.server.service.intent.IntentApiService;
import org.onap.usecaseui.server.service.intent.IntentInstanceService;
import org.onap.usecaseui.server.service.intent.IntentService;
import org.onap.usecaseui.server.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@org.springframework.context.annotation.Configuration
@EnableAspectJAutoProxy
@CrossOrigin(origins = "*")
@RequestMapping("/intent")
public class IntentController {
    private final Logger logger = LoggerFactory.getLogger(IntentController.class);

    @Resource(name = "IntentService")
    private IntentService intentService;

    @Resource(name = "IntentInstanceService")
    private IntentInstanceService intentInstanceService;

    private IntentApiService intentApiService;

    private ObjectMapper omAlarm = new ObjectMapper();

    @Resource(name = "SlicingService")
    private SlicingService slicingService;

    public IntentController() {
        this(RestfulServices.create(IntentApiService.class));
    }
    public IntentController(IntentApiService intentApiService) {
        this.intentApiService = intentApiService;
    }

    @GetMapping(value="/listModel",produces = "application/json;charset=utf8")
    public String getModels() throws JsonProcessingException {
        List<IntentModel> listModels = intentService.listModels();
        return omAlarm.writeValueAsString(listModels);
    }

    @RequestMapping("/uploadModel")
    @ResponseBody
    public String uploadModel (@RequestParam("file") MultipartFile file,@RequestParam("modelType")String modelType) {
        String fileName = file.getOriginalFilename();

        String filePath = IntentConstant.UPLOADPATH + fileName ;

        File dest = newFile(filePath);

        if(!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
            logger.info("create dir, name=" + dest.getParentFile().getName());
        }
        try {

            file.transferTo(dest);
            logger.info("upload file, name = " + dest.getName());
            IntentModel model = new IntentModel();
            model.setModelName(fileName);
            model.setFilePath(filePath);
            model.setCreateTime(DateUtils.dateToString(new Date()));
            float size = dest.length();
            float sizeM = size/1024;
            model.setSize(sizeM);
            model.setActive(0);
            model.setModelType(modelType);
            Map<String,String> fileMap = new HashMap<>();
            fileMap.put("file", filePath);
            UploadFileUtil.formUpload(IntentConstant.NLP_FILE_URL_BASE + "/uploader", null, fileMap, null);

            intentService.addModel(model);

            logger.info("save model, " + model.toString());
            return "1";
        } catch (Exception e) {
            logger.error("Details:" + e.getMessage());
            return "0";
        }
    }

    private String deleteModelFile(String modelId){
        String result = "0";
        try{
            IntentModel model = intentService.getModel(modelId);
            if( model==null){
                return result;
            }

            String fileName = model.getModelName();
            String filePath = IntentConstant.UPLOADPATH + fileName;
            logger.info("delete model file: " + filePath);
            File dest = newFile(filePath);
            if(dest.exists()){
                dest.delete();
                postDeleteFile(fileName);
                logger.info("delete file OK: " + filePath);
            }{
                logger.info("file not found: " + filePath);
            }
            result = "1";
        }catch (Exception e){
            logger.error("Details:" + e.getMessage());
            return "0";
        }
        return result;
    }


    private String postDeleteFile(String fileName) {

        String url = IntentConstant.NLP_FILE_URL_BASE + "/deleteFile/"+ fileName;
        HashMap<String, String> headers = new HashMap<>();

        HttpResponseResult result = HttpUtil.sendGetRequest(url,headers);
        String respContent = result.getResultContent();

        logger.info("NLP api respond: " + String.valueOf(result.getResultCode()));
        logger.info(respContent);

        return respContent;
    }

    @GetMapping(value = {"/activeModel"}, produces = "application/json")
    public String activeModel(@RequestParam String modelId){
        String result = "0";
        try{
            logger.info("update model record status: id=" + modelId);
            IntentModel model = intentService.activeModel(modelId);

            logger.info("active NLP model, model=" + model.getFilePath());
            String fileName = intentService.activeModelFile(model);
            if (fileName != null) {
                intentService.load(IntentConstant.NLPLOADPATH + fileName);
            }


            result = "1";
        }catch (Exception e) {
            logger.error("Details:" + e.getMessage());
            return "0";
        }

        return result;
    }



    @DeleteMapping(value = {"/deleteModel"}, produces = "application/json")
    public String deleteModel(@RequestParam String modelId){
        String result = "0";
        try{
            result = deleteModelFile(modelId);

            logger.info("delete model record: id=" + modelId);
            result = intentService.deleteModel(modelId);
        }catch (Exception e) {
            logger.error("Details:" + e.getMessage());
            return "0";
        }

        return result;
    }
    @IntentResponseBody
    @ResponseBody
    @PostMapping(value = {"/predict"}, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = "application/json; charset=utf-8")
    public Map<String, Object> predict(@RequestBody Object body) throws ParseException {
        String text = (String)((Map)body).get("text");
        text = text.trim();
        String modelType = (String)((Map)body).get("modelType");

        String activeModelType = intentService.getActiveModelType();
        if (modelType == null || !modelType.equals(activeModelType)) {
            throw new RuntimeException("The active model file does not support parsing the current text");
        }
        String[] questions = getQuestions(modelType);

        String url = IntentConstant.NLP_ONLINE_URL_BASE + "/api/online/predict";
        HashMap<String, String> headers = new HashMap<>();
        String bodyStr = "{\"title\": \"predict\", \"text\": \"" + text
                +  "\", \"questions\":" + new JSONArray(Arrays.asList(questions)).toJSONString() + "}";
        logger.info("request body: " + bodyStr);

        HttpResponseResult result = HttpUtil.sendPostRequestByJson(url, headers, bodyStr);
        String respContent = result.getResultContent();

        logger.info("NLP api respond: " + String.valueOf(result.getResultCode()));
        logger.info(respContent);

        JSONObject map = JSON.parseObject(respContent);

        JSONObject map2 = new JSONObject();

        if (IntentConstant.MODEL_TYPE_CCVPN.equals(modelType)) {
            assemblyCCVPNResult(text, map, map2);
        }
        else {
            assemblySlicingResult(map, map2);
        }

        logger.info("translate result: " + map2.toJSONString());

        return map2;
    }

    @IntentResponseBody
    @ResponseBody
    @PostMapping(value = {"/unifyPredict"}, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = "application/json; charset=utf-8")
    public Map<String, Object> unifyPredict(@RequestBody Object body) throws ParseException {
        String text = (String)((Map)body).get("text");
        text = text.trim();
        String modelType = intentService.getModelTypeByIntentText(text);

        String activeModelType = intentService.getActiveModelType();
        if (modelType == null || !modelType.equals(activeModelType)) {
            intentService.activeModelByType(modelType);
        }
        String[] questions = getQuestions(modelType);

        String url = IntentConstant.NLP_ONLINE_URL_BASE + "/api/online/predict";
        HashMap<String, String> headers = new HashMap<>();
        String bodyStr = "{\"title\": \"predict\", \"text\": \"" + text
                +  "\", \"questions\":" + new JSONArray(Arrays.asList(questions)).toJSONString() + "}";
        logger.info("request body: " + bodyStr);

        HttpResponseResult result = HttpUtil.sendPostRequestByJson(url, headers, bodyStr);
        String respContent = result.getResultContent();

        logger.info("NLP api respond: " + String.valueOf(result.getResultCode()));
        logger.info(respContent);

        JSONObject map = JSON.parseObject(respContent);

        JSONObject map2 = new JSONObject();
        JSONObject resultMap = new JSONObject();

        if (IntentConstant.MODEL_TYPE_CCVPN.equals(modelType)) {
            assemblyCCVPNResult(text, map, map2);
            resultMap.put("type", IntentConstant.MODEL_TYPE_CCVPN);
        }
        else {
            assemblySlicingResult(map, map2);
            resultMap.put("type", IntentConstant.MODEL_TYPE_5GS);
        }
        resultMap.put("formData",map2);

        logger.info("translate result: " + resultMap.toJSONString());

        return resultMap;
    }

    private void assemblySlicingResult(JSONObject map, JSONObject resultMap) {
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            logger.debug(entry.getKey() + "," + entry.getValue());
            String key = tranlateFieldName(entry.getKey());
            String valueStr = (String) entry.getValue();
            String value = intentService.calcFieldValue(key, valueStr);
            resultMap.put(key, value);
        }
    }

    private void assemblyCCVPNResult(String text, JSONObject map, JSONObject map2) {
        String bandWidth = map.getString("bandwidth");
        String accessPoint = map.getString("access point");
        String cloudPoint = map.getString("cloud point");
        boolean protect = MapUtils.getBooleanValue(map, "protect", false);
        String instanceId = getUUID();
        String accessPointAlias = intentInstanceService.formatAccessPoint(accessPoint);
        if ("".equals(accessPointAlias)) {
            if (text.indexOf("Access one") > -1) {
                accessPointAlias = "tranportEp_src_ID_111_1";
            } else if (text.indexOf("Access two") > -1) {
                accessPointAlias = "tranportEp_src_ID_111_2";
            } else if (text.indexOf("Access three") > -1) {
                accessPointAlias = "tranportEp_src_ID_113_1";
            }
        }
        String bandwidthAlias = null;
        if (bandWidth.matches("\\d+")) {
            bandwidthAlias = intentInstanceService.formatBandwidth(bandWidth);
        } else {
            Pattern pattern = Pattern.compile("(\\d+)(Gbps|Mbps)");
            Matcher matcher = pattern.matcher(text);
            if (matcher.find()) {
                int value = Integer.parseInt(matcher.group(1));
                String unit = matcher.group(2);
                if ("Gbps".equals(unit)) {
                    value = value * 1000;
                }
                bandwidthAlias = value + "";
            }
        }

        String cloudPointAlias = intentInstanceService.formatCloudPoint(cloudPoint);
        if ("".equals(cloudPointAlias)) {
            if (text.indexOf("Cloud one") > -1) {
                cloudPointAlias = "tranportEp_dst_ID_212_1";
            }
        }

        Map<String, Object> accessPointOne = new HashMap<>();
        accessPointOne.put("name", accessPointAlias);
        accessPointOne.put("bandwidth", bandwidthAlias);
        map2.put("name", "");
        map2.put("instanceId", instanceId);
        map2.put("accessPointOne", accessPointOne);
        map2.put("cloudPointName", cloudPointAlias);
        map2.put("protect", protect);
    }

    private String[] getQuestions(String modelType) {
        if (IntentConstant.MODEL_TYPE_CCVPN.equals(modelType)) {
            return IntentConstant.QUESTIONS_CCVPN;
        } else {
            return IntentConstant.QUESTIONS_5GS;
        }
    }


    private static String tranlateFieldName(String key){
        String ret = "";
        if(key==null || key.trim().equals(""))
            return ret;

        HashMap<String, String> map = new HashMap<>();
        map.put("Communication Service Name","name");
        map.put("Max Number of UEs","maxNumberofUEs");
        map.put("Data Rate Downlink","expDataRateDL");
        map.put("Latency","latency");
        map.put("Data Rate Uplink","expDataRateUL");
        map.put("Resource Sharing Level","resourceSharingLevel");
        map.put("Mobility","uEMobilityLevel");
        map.put("Area","coverageArea");

        ret = map.get(key.trim());
        return ret;
    }

    @IntentResponseBody
    @ResponseBody
    @GetMapping(value = {"/getInstanceId"},
            produces = "application/json")
    public JSONObject getInstanceId() {
        String instanceId = getUUID();
        JSONObject result = new JSONObject();
        result.put("instanceId", instanceId);
        return result;
    }

    private String getUUID() {
        int first = new Random(10).nextInt(8) + 1;
        int hashCodeV = UUID.randomUUID().toString().hashCode();
        if (hashCodeV < 0) {
            hashCodeV = -hashCodeV;
        }
        String instanceId = first + String.format("%015d", hashCodeV);
        return instanceId;
    }

    @IntentResponseBody
    @ResponseBody
    @PostMapping (value = {"/getInstanceList"},consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = "application/json")
    public Object getInstanceList(@RequestBody Object body) {
        int currentPage = (int) ((Map)body).get("currentPage");
        int pageSize = (int) ((Map)body).get("pageSize");
        logger.error("getInstanceList --> currentPage:" + currentPage + ",pageSize:" + pageSize);
        return intentInstanceService.queryIntentInstance(null, currentPage, pageSize);
    }
    @IntentResponseBody
    @ResponseBody
    @PostMapping(value = {"/createIntentInstance"}, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = "application/json; charset=utf-8")
    public Object createIntentInstance(@RequestBody Object body) throws IOException {
        String intentInstanceId = (String) ((Map)body).get("instanceId");
        String name = (String) ((Map)body).get("name");
        String lineNum = (String) ((Map)body).get("lineNum");
        String cloudPointName = (String) ((Map)body).get("cloudPointName");
        Map<String, Object> accessPointOne = (Map) ((Map)body).get("accessPointOne");
        String accessPointOneName = MapUtils.getString(accessPointOne, "name");
        int accessPointOneBandWidth = MapUtils.getIntValue(accessPointOne, "bandwidth");
        boolean protectStatus = MapUtils.getBooleanValue((Map)body,"protect", false);

        CCVPNInstance instance = new CCVPNInstance();
        instance.setInstanceId(intentInstanceId);
        instance.setName(name);
        instance.setLineNum(lineNum);
        instance.setCloudPointName(cloudPointName);
        instance.setAccessPointOneName(accessPointOneName);
        instance.setAccessPointOneBandWidth(accessPointOneBandWidth);
        instance.setStatus("0");
        instance.setProtectStatus(protectStatus?1:0);

        int flag = intentInstanceService.createIntentInstance(instance);

        if(flag == 1) {
            return "OK";
        }
        else {
            throw new RuntimeException("create Instance error");
        }
    }

    @IntentResponseBody
    @GetMapping(value = {"/getFinishedInstanceInfo"},
            produces = "application/json")
    public Object getFinishedInstanceInfo() {
        List<CCVPNInstance> instanceList = intentInstanceService.getFinishedInstanceInfo();
        List<Map<String, Object>> result = new ArrayList<>();
        for (CCVPNInstance instance : instanceList) {
            Map<String, Object> instanceInfo = new HashMap<>();
            instanceInfo.put("instanceId", instance.getInstanceId());
            instanceInfo.put("name", instance.getName());
            result.add(instanceInfo);
        }
        return result;
    }

    @IntentResponseBody
    @DeleteMapping(value = {"/deleteIntentInstance"}, produces = "application/json; charset=utf-8")
    public Object deleteIntentInstance(@RequestParam String instanceId) {
        intentInstanceService.deleteIntentInstance(instanceId);
        return "ok";
    }
    @IntentResponseBody
    @ResponseBody
    @PostMapping(value = {"/activeIntentInstance"}, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = "application/json; charset=utf-8")
    public Object activeIntentInstance(@RequestBody Object body) {
        String instanceId= (String) ((Map)body).get("instanceId");
        intentInstanceService.activeIntentInstance(instanceId);
        return "ok";
    }
    @IntentResponseBody
    @ResponseBody
    @PostMapping(value = {"/invalidIntentInstance"}, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = "application/json; charset=utf-8")
    public Object invalidIntentInstance(@RequestBody Object body) {
        String instanceId= (String) ((Map)body).get("instanceId");
        intentInstanceService.invalidIntentInstance(instanceId);
        return "ok";
    }

    @IntentResponseBody
    @ResponseBody
    @PostMapping(value = {"/queryInstancePerformanceData"}, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = "application/json; charset=utf-8")
    public Object queryInstancePerformanceData(@RequestBody Object body) {
        String instanceId= (String) ((Map)body).get("instanceId");
        return intentInstanceService.queryInstancePerformanceData(instanceId);
    }

    @IntentResponseBody
    @GetMapping(value = {"/queryAccessNodeInfo"},
            produces = "application/json")
    public Object queryAccessNodeInfo() throws IOException{
        return intentInstanceService.queryAccessNodeInfo();
    }


    @IntentResponseBody
    @ResponseBody
    @PostMapping(value = {"/getInstanceStatus"}, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = "application/json; charset=utf-8")
    public Object getInstanceStatus(@RequestBody Object body) {
        JSONArray ids= new JSONObject((Map)body).getJSONArray("ids");
        return intentInstanceService.getInstanceStatus(ids);
    }

    public File newFile(String filePath) {
        return new File(filePath);
    }



    @IntentResponseBody
    @ResponseBody
    @PostMapping(value = {"/addCustomer"}, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = "application/json; charset=utf-8")
    public Object addCustomer() throws IOException {
        intentInstanceService.addCustomer();
        return "OK";
    }
}
