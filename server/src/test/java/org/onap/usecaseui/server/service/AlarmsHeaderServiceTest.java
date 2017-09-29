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
        a.setEventName("a1");
        a.setStatus("2");
        a.setVfStatus("3");
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

        alarmsHeaderService.queryId(new String[]{"1101"}).forEach( a -> System.out.println(a));
    }

    @Test
    public void queryEventName() throws ParseException {
      AlarmsHeader a=new AlarmsHeader();
      a.setEventName("a");
      System.out.println(alarmsHeaderService.queryAlarmsHeader(a,1,100).getList().size());
    }
    
    @Test
    public void queryStatus() throws ParseException {
      AlarmsHeader a=new AlarmsHeader();
      a.setStatus("1");
      System.out.println(alarmsHeaderService.queryAlarmsHeader(a,1,100).getList().size());
    }
    @Test
    public void queryEventId() throws ParseException {
      AlarmsHeader a=new AlarmsHeader();
      a.setEventId("1101");
      System.out.println(alarmsHeaderService.queryAlarmsHeader(a,1,100).getList().size());
    }
    @Test
    public void queryDomain() throws ParseException {
      AlarmsHeader a=new AlarmsHeader();
      a.setDomain("asb");
      System.out.println(alarmsHeaderService.queryAlarmsHeader(a,1,100).getList().size());
    }
    @Test
    public void queryEventCategory() throws ParseException {
      AlarmsHeader a=new AlarmsHeader();
      a.setEventCategory("s");
      System.out.println(alarmsHeaderService.queryAlarmsHeader(a,1,100).getList().size());
    }
    @Test
    public void queryAlarmCondition() throws ParseException {
      AlarmsHeader a=new AlarmsHeader();
      a.setAlarmCondition("ea");
      System.out.println(alarmsHeaderService.queryAlarmsHeader(a,1,100).getList().size());
    }
    @Test
    public void queryAlarmInterfaceA() throws ParseException {
      AlarmsHeader a=new AlarmsHeader();
      a.setAlarmInterfaceA("cs");
      System.out.println(alarmsHeaderService.queryAlarmsHeader(a,1,100).getList().size());
    }
    @Test
    public void queryEventServrity() throws ParseException {
      AlarmsHeader a=new AlarmsHeader();
      a.setEventServrity("s");
      System.out.println(alarmsHeaderService.queryAlarmsHeader(a,1,100).getList().size());
    }
    @Test
    public void queryEventSourceType() throws ParseException {
      AlarmsHeader a=new AlarmsHeader();
      a.setEventSourceType("q");
      System.out.println(alarmsHeaderService.queryAlarmsHeader(a,1,100).getList().size());
    }
    @Test
    public void queryEventType() throws ParseException {
      AlarmsHeader a=new AlarmsHeader();
      a.setEventType("q");
      System.out.println(alarmsHeaderService.queryAlarmsHeader(a,1,100).getList().size());
    }
    @Test
    public void queryFaultFieldsVersion() throws ParseException {
      AlarmsHeader a=new AlarmsHeader();
      a.setFaultFieldsVersion("v1");
      System.out.println(alarmsHeaderService.queryAlarmsHeader(a,1,100).getList().size());
    }
    @Test
    public void queryLastEpochMicroSec() throws ParseException {
      AlarmsHeader a=new AlarmsHeader();
      a.setLastEpochMicroSec("csa");
      System.out.println(alarmsHeaderService.queryAlarmsHeader(a,1,100).getList().size());
    }
    @Test
    public void queryNfcNamingCode() throws ParseException {
      AlarmsHeader a=new AlarmsHeader();
        a.setNfcNamingCode("std");
      System.out.println(alarmsHeaderService.queryAlarmsHeader(a,1,100).getList().size());
    }
    @Test
    public void queryNfNamingCode() throws ParseException {
      AlarmsHeader a=new AlarmsHeader();
	  a.setNfNamingCode("cout");
      System.out.println(alarmsHeaderService.queryAlarmsHeader(a,1,100).getList().size());
    }
    @Test
    public void queryPriority() throws ParseException {
      AlarmsHeader a=new AlarmsHeader();
	  a.setPriority("cs");
      System.out.println(alarmsHeaderService.queryAlarmsHeader(a,1,100).getList().size());
    }
    @Test
    public void queryReportingEntityId() throws ParseException {
      AlarmsHeader a=new AlarmsHeader();
	  a.setReportingEntityId("112");
      System.out.println(alarmsHeaderService.queryAlarmsHeader(a,1,100).getList().size());
    }
    @Test
    public void queryReportingEntityName() throws ParseException {
      AlarmsHeader a=new AlarmsHeader();
	  a.setReportingEntityName("asfs");
      System.out.println(alarmsHeaderService.queryAlarmsHeader(a,1,100).getList().size());
    }
    @Test
    public void querySequence() throws ParseException {
      AlarmsHeader a=new AlarmsHeader();
	  a.setSequence("cgg");
      System.out.println(alarmsHeaderService.queryAlarmsHeader(a,1,100).getList().size());
    }
    @Test
    public void querySourceId() throws ParseException {
      AlarmsHeader a=new AlarmsHeader();
	  a.setSourceId("123");
      System.out.println(alarmsHeaderService.queryAlarmsHeader(a,1,100).getList().size());
    }
    @Test
    public void querySourceName() throws ParseException {
      AlarmsHeader a=new AlarmsHeader();
	  a.setSourceName("eggs");
      System.out.println(alarmsHeaderService.queryAlarmsHeader(a,1,100).getList().size());
    }
    @Test
    public void querySpecificProblem() throws ParseException {
      AlarmsHeader a=new AlarmsHeader();
	  a.setSpecificProblem("especially");
      System.out.println(alarmsHeaderService.queryAlarmsHeader(a,1,100).getList().size());
    }
    @Test
    public void queryStartEpochMicrosec() throws ParseException {
      AlarmsHeader a=new AlarmsHeader();
	  a.setStartEpochMicrosec("wallet");
      System.out.println(alarmsHeaderService.queryAlarmsHeader(a,1,100).getList().size());
    }
    @Test
    public void queryVersion() throws ParseException {
      AlarmsHeader a=new AlarmsHeader();
	  a.setVersion("va2");
      System.out.println(alarmsHeaderService.queryAlarmsHeader(a,1,100).getList().size());
    }
    @Test
    public void queryVfStatus() throws ParseException {
      AlarmsHeader a=new AlarmsHeader();
      a.setVfStatus("1");
      System.out.println(alarmsHeaderService.queryAlarmsHeader(a,1,100).getList().size());
    }
    @Test
    public void queryCreateTime() throws ParseException {
      AlarmsHeader a=new AlarmsHeader();
      a.setCreateTime(DateUtils.now());
      System.out.println(alarmsHeaderService.queryAlarmsHeader(a,1,100).getList().size());
    }
    @Test
    public void queryUpdateTime() throws ParseException {
      AlarmsHeader a=new AlarmsHeader();
      a.setUpdateTime(DateUtils.now());
      System.out.println(alarmsHeaderService.queryAlarmsHeader(a,1,100).getList().size());
    }

}
