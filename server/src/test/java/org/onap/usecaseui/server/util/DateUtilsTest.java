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

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Date;

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
	public void TestGetYearMonthDayHourMinuteSecond(){
		System.out.println(DateUtils.getYearMonthDayHourMinuteSecond(1527145109000L));
		System.out.println(DateUtils.getYearMonthDayHourMinuteSecond(1514736000000L));
	}
	@Test
	public void TestGetResultDate(){
		System.out.println(DateUtils.getResultDate(1514736000000L,"yyyy-MM-dd"));
		System.out.println(DateUtils.getResultDate(1527145109000L,"month"));
		System.out.println(DateUtils.getResultDate(1527145109000L,"day"));
		System.out.println(DateUtils.getResultDate(1527145109000L,"minute"));
		System.out.println(DateUtils.getResultDate(1527145109000L,"hour"));
	}
	@Test
	public void TestMonthOfDay(){
		System.out.println(DateUtils.MonthOfDay("2018-02-12","yyyy-MM-dd"));
		System.out.println(DateUtils.MonthOfDay("dateTime","yyyy-MM-dd"));
	}
	
	@Test
	public void TestAddDate() throws ParseException{
		DateUtils.addDate(new Date(), "year", 1);
		DateUtils.addDate(new Date(), "day", 1);
		DateUtils.addDate(new Date(), "hour", 1);
		DateUtils.addDate(new Date(), "minute", 1);
	}
}
