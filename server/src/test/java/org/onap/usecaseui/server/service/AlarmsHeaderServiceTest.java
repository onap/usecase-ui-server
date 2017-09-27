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
package org.onap.usecaseui.server.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.onap.usecaseui.server.bean.AlarmsHeader;
import org.onap.usecaseui.server.util.DateUtils;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.text.ParseException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AlarmsHeaderServiceTest {

    @Resource(name = "AlarmsHeaderService")
    private AlarmsHeaderService alarmsHeaderService;



    @Test
    public void save() throws ParseException {
        AlarmsHeader a = new AlarmsHeader();
        a.setEventName("a");
        a.setStatus("1");
        a.setVfStatus("1");
        a.setEventId("1101");
        a.setDomain("asb");
        a.setEventCategory("s");
        a.setAlarmCondition("ea");
        a.setAlarmInterfaceA("cs");
        a.setCreateTime(DateUtils.now());
        a.setEventServrity("s");
        a.setEventSourceType("q");
        a.setEventType("q");
        a.setFaultFieldsVersion("v1");
        a.setLastEpochMicroSec("csa");
        a.setNfcNamingCode("std");
        a.setNfNamingCode("cout");
        a.setPriority("cs");
        a.setReportingEntityId("112");
        a.setReportingEntityName("asfs");
        a.setSequence("cgg");
        a.setSourceId("123");
        a.setSourceName("eggs");
        a.setSpecificProblem("especially");
        a.setStartEpochMicrosec("wallet");
        a.setUpdateTime(DateUtils.now());
        a.setVersion("va2");
        System.out.println(alarmsHeaderService.saveAlarmsHeader(a));

    }
    @Test
    public void update() throws ParseException {
        AlarmsHeader a = new AlarmsHeader();
        a.setEventName("a");
        a.setStatus("1");
        a.setVfStatus("1");
        a.setEventId("1101");
        a.setDomain("asb");
        a.setEventCategory("s");
        a.setAlarmCondition("ea");
        a.setAlarmInterfaceA("cs");
        a.setCreateTime(DateUtils.now());
        a.setEventServrity("s");
        a.setEventSourceType("q");
        a.setEventType("q");
        a.setFaultFieldsVersion("v1");
        a.setLastEpochMicroSec("csa");
        a.setNfcNamingCode("std");
        a.setNfNamingCode("cout");
        a.setPriority("cs");
        a.setReportingEntityId("112");
        a.setReportingEntityName("asfs");
        a.setSequence("cgg");
        a.setSourceId("123");
        a.setSourceName("eggs");
        a.setSpecificProblem("especially");
        a.setStartEpochMicrosec("wallet");
        a.setUpdateTime(DateUtils.now());
        a.setVersion("va2");
        System.out.println(alarmsHeaderService.updateAlarmsHeader(a));
    }

    @Test
    public void get(){

        alarmsHeaderService.queryId(new String[]{"110"}).forEach( a -> System.out.println(a));
    }

    @Test
    public void query() throws ParseException {
      AlarmsHeader a=new AlarmsHeader();
        a.setEventName("a");
        a.setStatus("1");
        a.setVfStatus("1");
        a.setEventId("1101");
        a.setDomain("asb");
        a.setEventCategory("s");
        a.setAlarmCondition("ea");
        a.setAlarmInterfaceA("cs");
        a.setCreateTime(DateUtils.now());
        a.setEventServrity("s");
        a.setEventSourceType("q");
        a.setEventType("q");
        a.setFaultFieldsVersion("v1");
        a.setLastEpochMicroSec("csa");
        a.setNfcNamingCode("std");
        a.setNfNamingCode("cout");
        a.setPriority("cs");
        a.setReportingEntityId("112");
        a.setReportingEntityName("asfs");
        a.setSequence("cgg");
        a.setSourceId("123");
        a.setSourceName("eggs");
        a.setSpecificProblem("especially");
        a.setStartEpochMicrosec("wallet");
        a.setUpdateTime(DateUtils.now());
        a.setVersion("va2");
      System.out.println(alarmsHeaderService.queryAlarmsHeader(a,1,100).getList().size());
    }

}
