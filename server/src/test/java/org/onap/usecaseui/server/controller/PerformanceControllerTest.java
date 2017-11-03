/*
 * Copyright (C) 2017 CMCC, Inc. and others. All rights reserved.
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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.onap.usecaseui.server.bean.PerformanceHeader;
import org.onap.usecaseui.server.bean.PerformanceInformation;
import org.onap.usecaseui.server.service.PerformanceHeaderService;
import org.onap.usecaseui.server.service.PerformanceInformationService;
import org.onap.usecaseui.server.util.CSVUtils;
import org.onap.usecaseui.server.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PerformanceControllerTest {

    @Autowired
    private PerformanceController performanceController;

    @Test
    public void getPerformanceData() throws JsonProcessingException {
        System.out.println(performanceController.getPerformanceData(null,1,100,null,null,null,null,null));
    }

    @Test
    public void generateCsvFile() throws JsonProcessingException {
        System.out.println(performanceController.getPerformanceData(null,1,100,"110","fxc","efw","fre","2017-09-28 10:00:00"));
        System.out.println(performanceController.getPerformanceData(null,1,100,"110","fxc","efw","fre","null"));
        System.out.println(performanceController.getPerformanceData(null,1,100,"110","fxc","efw","null","null"));
        System.out.println(performanceController.getPerformanceData(null,1,100,"110","fxc","null","null","null"));
        System.out.println(performanceController.getPerformanceData(null,1,100,"110","null","null","null","null"));
        System.out.println(performanceController.getPerformanceData(null,1,100,"null","null","null","null","null"));
        System.out.println(performanceController.getPerformanceData(null,1,100,"110","fxc","efw","null","asdasdasda"));


    }

    @Test
    public void generateDiaCsvFile() throws JsonProcessingException {
        Map<String,String> p = new HashMap<>();
        p.put("eventId","110");
        p.put("name","110");
        p.put("value","110");
        p.put("createTime","110");
        p.put("updateTime","110");
        //System.out.println(performanceController.generateDiaCsvFile(null,p));
    }

    @Test
    public void generateDiagram(){
        try {
            System.out.println(performanceController.generateDiagram("hour","110"));
            System.out.println(performanceController.generateDiagram("day","110"));
            System.out.println(performanceController.generateDiagram("month","110"));
            System.out.println(performanceController.generateDiagram("year","110"));
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }


}
