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

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DateUtilsTest {


	@Test
	public void stringToDate(){
		try {
			System.out.println(DateUtils.stringToDate("2017-09-28 16:00:00"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void dateToString(){
		System.out.println(DateUtils.dateToString(new Date()));
	}

	@Test
	public void initDate(){
		try {
			System.out.println(DateUtils.initDate(new Date(),0,0,0,0,0,0));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void initProcessDate(){
		try {
			System.out.println(DateUtils.initProcessDate(new Date(),0,0,0,0,0,0));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void now(){
		try {
			System.out.println(DateUtils.now());
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void addDate(){
		try {
			System.out.println(DateUtils.addDate(new Date(),"hour",5));
			System.out.println(DateUtils.addDate(new Date(),"day",5));
			System.out.println(DateUtils.addDate(new Date(),"month",5));
			System.out.println(DateUtils.addDate(new Date(),"year",5));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

}
