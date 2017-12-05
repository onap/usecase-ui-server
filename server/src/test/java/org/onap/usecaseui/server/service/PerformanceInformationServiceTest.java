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
import org.onap.usecaseui.server.bean.PerformanceInformation;
import org.onap.usecaseui.server.util.DateUtils;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.List;
import java.util.Random;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PerformanceInformationServiceTest {


    @Resource(name = "PerformanceInformationService")
    PerformanceInformationService performanceInformationService;

    @Test
    public void save() throws ParseException {
        PerformanceInformation a = new PerformanceInformation();

        a.setEventId("123");
        a.setName("SGS.UeUnreachable");
        a.setValue("40");
        a.setCreateTime(DateUtils.now());
        a.setUpdateTime(DateUtils.now());
        System.out.println(performanceInformationService.savePerformanceInformation(a));


    }

    @Test
    public void update() throws ParseException {
        PerformanceInformation a = new PerformanceInformation();
        a.setEventId("110");
        a.setName("efw");
        a.setValue("fko11");
        a.setUpdateTime(DateUtils.now());
        a.setCreateTime(DateUtils.now());
        System.out.println(performanceInformationService.updatePerformanceInformation(a));
    }

    @Test
    public void update1() throws ParseException {
        List<PerformanceInformation> as = performanceInformationService.queryId(new String[]{"2202"});
        as.forEach(a -> {
            try {
                a.setCreateTime(DateUtils.stringToDate(DateUtils.addDate(a.getCreateTime(), "day", 1)));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            System.out.println(performanceInformationService.updatePerformanceInformation(a));
        });
    }

    @Test
    public void get() throws ParseException {
        performanceInformationService.queryId(new String[]{"2202"})
                .forEach(ai -> System.out.println(ai.getCreateTime()));
    }

    @Test
    public void queryEventId() throws ParseException {
        PerformanceInformation a = new PerformanceInformation();
        // a.setEventId("2202");
        performanceInformationService.queryPerformanceInformation(a, 1, 100)
                .getList().forEach(al -> System.out.println(al.getValue()));
    }

    @Test
    public void queryName() throws ParseException {
        PerformanceInformation a = new PerformanceInformation();
        a.setName("kl");
        performanceInformationService.queryPerformanceInformation(a, 1, 100)
                .getList().forEach(al -> System.out.println(al));
    }

    @Test
    public void queryValue() throws ParseException {
        PerformanceInformation a = new PerformanceInformation();
        a.setValue("yue");
        performanceInformationService.queryPerformanceInformation(a, 1, 100)
                .getList().forEach(al -> System.out.println(al));
    }

    @Test
    public void queryUpdateTime() throws ParseException {
        PerformanceInformation a = new PerformanceInformation();
        a.setUpdateTime(DateUtils.now());
        performanceInformationService.queryPerformanceInformation(a, 1, 100)
                .getList().forEach(al -> System.out.println(al));
    }

    @Test
    public void queryCreateTime() throws ParseException {
        PerformanceInformation a = new PerformanceInformation();
        a.setCreateTime(DateUtils.now());
        performanceInformationService.queryPerformanceInformation(a, 1, 100)
                .getList().forEach(al -> System.out.println(al));
    }

    @Test
    public void between() throws ParseException {
        performanceInformationService.queryDateBetween("2202", DateUtils.stringToDate("2017-10-15 01:00:00"), DateUtils.stringToDate("2017-10-15 02:00:00")).forEach(p -> System.out.println(p));
    }


}
