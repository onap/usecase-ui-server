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

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsecaseuiServerApplicationTests {




	@Test
	public void contextLoads() throws JsonProcessingException, ParseException {
		/*ObjectMapper objectMapper = new ObjectMapper();
		AlarmsHeader alarmsHeader = new AlarmsHeader();
		alarmsHeader.setAlarmCondition("send to my phone");
		alarmsHeader.setAlarmInterfaceA("Baby have no fear");
		alarmsHeader.setCreateTime(new Date());
		alarmsHeader.setDomain("Hope they ready");
		List<AlarmsHeader> alarmsHeaders = new ArrayList<>();
		alarmsHeaders.add(alarmsHeader);
		alarmsHeaders.add(alarmsHeader);
		alarmsHeaders.add(alarmsHeader);
		Map<String,Object> map = new HashMap<>();
		map.put("alarms",alarmsHeaders);
		map.put("asd",alarmsHeader);
		String jsonStr = objectMapper.writeValueAsString(map);
		System.out.println(jsonStr);*/
		//System.out.println(alarmsInformationService.saveAlarmsInformation(new AlarmsInformation("11","22","123",new Date(),new Date())));
	}
}
