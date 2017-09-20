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
package org.onap.usecaseui.server;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.onap.usecaseui.server.bean.AlarmsHeader;
import org.onap.usecaseui.server.bean.AlarmsInformation;
import org.onap.usecaseui.server.service.AlarmsHeaderService;
import org.onap.usecaseui.server.service.AlarmsInformationService;
import org.onap.usecaseui.server.service.impl.AlarmsHeaderServiceImpl;
import org.onap.usecaseui.server.service.impl.AlarmsInformationServiceImpl;
import org.onap.usecaseui.server.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsecaseuiServerApplicationTests {
	
//	@Autowired
//	AlarmsInformationService info;
	
	@Autowired
	AlarmsHeaderService alarm;
	
	@Test
	public void contextLoads() throws ParseException {
//		AlarmsInformation in= new AlarmsInformation();
//		Date date =new Date();
//		in.setName("name");
//		in.setEventId("eventId");
//		in.setValue("value");
//		in.setCreateTime(date);
//		in.setUpdateTime(date);
//		String d=info.saveAlarmsInformation(in);
//		String d=info.updateAlarmsInformation(in);
		
//		int c=alarm.getAllCount();
		
		AlarmsHeader a = new AlarmsHeader();
//		a.setVersion("1");
		a.setDomain("3");
//		SimpleDateFormat e =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		a.setCreateTime(e.parse("2017-09-03 15:25:11"));
		Page<AlarmsHeader> b = alarm.queryAlarmsHeader(a, 1, 5);
		String c =b.getList().get(0).getEventName();
		System.out.println("============"+c);
		Assert.assertEquals("2", c);
		
	}
}
