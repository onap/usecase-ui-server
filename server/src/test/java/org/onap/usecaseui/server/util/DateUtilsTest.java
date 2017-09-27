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

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DateUtilsTest {


    @Test
    public void DateTest(){
        SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		LocalDateTime ldt = LocalDateTime.now();
		ldt = ldt.withMonth(2);
		ldt = ldt.withDayOfMonth(30);
		//ldt = ldt.withMonth(1);
		ldt = ldt.withHour(1);
		ZoneId zone = ZoneId.systemDefault();
		Instant instant = ldt.atZone(zone).toInstant();
		System.out.println(ldt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }

}
