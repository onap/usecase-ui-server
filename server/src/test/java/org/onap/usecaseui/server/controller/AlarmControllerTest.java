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
import org.junit.Test;
import org.junit.runner.RunWith;
import org.onap.usecaseui.server.bean.AlarmsHeader;
import org.onap.usecaseui.server.bean.AlarmsInformation;
import org.onap.usecaseui.server.service.AlarmsHeaderService;
import org.onap.usecaseui.server.service.AlarmsInformationService;
import org.onap.usecaseui.server.util.CSVUtils;
import org.onap.usecaseui.server.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AlarmControllerTest {

    @Autowired
    AlarmController alarmController;

    @Test
    public void getDataNotParam() throws JsonProcessingException {
       System.out.println(alarmController.getAlarmData(null,null,null,null,null,null,null,1,100));
    }

    @Test
    public void getDataCarryParam() throws JsonProcessingException {
        System.out.println(alarmController.getAlarmData("110","a","drop","down","1506331166000","1","2",1,100));
    }

    @Test
    public void csvFile() throws JsonProcessingException {
        System.out.println(alarmController.generateCsvFile(null,new String[]{"110"}));
    }

    @Test
    public void update() throws JsonProcessingException {
        System.out.println(alarmController.updateStatus(null,new String[]{"110"},new String[]{"1"},"s"));
        System.out.println(alarmController.updateStatus(null,new String[]{"110","1101"},new String[]{"1","1"},"many"));
        System.out.println(alarmController.updateStatus(null,new String[]{"110"},new String[]{"1"},"vf"));

    }

}
