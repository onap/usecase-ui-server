package org.onap.usecaseui.server.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.onap.usecaseui.server.bean.HttpResponseResult;
import org.onap.usecaseui.server.bean.intent.IntentModel;
import org.onap.usecaseui.server.service.intent.IntentService;
import org.onap.usecaseui.server.util.DateUtils;
import org.onap.usecaseui.server.util.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@org.springframework.context.annotation.Configuration
@EnableAspectJAutoProxy
@CrossOrigin(origins = "*")
@RequestMapping("/intent")
public class IntentController {
    private final Logger logger = LoggerFactory.getLogger(IntentController.class);
    //private final static String UPLOADPATH = "c:/var/uploadtest/";
    private final static String UPLOADPATH = "/home/uui/upload/";
    private final static String NLPLOADPATH = "/home/run/bert-master/upload/";

    private final static int MAX_NUMBER_OF_UES = 100000;
    private final static int MIN_NUMBER_OF_UES = 1;
    private final static int MAX_EXP_DATA_RATE_DL = 3000;
    private final static int MIN_EXP_DATA_RATE_DL = 100;
    private final static int MAX_EXP_DATA_RATE_UL = 3000;
    private final static int MIN_EXP_DATA_RATE_UL = 100;
    private final static int MAX_LATENCY = 200;
    private final static int MIN_LATENCY = 10;


    private final static List<String> GB_COMPANY = Arrays.asList(new String[] {"gbps", "gb"});
    private final static List<String> MB_COMPANY = Arrays.asList(new String[] {"mbps", "mb"});

    @Resource(name = "IntentService")
    private IntentService intentService;

    private ObjectMapper omAlarm = new ObjectMapper();

    @GetMapping(value="/listModel",produces = "application/json;charset=utf8")
    public String getModels() throws JsonProcessingException {
        //Map<String, List<SortMaster>> map = new HashMap<>();
        List<IntentModel> listModels = intentService.listModels();
//        List<SortMaster> operationResults = alarmsHeaderService.listSortMasters("operationResult");
//        map.put("operationTypes", operationTypes);
//        map.put("operationResults", operationResults);
//        return omAlarm.writeValueAsString(map);
        return omAlarm.writeValueAsString(listModels);
    }

    @RequestMapping("/uploadModel")
    @ResponseBody
    //@PutMapping(value="/uploadModel",produces = "application/json;charset=utf8")
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
            e.printStackTrace();
        }
        return  "0";
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
            e.printStackTrace();
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
            e.printStackTrace();
        }

        return result;
    }

    private String load(String dirPath) {

        String url = "http://110.21.19.55:33011/api/online/load";
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
            e.printStackTrace();
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
            String value = calcFieldValue(key, valueStr);
            map2.put(key, value);
        }

        logger.info("translate result: " + map2.toJSONString());

        return map2.toJSONString();
    }

    private static String calcFieldValue(String key, String strValue){
        String ret = "";


        if(strValue==null)
            strValue = "";
        else
            ret = strValue.trim();

        if("resourceSharingLevel".equalsIgnoreCase(key)){
            ret = "no-shared";
            if("shared".equalsIgnoreCase(strValue)
                    || "共享".equalsIgnoreCase(strValue)){
                ret = "shared";
            }
        }
        else if("uEMobilityLevel".equalsIgnoreCase(key)){
            ret = "stationary";
            if(strValue.contains("Nomadic") || strValue.contains("游牧")){
                ret = "nomadic";
            }
            else if(strValue.contains("restricted") || strValue.contains("限制")){
                ret = "Spatially Restricted Mobility";
            }
            else if(strValue.contains("fully")){
                ret = "Fully Mobility";
            }
        }
        else if("coverageArea".equalsIgnoreCase(key)){

            Map<String, Object> areaMap = new HashMap<>();
            areaMap.put("wanshoulu", "Beijing Haidian District Wanshoulu Street");
            areaMap.put("zhongguancun", "Beijing Haidian District Zhongguancun");
            areaMap.put("haidian", "Beijing Haidian District Haidian Street");
            areaMap.put("xisanqi", "Beijing Haidian District Xisanqi Street");
            areaMap.put("chengbei", "Beijing Changping District Chengbei Street");
            areaMap.put("chengnan", "Beijing Changping District Chengnan Street");
            areaMap.put("tiantongyuan north", "Beijing Changping District Tiantongyuan North Street");
            areaMap.put("tiantongyuan south", "Beijing Changping District Tiantongyuan South Street");
            areaMap.put("guang'anmenwai", "Beijing Xicheng District Guang'anmenwai Street");
            areaMap.put("xuanwumen", "Beijing Xicheng District Xuanwumen Street");
            areaMap.put("west changan", "Beijing Xicheng District West Changan Street");
            areaMap.put("financial", "Beijing Xicheng District Financial Street");
            areaMap.put("lujiazui", "Shanghai udongxin District Lujiazui Street");
            areaMap.put("zhoujiadu", "Shanghai udongxin District Zhoujiadu Street");
            areaMap.put("tangqiao", "Shanghai udongxin District Tangqiao Street");
            areaMap.put("nanquanlu", "Shanghai udongxin District Nanquanlu Street");
            areaMap.put("jiangning lu", "Shanghai Jingan District Jiangning Lu Street");
            areaMap.put("jing'an temple", "Shanghai Jingan District Jing'an Temple Street");
            areaMap.put("ningjing west road", "Shanghai Jingan District Ningjing West Road");

            ret = "Beijing Beijing Haiding Wanshoulu";
            for (Map.Entry<String, Object> entry : areaMap.entrySet()) {

                if (strValue.toLowerCase().contains(entry.getKey())) {
                    ret = entry.getValue().toString();
                }
            }
        }
        else if ("maxNumberofUEs".equalsIgnoreCase(key)) {
            Pattern pattern = Pattern.compile("(\\d+)");
            Matcher matcher = pattern.matcher(strValue);
            int maxNumber = 1;
            if (matcher.matches()) {
                maxNumber = Integer.parseInt(matcher.group(1));
                maxNumber = maxNumber < MIN_NUMBER_OF_UES ? MIN_NUMBER_OF_UES : (maxNumber > MAX_NUMBER_OF_UES ? MAX_NUMBER_OF_UES : maxNumber);
            }
            ret = maxNumber + "";
        }
        else if ("expDataRateDL".equalsIgnoreCase(key)) {
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
                dataRate = dataRate < MIN_EXP_DATA_RATE_DL ? MIN_EXP_DATA_RATE_DL : (dataRate > MAX_EXP_DATA_RATE_DL ? MAX_EXP_DATA_RATE_DL : dataRate);
            }

            ret = dataRate + "";
        }
        else if ("expDataRateUL".equalsIgnoreCase(key)) {
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
                dataRate = dataRate < MIN_EXP_DATA_RATE_UL ? MIN_EXP_DATA_RATE_UL : (dataRate > MAX_EXP_DATA_RATE_UL ? MAX_EXP_DATA_RATE_UL : dataRate);
            }
            ret = dataRate + "";
        }
        else if ("latency".equalsIgnoreCase(key)) {
            if ("default".equalsIgnoreCase(strValue)) {
                ret = MAX_LATENCY + "";
            }
            else if ("low".equalsIgnoreCase(strValue)) {
                ret = MIN_LATENCY + "";
            }
            else {
                Pattern pattern = Pattern.compile("(\\d+)([\\w ]*)");
                Matcher matcher = pattern.matcher(strValue);


                int dataRate = 10;
                if (matcher.matches()) {
                    dataRate = Integer.parseInt(matcher.group(1));
                    String company = matcher.group(2).trim().toLowerCase();
                    if ("s".equalsIgnoreCase(company)) {
                        dataRate = dataRate * 1000;
                    }
                    else if (!"ms".equalsIgnoreCase(company)) {
                        dataRate = MAX_LATENCY;
                    }
                    dataRate = dataRate < MIN_LATENCY ? MIN_LATENCY : (dataRate > MAX_LATENCY ? MAX_LATENCY : dataRate);
                }
                ret = dataRate + "";
            }

        }

        return ret;
    }

    private static String tranlateFieldName(String key){
        String ret = "";
        if(key==null || key.trim().equals(""))
            return ret;

        HashMap<String, String> map = new HashMap<>();
        map.put("通信服务名称","name");
        map.put("Communication service","name");
        map.put("最大用户设备数","maxNumberofUEs");
        map.put("Maximum user devices","maxNumberofUEs");
        map.put("下行链路数据速率","expDataRateDL");
        map.put("Downlink data rate","expDataRateDL");
        map.put("时延","latency");
        map.put("Time delay","latency");
        map.put("上行链路数据速率","expDataRateUL");
        map.put("Uplink data rate","expDataRateUL");
        map.put("资源共享类别","resourceSharingLevel");
        map.put("Resource","resourceSharingLevel");
        map.put("移动性","uEMobilityLevel");
        map.put("Mobility","uEMobilityLevel");
        map.put("区域","coverageArea");
        map.put("Region","coverageArea");

        ret = map.get(key.trim());
        return ret;
    }
}
