/*
 * Copyright (C) 2022 Wipro Limited. All rights reserved.
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
package org.onap.usecaseui.server.bean.nsmf.monitor;

import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;
public class ServicePDUSessionEstSRInfoTest {

	 @Before
	    public void before() throws Exception {
	    }

	    @After
	    public void after() throws Exception {
	    }

	    @Test
	    public void testSetAndGetServicePDUSessionEstSRInfo() throws Exception {

	    	ServicePDUSessionEstSRInfo servicePDUSessionEstSRInfo = new ServicePDUSessionEstSRInfo();

	    	List<PDUSessionEstSRInfo> pDUSessionEstSRInfoList = new ArrayList<>();
	        
	        PDUSessionEstSRInfo pDUSessionEstSRInfo = new PDUSessionEstSRInfo();
	        pDUSessionEstSRInfo.setPduSessionEstSR("188");
	        pDUSessionEstSRInfo.setTimestamp("1576143554000");
	        pDUSessionEstSRInfoList.add(pDUSessionEstSRInfo);
	        
	        servicePDUSessionEstSRInfo.setId("01000001");
	        servicePDUSessionEstSRInfo.setPDUSessionEstSRInfoList(pDUSessionEstSRInfoList);
	        
	        servicePDUSessionEstSRInfo.getId();
	        servicePDUSessionEstSRInfo.getPDUSessionEstSRInfoList();
	    }

}
