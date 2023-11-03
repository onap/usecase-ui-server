/*
 * Copyright (C) 2018 CMCC, Inc. and others. All rights reserved.
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

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.onap.usecaseui.server.service.AlarmsHeaderService;
import org.onap.usecaseui.server.service.AlarmsInformationService;
import org.onap.usecaseui.server.service.PerformanceHeaderService;
import org.onap.usecaseui.server.service.PerformanceInformationService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyObject;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DmaapSubscriberTest {
	@InjectMocks
	DmaapSubscriber dmaap;

	@Mock
	PerformanceHeaderService performanceHeaderService;

	@Mock
	PerformanceInformationService performanceInformationService;

	@Mock
	AlarmsHeaderService alarmsHeaderService;

	@Mock
	AlarmsInformationService alarmsInformationService;

	@Test
	public void initConfigTest(){
		dmaap.initConfig();
	}
	
	@Test
	public void alarmProcessTest() throws JsonParseException, JsonMappingException, IOException{
		when(alarmsHeaderService.updateAlarmsHeader2018(eq("close"),any(),anyString(),anyString(),anyString(),anyString(),anyString())).thenReturn("mm");
		List<String> list = new ArrayList<>();
		ObjectMapper objMapper = new ObjectMapper();
		list.add("{\"event\":{\"commonEventHeader\":{\"sourceId\":\"vnf_test_3\",\"startEpochMicrosec\":1516784364869,\"eventId\":\"ab305d54-85b4-a31b-7db2-fb6b9e546015\",\"sequence\":0,\"domain\":\"fault\",\"lastEpochMicrosec\":1516784364869,\"eventName\":\"Fault_MultiCloud_VMFailureCleared\",\"sourceName\":\"vSBC\",\"priority\":\"High\",\"version\":3.0,\"reportingEntityName\":\"vnf_test_3_rname\"},\"faultFields\":{\"eventSeverity\":\"CRITICAL\",\"alarmCondition\":\"Guest_Os_Failure\",\"faultFieldsVersion\":2.0,\"specificProblem\":\"Fault_MultiCloud_VMFailure\",\"alarmInterfaceA\":\"aaaa\",\"alarmAdditionalInformation\":[{\"name\":\"objectType\",\"value\":\"VIM\"},{\"name\":\"eventTime\",\"value\":\"2017-10-31 09:51:15\"}],\"eventSourceType\":\"other\",\"vfStatus\":\"Active\"}}}");
		Map<String, Object> eventMaps = (Map<String, Object>) objMapper.readValue(list.get(0), Map.class).get("event");
		dmaap.alarmProcess(eventMaps);
		
	}
	
	@Test
	public void performanceProcessTest() throws JsonParseException, JsonMappingException, IOException{
		when(performanceHeaderService.savePerformanceHeader(any())).thenReturn("xx");
		when(performanceInformationService.savePerformanceInformation(any())).thenReturn("vv");
		List<String> list = new ArrayList<>();
		ObjectMapper objMapper = new ObjectMapper();
		list.add("{\"VESversion\":\"v5\",\"event\":{\"commonEventHeader\":{\"startEpochMicrosec\":0,\"sourceId\":\"shentao_test_vnf5\",\"eventId\":\"uui_test_vnf5\",\"nfcNamingCode\":\"\",\"reportingEntityId\":\"uui_test_vnf5\",\"internalHeaderFields\":{\"collectorTimeStamp\":\"Thu, 03 22 2018 02:30:09 GMT\"},\"eventType\":\"applicationVnf\",\"priority\":\"Normal\",\"version\":1.1,\"reportingEntityName\":\"ZTE-CMBJ-BJ,SubNetwork=100001,ManagedElement=100040_40,MmeFunction=1,EpRpDynS6aMme=2\",\"sequence\":498594,\"domain\":\"measurementsForVfScaling\",\"lastEpochMicrosec\":10363234321004,\"eventName\":\"Mfvs_MMEEpRpDynS6aMme\",\"sourceName\":\"1101ZTHX1E6M2QI4FZVTOXU\",\"nfNamingCode\":\"\"},\"measurementsForVfScalingFields\":{\"measurementInterval\":15,\"measurementsForVfScalingVersion\":2.1,\"additionalMeasurements\":[{\"name\":\"MME\",\"arrayOfFields\":[{\"name\":\"MM.UpdateLocationAnsFail\",\"value\":\"0.1\"},{\"name\":\"MM.UpdateLocationAnsFail.1xxx\",\"value\":\"0.2\"},{\"name\":\"MM.UpdateLocationAnsFail.3xxx\",\"value\":\"0.3\"}]}]}}}");
		Map<String, Object> eventMaps = (Map<String, Object>) objMapper.readValue(list.get(0), Map.class).get("event");
		dmaap.performanceProcess(eventMaps);
	}
}
