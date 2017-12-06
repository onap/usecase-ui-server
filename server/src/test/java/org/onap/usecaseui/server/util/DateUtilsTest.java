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
package org.onap.usecaseui.server.util;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.onap.usecaseui.server.constant.Constant;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateUtilsTest {

	@Test
	public void stringToDate() throws ParseException {
        Date result = DateUtils.stringToDate("2017-08-12 13:12:12");
        Assert.assertEquals(result,new SimpleDateFormat(Constant.DATE_FORMAT).parse("2017-08-12 13:12:12"));
	}

	@Test
	public void dateToString(){
        Assert.assertEquals(DateUtils.dateToString(new Date()),new SimpleDateFormat(Constant.DATE_FORMAT).format(new Date()));
    }

	@Test
	public void now() throws ParseException {
		Assert.assertNotNull(DateUtils.now());
	}

	@Test
	public void addDate() throws ParseException {
        Assert.assertNotNull(DateUtils.addDate(new Date(),"year",1));
        Assert.assertNotNull(DateUtils.addDate(new Date(),"month",1));
        Assert.assertNotNull(DateUtils.addDate(new Date(),"month",13));
        Assert.assertNotNull(DateUtils.addDate(new Date(),"day",1));
        Assert.assertNotNull(DateUtils.addDate(new Date(),"day",10));
        Assert.assertNotNull(DateUtils.addDate(new Date(),"hour",1));
        Assert.assertNotNull(DateUtils.addDate(new Date(),"hour",24));
        Assert.assertNotNull(DateUtils.addDate(new Date(),"minute",1));
	}




}
