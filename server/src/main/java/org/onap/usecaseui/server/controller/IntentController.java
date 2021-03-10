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
package org.onap.usecaseui.server.controller;

import java.io.File;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;

import org.onap.usecaseui.server.bean.HttpResponseResult;
import org.onap.usecaseui.server.bean.intent.IntentModel;
import org.onap.usecaseui.server.service.intent.IntentService;
import org.onap.usecaseui.server.util.DateUtils;
import org.onap.usecaseui.server.util.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
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
    private final static String UPLOADPATH = "/home/uui/upload/";
    private final static String NLPLOADPATH = "/home/run/bert-master/upload/";

    @Resource(name = "IntentService")
    private IntentService intentService;

    private ObjectMapper omAlarm = new ObjectMapper();

    @GetMapping(value="/listModel",produces = "application/json;charset=utf8")
    public String getModels() throws JsonProcessingException {
        List<IntentModel> listModels = intentService.listModels();
        return omAlarm.writeValueAsString(listModels);
    }

    @RequestMapping("/uploadModel")
    @ResponseBody
    public String uploadModel (@RequestParam("file") MultipartFile file) {
        String fileName = file.getOriginalFilename();

        String filePath = UPLOADPATH + fileName ;

        File dest = new File(filePath);

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
            String filePath = UPLOADPATH + fileName;
            logger.info("delete model file: " + filePath);
            File dest = new File(filePath);
            if(dest.exists()){
                dest.delete();
                logger.info("delete file OK: " + filePath);
                if (filePath.endsWith(".zip")) {
                    String unzipPath = filePath.substring(0, filePath.length() - 1 - 4);
                    File unZipFile = new File(unzipPath);
                    if (unZipFile.exists()) {
                        unZipFile.delete();
                    }
                }
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

    @GetMapping(value = {"/activeModel"}, produces = "application/json")
    public String activeModel(@RequestParam String modelId){
        String result = "0";
        try{
            logger.info("update model record status: id=" + modelId);
            IntentModel model = intentService.activeModel(modelId);

            logger.info("active NLP model, model=" + model.getFilePath());
            String dirPath = intentService.activeModelFile(model);
            if (dirPath != null) {
                dirPath = dirPath.replace(UPLOADPATH, NLPLOADPATH);
                load(dirPath);
            }


            result = "1";
        }catch (Exception e) {
            logger.error("Details:" + e.getMessage());
            return "0";
        }

        return result;
    }

    private String load(String dirPath) {

        String url = "http://uui-nlp.onap:33011/api/online/load";
        HashMap<String, String> headers = new HashMap<>();
        String bodyStr = "{" + "\"path\": \""+dirPath+"\"" + "}";
        logger.info("request body: " + bodyStr);

        HttpResponseResult result = HttpUtil.sendPostRequestByJson(url, headers, bodyStr);
        String respContent = result.getResultContent();

        logger.info("NLP api respond: " + String.valueOf(result.getResultCode()));
        logger.info(respContent);

        JSONObject map = JSON.parseObject(respContent);

        String status = map.getString("Status");
        logger.info("load result: " + status);

        return status;
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

    @ResponseBody
    @PostMapping(value = {"/predict"}, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = "application/json; charset=utf-8")
    public String predict(@RequestBody Object body) throws ParseException {
        String text = (String)((Map)body).get("text");
        //System.out.println(text);

        String url = "http://uui-nlp.onap:33011/api/online/predict";
        HashMap<String, String> headers = new HashMap<>();
        String bodyStr = "{\"title\": \"predict\", \"text\": \"" + text
                +  "\"}";
        logger.info("request body: " + bodyStr);

        HttpResponseResult result = HttpUtil.sendPostRequestByJson(url, headers, bodyStr);
        String respContent = result.getResultContent();

        logger.info("NLP api respond: " + String.valueOf(result.getResultCode()));
        logger.info(respContent);

        JSONObject map = JSON.parseObject(respContent);

        JSONObject map2 = new JSONObject();

        for (Map.Entry<String, Object> entry:map.entrySet()) {
            logger.debug(entry.getKey()+","+entry.getValue());
            String key = tranlateFieldName(entry.getKey());
            String valueStr = (String) entry.getValue();
            String value = intentService.calcFieldValue(key, valueStr);
            map2.put(key, value);
        }

        logger.info("translate result: " + map2.toJSONString());

        return map2.toJSONString();
    }



    private static String tranlateFieldName(String key){
        String ret = "";
        if(key==null || key.trim().equals(""))
            return ret;

        HashMap<String, String> map = new HashMap<>();
        map.put("Communication service","name");
        map.put("Maximum user devices","maxNumberofUEs");
        map.put("Downlink data rate","expDataRateDL");
        map.put("Time delay","latency");
        map.put("Uplink data rate","expDataRateUL");
        map.put("Resource","resourceSharingLevel");
        map.put("Mobility","uEMobilityLevel");
        map.put("Region","coverageArea");

        ret = map.get(key.trim());
        return ret;
    }
}
