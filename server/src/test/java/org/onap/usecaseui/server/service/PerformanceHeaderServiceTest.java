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
import org.onap.usecaseui.server.bean.PerformanceHeader;
import org.onap.usecaseui.server.util.DateUtils;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import javax.annotation.Resource;
import java.text.ParseException;

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
        p.setEventName("fxc");
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
         p.setCreateTime(DateUtils.now());
         p.setEventId("110");
         p.setEventName("fxc");
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
        System.out.println(performanceHeaderService.updatePerformanceHeader(p));
    }

    @Test
    public void get() throws ParseException {
        performanceHeaderService.queryId(new String[]{"110"})
                .forEach(pe -> System.out.println(pe));
    }

    @Test
    public void queryEventId() throws ParseException {
        PerformanceHeader p = new PerformanceHeader();
        p.setEventId("110");
        performanceHeaderService.queryPerformanceHeader(p,1,100)
                .getList().forEach(per -> System.out.println(per));
    }
    @Test
    public void queryEventName() throws ParseException {
        PerformanceHeader p = new PerformanceHeader();
        p.setEventName("fxc");
        performanceHeaderService.queryPerformanceHeader(p,1,100)
                .getList().forEach(per -> System.out.println(per));
    }
    @Test
    public void queryDomain() throws ParseException {
        PerformanceHeader p = new PerformanceHeader();
        p.setDomain("asb");
        performanceHeaderService.queryPerformanceHeader(p,1,100)
                .getList().forEach(per -> System.out.println(per));
    }
    @Test
    public void queryEventType() throws ParseException {
        PerformanceHeader p = new PerformanceHeader();
        p.setEventType("q");
        performanceHeaderService.queryPerformanceHeader(p,1,100)
                .getList().forEach(per -> System.out.println(per));
    }
    @Test
    public void queryLastEpochMicroSec() throws ParseException {
        PerformanceHeader p = new PerformanceHeader();
        p.setLastEpochMicroSec("csa");
        performanceHeaderService.queryPerformanceHeader(p,1,100)
                .getList().forEach(per -> System.out.println(per));
    }
    @Test
    public void queryNfcNamingCode() throws ParseException {
        PerformanceHeader p = new PerformanceHeader();
        p.setNfcNamingCode("std");
        performanceHeaderService.queryPerformanceHeader(p,1,100)
                .getList().forEach(per -> System.out.println(per));
    }
    @Test
    public void queryNfNamingCode() throws ParseException {
        PerformanceHeader p = new PerformanceHeader();
        p.setNfNamingCode("cout");
        performanceHeaderService.queryPerformanceHeader(p,1,100)
                .getList().forEach(per -> System.out.println(per));
    }
    @Test
    public void queryPriority() throws ParseException {
        PerformanceHeader p = new PerformanceHeader();
        p.setPriority("cs");
        performanceHeaderService.queryPerformanceHeader(p,1,100)
                .getList().forEach(per -> System.out.println(per));
    }
    @Test
    public void queryReportingEntityId() throws ParseException {
        PerformanceHeader p = new PerformanceHeader();
        p.setReportingEntityId("112");
        performanceHeaderService.queryPerformanceHeader(p,1,100)
                .getList().forEach(per -> System.out.println(per));
    }
    @Test
    public void queryReportingEntityName() throws ParseException {
        PerformanceHeader p = new PerformanceHeader();
        p.setReportingEntityName("asfs");
        performanceHeaderService.queryPerformanceHeader(p,1,100)
                .getList().forEach(per -> System.out.println(per));
    }
    @Test
    public void querySequence() throws ParseException {
        PerformanceHeader p = new PerformanceHeader();
        p.setSequence("cgg");
        performanceHeaderService.queryPerformanceHeader(p,1,100)
                .getList().forEach(per -> System.out.println(per));
    }
    @Test
    public void querySourceId() throws ParseException {
        PerformanceHeader p = new PerformanceHeader();
        p.setSourceId("123");
        performanceHeaderService.queryPerformanceHeader(p,1,100)
                .getList().forEach(per -> System.out.println(per));
    }
    @Test
    public void querySourceName() throws ParseException {
        PerformanceHeader p = new PerformanceHeader();
        p.setSourceName("eggs");
        performanceHeaderService.queryPerformanceHeader(p,1,100)
                .getList().forEach(per -> System.out.println(per));
    }
    @Test
    public void queryStartEpochMicrosec() throws ParseException {
        PerformanceHeader p = new PerformanceHeader();
        p.setStartEpochMicrosec("wallet");
        performanceHeaderService.queryPerformanceHeader(p,1,100)
                .getList().forEach(per -> System.out.println(per));
    }
    @Test
    public void queryVersion() throws ParseException {
        PerformanceHeader p = new PerformanceHeader();
        p.setVersion("va2");
        performanceHeaderService.queryPerformanceHeader(p,1,100)
                .getList().forEach(per -> System.out.println(per));
    }
    @Test
    public void queryMeasurementInterval() throws ParseException {
        PerformanceHeader p = new PerformanceHeader();
        p.setMeasurementInterval("12");
        performanceHeaderService.queryPerformanceHeader(p,1,100)
                .getList().forEach(per -> System.out.println(per));
    }
    @Test
    public void queryMeasurementsForVfScalingVersion() throws ParseException {
        PerformanceHeader p = new PerformanceHeader();
        p.setMeasurementsForVfScalingVersion("12");
        performanceHeaderService.queryPerformanceHeader(p,1,100)
                .getList().forEach(per -> System.out.println(per));
    }

}

