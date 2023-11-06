/**
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
package org.onap.usecaseui.server.service.lcm.domain.aai.bean;

import org.junit.Test;


import static org.junit.Assert.assertEquals;

public class ServiceInstanceTest {

	@Test
	public void testServiceInstance() throws Exception {
		ServiceInstance si = new ServiceInstance("globalCustomerId", "serviceType", "serviceInstanceId", "subscriberName",
												"subscriberType", "serviceInstanceName", "serviceInstanceLocationId");
		si.setServiceDomain("serviceDomain");
		assertEquals("globalCustomerId",si.getGlobalCustomerId());
		assertEquals("serviceType",si.getServiceType());
		assertEquals("serviceInstanceId",si.getServiceInstanceId());
		assertEquals("subscriberName",si.getSubscriberName());
		assertEquals("subscriberType",si.getSubscriberType());
		assertEquals("serviceInstanceName",si.getServiceInstanceName());
		assertEquals("serviceInstanceLocationId",si.getServiceInstanceLocationId());
		assertEquals("serviceDomain",si.getServiceDomain());
	}
}

