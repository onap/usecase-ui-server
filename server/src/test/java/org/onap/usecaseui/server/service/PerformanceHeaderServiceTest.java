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
import org.onap.usecaseui.server.bean.AlarmsInformation;
import org.onap.usecaseui.server.bean.PerformanceHeader;
import org.onap.usecaseui.server.util.DateUtils;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import javax.annotation.Resource;
import java.text.ParseException;

import static org.apache.coyote.http11.Constants.a;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PerformanceHeaderServiceTest {


    @Resource(name = "PerformanceHeaderService")
    PerformanceHeaderService performanceHeaderService;

    @Test
    public void save() throws ParseException {
        PerformanceHeader p = new PerformanceHeader();
        p.setCreateTime(DateUtils.now());
        p.setEventId("110");
        p.setDomain("asb");
        p.setCreateTime(DateUtils.now());
        p.setEventType("q");
        p.setLastEpochMicroSec("csa");
        p.setNfcNamingCode("std");
        p.setNfNamingCode("cout");
        p.setPriority("cs");
        p.setReportingEntityId("112");
        p.setReportingEntityName("asfs");
        p.setSequence("cgg");
        p.setSourceId("123");
        p.setSourceName("eggs");
        p.setStartEpochMicrosec("wallet");
        p.setUpdateTime(DateUtils.now());
        p.setVersion("va2");
        p.setMeasurementInterval("12");
        p.setMeasurementsForVfScalingVersion("12");

        System.out.println(performanceHeaderService.savePerformanceHeader(p));
    }

    @Test
    public void update() throws ParseException {
        PerformanceHeader p = new PerformanceHeader();
        p.setEventId("110");
        p.setPriority("we");
        p.setReportingEntityName("xddw");
        p.setSequence("dwd");
        p.setSourceName("swde");
        p.setStartEpochMicrosec("lala");
        p.setVersion("bgf3");
        p.setEventName("ds");
        p.setDomain("ef");
        p.setEventType("l");
        p.setLastEpochMicroSec("vf");
        p.setNfcNamingCode("vds");
        p.setNfNamingCode("f");
        p.setCreateTime(DateUtils.now());
        p.setMeasurementInterval("cdhs");
        System.out.println(performanceHeaderService.updatePerformanceHeader(p));
    }

    @Test
    public void get() throws ParseException {
        performanceHeaderService.queryId(new String[]{"110"})
                .forEach(pe -> System.out.println(pe));
    }

    @Test
    public void query() throws ParseException {
        PerformanceHeader p = new PerformanceHeader();
        p.setEventId("110");
        p.setPriority("we");
        p.setReportingEntityName("xddw");
        p.setSequence("dwd");
        p.setSourceName("swde");
        p.setStartEpochMicrosec("lala");
        p.setVersion("bgf3");
        p.setEventName("ds");
        p.setDomain("ef");
        p.setEventType("l");
        p.setLastEpochMicroSec("vf");
        p.setNfcNamingCode("vds");
        p.setNfNamingCode("f");
        p.setCreateTime(DateUtils.now());
        performanceHeaderService.queryPerformanceHeader(p,1,100)
                .getList().forEach(per -> System.out.println(per));
    }

}

