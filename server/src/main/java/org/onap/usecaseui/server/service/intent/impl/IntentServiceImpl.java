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

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.onap.usecaseui.server.bean.intent.IntentModel;
import org.onap.usecaseui.server.service.intent.IntentService;
import org.onap.usecaseui.server.util.ZipUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Service;

@Service("IntentService")
@Transactional
@org.springframework.context.annotation.Configuration
@EnableAspectJAutoProxy
public class IntentServiceImpl implements IntentService {
    private static final Logger logger = LoggerFactory.getLogger(IntentServiceImpl.class);

    private final static String UPLOADPATH = "/home/uui/upload/";

    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.openSession();
    }

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

    @Override
    public String addModel(IntentModel model) {
        try(Session session = getSession()){
            if (null == model){
                logger.error("IntentServiceImpl addModel model is null!");
                return "0";
            }
            Transaction tx = session.beginTransaction();
            session.save(model);
            tx.commit();
            session.flush();
            return "1";
        } catch (Exception e) {
            logger.error("Details:" + e.getMessage());
            return "0";
        }


    }

    public List<IntentModel> listModels(){
        try(Session session = getSession()){
            StringBuffer hql =new StringBuffer("from IntentModel a where 1=1 ");
            Query query = session.createQuery(hql.toString());
            //query.setString("sortType",sortType);
            List<IntentModel> list= query.list();
            return list;
        } catch (Exception e) {
            logger.error("Details:" + e.getMessage());
            return Collections.emptyList();
        }
    }

    public IntentModel getModel(String modelId){
        //Transaction tx = null;
        IntentModel result = null;

        try(Session session = getSession()) {
            //tx = session.beginTransaction();

            result = (IntentModel)session.createQuery("from IntentModel where id = :modelId")
                    .setParameter("modelId", Integer.parseInt(modelId)).uniqueResult();
            logger.info("get model OK, id=" + modelId);

        } catch (Exception e) {
            logger.error("getodel occur exception:"+e);

        }

        return result;
    }

    public String deleteModel(String modelId){
        Transaction tx = null;
        String result="0";
        if(modelId==null || modelId.trim().equals(""))
            return  result;

        try(Session session = getSession()) {
            tx = session.beginTransaction();

            IntentModel model = new IntentModel();
            model.setId(Integer.parseInt(modelId));
            session.delete(model);
            tx.commit();
            logger.info("delete model OK, id=" + modelId);

            result="1";
        } catch (Exception e) {
            if(tx!=null){
                tx.rollback();
            }
            logger.error("deleteModel occur exception:"+e);

        }
        return result;
    }

    public IntentModel activeModel(String modelId){
        Transaction tx = null;
        IntentModel result=null;
        if(modelId==null || modelId.trim().equals(""))
            return result;

        try(Session session = getSession()) {
            tx = session.beginTransaction();
            List<IntentModel> list = session.createQuery("from IntentModel where active=1").list();
            if(list!=null && list.size()>0){
                for (IntentModel m : list) {
                    m.setActive(0);
                    session.save(m);
                }
            }

            IntentModel model = (IntentModel)session.createQuery("from IntentModel where id = :modelId")
                    .setParameter("modelId", Integer.parseInt(modelId)).uniqueResult();
            model.setActive(1);
            session.save(model);
            tx.commit();
            logger.info("active model OK, id=" + modelId);

            result = model;
        } catch (Exception e) {
            if(tx!=null){
                tx.rollback();
            }
            logger.error("deleteModel occur exception:"+e);

        }
        return result;
    }

    @Override
    public String activeModelFile(IntentModel model) {
        if (model == null) {
            return null;
        }
        String filePath = model.getFilePath();
        if (filePath == null) {
            return null;
        }
        else if (filePath.endsWith(".zip")){
            try {
                File file = new File(filePath);
                String parentPath = file.getParent();
                String unzipPath = filePath.substring(0, filePath.length() - 4);
                File unZipFile = new File(unzipPath);
                if (!unZipFile.exists()) {
                    ZipUtil.unzip(file,parentPath);
                }
                return unzipPath;

            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return filePath;
    }

    public String calcFieldValue(String key, String strValue){
        String ret = "";


        if(strValue==null)
            strValue = "";
        else
            ret = strValue.trim();

        if("resourceSharingLevel".equalsIgnoreCase(key)){
            ret = formatValueForResourcesSharingLevel(strValue);
        }
        else if("uEMobilityLevel".equalsIgnoreCase(key)){
            ret = formatValueForUEMobilitylevel(strValue);
        }
        else if("coverageArea".equalsIgnoreCase(key)){

            ret = formatValueForCoverageArea(strValue);
        }
        else if ("maxNumberofUEs".equalsIgnoreCase(key)) {
            ret = formatValueForMaxNumberofUEs(strValue);
        }
        else if ("expDataRateDL".equalsIgnoreCase(key)) {
            ret = formatValueForExpDataRateDL(strValue);
        }
        else if ("expDataRateUL".equalsIgnoreCase(key)) {
            ret = formatValueForExpDataRateUL(strValue);
        }
        else if ("latency".equalsIgnoreCase(key)) {
            ret = formatValueForLatency(strValue);

        }

        return ret;
    }

    private String formatValueForLatency(String strValue) {
        String ret;
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
        return ret;
    }

    private String formatValueForExpDataRateUL(String strValue) {
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
            dataRate = dataRate < MIN_EXP_DATA_RATE_UL ? MIN_EXP_DATA_RATE_UL : (dataRate > MAX_EXP_DATA_RATE_UL ? MAX_EXP_DATA_RATE_UL : dataRate);
        }
        ret = dataRate + "";
        return ret;
    }

    private String formatValueForExpDataRateDL(String strValue) {
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
            dataRate = dataRate < MIN_EXP_DATA_RATE_DL ? MIN_EXP_DATA_RATE_DL : (dataRate > MAX_EXP_DATA_RATE_DL ? MAX_EXP_DATA_RATE_DL : dataRate);
        }

        ret = dataRate + "";
        return ret;
    }

    private String formatValueForMaxNumberofUEs(String strValue) {
        String ret;
        Pattern pattern = Pattern.compile("(\\d+)");
        Matcher matcher = pattern.matcher(strValue);
        int maxNumber = 1;
        if (matcher.matches()) {
            maxNumber = Integer.parseInt(matcher.group(1));
            maxNumber = maxNumber < MIN_NUMBER_OF_UES ? MIN_NUMBER_OF_UES : (maxNumber > MAX_NUMBER_OF_UES ? MAX_NUMBER_OF_UES : maxNumber);
        }
        ret = maxNumber + "";
        return ret;
    }

    private String formatValueForCoverageArea(String strValue) {
        String ret;
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
        return ret;
    }

    private String formatValueForUEMobilitylevel(String strValue) {
        String ret;
        ret = "stationary";
        if(strValue.contains("Nomadic")){
            ret = "nomadic";
        }
        else if(strValue.contains("restricted")){
            ret = "Spatially Restricted Mobility";
        }
        else if(strValue.contains("fully")){
            ret = "Fully Mobility";
        }
        return ret;
    }

    private String formatValueForResourcesSharingLevel(String strValue) {
        String ret;
        ret = "no-shared";
        if("shared".equalsIgnoreCase(strValue)){
            ret = "shared";
        }
        return ret;
    }
}
