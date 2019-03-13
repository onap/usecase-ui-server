package org.onap.usecaseui.server.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DmaapSubscriberTest {
	
	DmaapSubscriber dmaap = new DmaapSubscriber();
	private String topic="events/unauthenticated.SEC_FAULT_OUTPUT";
	
/*	@Test
	public void subscribeTest(){
		dmaap.subscribe(topic);
	}
	
	@Test
	public void getDMaaPDataTest(){
		dmaap.getDMaaPData(topic);
	}*/
	
	@Test
	public void initConfigTest(){
		dmaap.initConfig();
	}
	
	@Test
	public void alarmProcessTest() throws JsonParseException, JsonMappingException, IOException{
		List<String> list = new ArrayList<>();
		ObjectMapper objMapper = new ObjectMapper();
		list.add("{\"event\":{\"commonEventHeader\":{\"sourceId\":\"vnf_test_3\",\"startEpochMicrosec\":1516784364869,\"eventId\":\"ab305d54-85b4-a31b-7db2-fb6b9e546015\",\"sequence\":0,\"domain\":\"fault\",\"lastEpochMicrosec\":1516784364869,\"eventName\":\"Fault_MultiCloud_VMFailureCleared\",\"sourceName\":\"vSBC\",\"priority\":\"High\",\"version\":3.0,\"reportingEntityName\":\"vnf_test_3_rname\"},\"faultFields\":{\"eventSeverity\":\"CRITICAL\",\"alarmCondition\":\"Guest_Os_Failure\",\"faultFieldsVersion\":2.0,\"specificProblem\":\"Fault_MultiCloud_VMFailure\",\"alarmInterfaceA\":\"aaaa\",\"alarmAdditionalInformation\":[{\"name\":\"objectType\",\"value\":\"VIM\"},{\"name\":\"eventTime\",\"value\":\"2017-10-31 09:51:15\"}],\"eventSourceType\":\"other\",\"vfStatus\":\"Active\"}}}");
		Map<String, Object> eventMaps = (Map<String, Object>) objMapper.readValue(list.get(0), Map.class).get("event");
		dmaap.alarmProcess(eventMaps);
		
	}
	
	@Test
	public void performanceProcessTest() throws JsonParseException, JsonMappingException, IOException{
		List<String> list = new ArrayList<>();
		ObjectMapper objMapper = new ObjectMapper();
		list.add("{\"VESversion\":\"v5\",\"event\":{\"commonEventHeader\":{\"startEpochMicrosec\":0,\"sourceId\":\"shentao_test_vnf5\",\"eventId\":\"uui_test_vnf5\",\"nfcNamingCode\":\"\",\"reportingEntityId\":\"uui_test_vnf5\",\"internalHeaderFields\":{\"collectorTimeStamp\":\"Thu, 03 22 2018 02:30:09 GMT\"},\"eventType\":\"applicationVnf\",\"priority\":\"Normal\",\"version\":1.1,\"reportingEntityName\":\"ZTE-CMBJ-BJ,SubNetwork=100001,ManagedElement=100040_40,MmeFunction=1,EpRpDynS6aMme=2\",\"sequence\":498594,\"domain\":\"measurementsForVfScaling\",\"lastEpochMicrosec\":10363234321004,\"eventName\":\"Mfvs_MMEEpRpDynS6aMme\",\"sourceName\":\"1101ZTHX1E6M2QI4FZVTOXU\",\"nfNamingCode\":\"\"},\"measurementsForVfScalingFields\":{\"measurementInterval\":15,\"measurementsForVfScalingVersion\":2.1,\"additionalMeasurements\":[{\"name\":\"MME\",\"arrayOfFields\":[{\"name\":\"MM.UpdateLocationAnsFail\",\"value\":\"0.1\"},{\"name\":\"MM.UpdateLocationAnsFail.1xxx\",\"value\":\"0.2\"},{\"name\":\"MM.UpdateLocationAnsFail.3xxx\",\"value\":\"0.3\"}]}]}}}");
		Map<String, Object> eventMaps = (Map<String, Object>) objMapper.readValue(list.get(0), Map.class).get("event");
		dmaap.performanceProcess(eventMaps);
	}
}
