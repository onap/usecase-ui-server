/**
 * Copyright 2016-2017 ZTE Corporation.
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
package org.onap.usecaseui.server.service.lcm.impl;

import org.junit.Assert;
import org.junit.Test;
import org.onap.usecaseui.server.service.lcm.ServiceInstanceService;
import org.onap.usecaseui.server.service.lcm.domain.aai.AAIClient;
import org.onap.usecaseui.server.service.lcm.domain.aai.bean.ServiceInstanceRsp;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.onap.usecaseui.server.util.CallStub.emptyBodyCall;
import static org.onap.usecaseui.server.util.CallStub.failedCall;

public class DefaultServiceInstanceServiceTest {

    @Test
    public void itCanRetrieveServiceInstanceFromAAI() {
        AAIClient aaiClient = mock(AAIClient.class);
        String customerId = "1";
        String serviceType = "service";
        List<String> instances = Collections.singletonList("{\"service-instance-id\":\"35e88f8e-473f-4d88-92f8-6739a42baa23\",\"service-instance-name\":\"SDWANVPNInfra1\",\"service-type\":\"E2E Service\",\"service-role\":\"E2E Service\",\"model-invariant-id\":\"88dcb2f0-085b-4548-8b93-0882e37d25d8\",\"model-version-id\":\"462f84e5-f0e5-44c5-ab95-38fb4bf77064\",\"resource-version\":\"1535687551051\",\"input-parameters\":\"{\\n    \\\"service\\\":{\\n        \\\"name\\\":\\\"SDWANVPNInfra\\\",\\n        \\\"description\\\":\\\"SDWANVPNInfra\\\",\\n        \\\"serviceInvariantUuid\\\":\\\"88dcb2f0-085b-4548-8b93-0882e37d25d8\\\",\\n        \\\"serviceUuid\\\":\\\"462f84e5-f0e5-44c5-ab95-38fb4bf77064\\\",\\n        \\\"globalSubscriberId\\\":\\\"Demonstration\\\",\\n        \\\"serviceType\\\":\\\"CCVPN\\\",\\n        \\\"parameters\\\":{\\n            \\\"locationConstraints\\\":[\\n\\n            ],\\n            \\\"resources\\\":[\\n                {\\n                    \\\"resourceName\\\":\\\"SDWANConnectivity 0\\\",\\n                    \\\"resourceInvariantUuid\\\":\\\"f99a9a23-c88e-44ff-a4dc-22b88675d278\\\",\\n                    \\\"resourceUuid\\\":\\\"7baa7742-3a13-4288-8330-868015adc340\\\",\\n                    \\\"resourceCustomizationUuid\\\":\\\"94ec574b-2306-4cbd-8214-09662b040f73\\\",\\n                    \\\"parameters\\\":{\\n                        \\\"locationConstraints\\\":[\\n\\n                        ],\\n                        \\\"resources\\\":[\\n\\n                        ],\\n                        \\\"requestInputs\\\":{\\n\\n                        }\\n                    }\\n                },\\n                {\\n                    \\\"resourceName\\\":\\\"SPPartnerVF 0\\\",\\n                    \\\"resourceInvariantUuid\\\":\\\"072f9238-15b0-4bc5-a5f5-f18548739470\\\",\\n                    \\\"resourceUuid\\\":\\\"81b9430b-8abe-45d6-8bf9-f41a8f5c735f\\\",\\n                    \\\"resourceCustomizationUuid\\\":\\\"a7baba5d-6ac3-42b5-b47d-070841303ab1\\\",\\n                    \\\"parameters\\\":{\\n                        \\\"locationConstraints\\\":[\\n\\n                        ],\\n                        \\\"resources\\\":[\\n\\n                        ],\\n                        \\\"requestInputs\\\":{\\n\\n                        }\\n                    }\\n                }\\n            ],\\n            \\\"requestInputs\\\":{\\n                \\\"sdwanconnectivity0_name\\\":\\\"CMCCVPN\\\",\\n                \\\"sdwanconnectivity0_topology\\\":\\\"hub-spoke\\\"\\n            }\\n        }\\n    }\\n}\",\"relationship-list\":{\"relationship\":[{\"related-to\":\"sdwan-vpn\",\"relationship-label\":\"org.onap.relationships.inventory.PartOf\",\"related-link\":\"/aai/v13/network/sdwan-vpns/sdwan-vpn/4efe6dff-acfc-4d13-a3fd-1177d3c08e89\",\"relationship-data\":[{\"relationship-key\":\"sdwan-vpn.sdwan-vpn-id\",\"relationship-value\":\"4efe6dff-acfc-4d13-a3fd-1177d3c08e89\"}],\"related-to-property\":[{\"property-key\":\"sdwan-vpn.sdwan-vpn-name\",\"property-value\":\"vdfvpn\"}]},{\"related-to\":\"sp-partner\",\"relationship-label\":\"org.onap.relationships.inventory.PartOf\",\"related-link\":\"/aai/v13/business/sp-partners/sp-partner/1b9c677d-fddf-4b70-938b-925a7fa57d00\",\"relationship-data\":[{\"relationship-key\":\"sp-partner.sp-partner-id\",\"relationship-value\":\"1b9c677d-fddf-4b70-938b-925a7fa57d00\"}],\"related-to-property\":[{\"property-key\":\"sp-partner.sp-partner-id\",\"property-value\":\"1b9c677d-fddf-4b70-938b-925a7fa57d00\"}]},{\"related-to\":\"allotted-resource\",\"relationship-label\":\"org.onap.relationships.inventory.Uses\",\"related-link\":\"/aai/v13/business/customers/customer/Democcy/service-subscriptions/service-subscription/CCVPN/service-instances/service-instance/189b87a5-72fe-4197-a307-6929c3831f81/allotted-resources/allotted-resource/2214feec-1aef-4890-abba-f8f3a906935f\",\"relationship-data\":[{\"relationship-key\":\"customer.global-customer-id\",\"relationship-value\":\"Democcy\"},{\"relationship-key\":\"service-subscription.service-type\",\"relationship-value\":\"CCVPN\"},{\"relationship-key\":\"service-instance.service-instance-id\",\"relationship-value\":\"189b87a5-72fe-4197-a307-6929c3831f81\"},{\"relationship-key\":\"allotted-resource.id\",\"relationship-value\":\"2214feec-1aef-4890-abba-f8f3a906935f\"}],\"related-to-property\":[{\"property-key\":\"allotted-resource.description\",\"property-value\":\"2214feec-1aef-4890-abba-f8f3a906935f\"},{\"property-key\":\"allotted-resource.allotted-resource-name\",\"property-value\":\"sdwan ar\"}]},{\"related-to\":\"allotted-resource\",\"relationship-label\":\"org.onap.relationships.inventory.Uses\",\"related-link\":\"/aai/v13/business/customers/customer/Democcy/service-subscriptions/service-subscription/CCVPN/service-instances/service-instance/089b87a5-72fe-4197-a307-6929c3831f80/allotted-resources/allotted-resource/1114feec-1aef-4890-abba-f8f3a906935f\",\"relationship-data\":[{\"relationship-key\":\"customer.global-customer-id\",\"relationship-value\":\"Democcy\"},{\"relationship-key\":\"service-subscription.service-type\",\"relationship-value\":\"CCVPN\"},{\"relationship-key\":\"service-instance.service-instance-id\",\"relationship-value\":\"089b87a5-72fe-4197-a307-6929c3831f80\"},{\"relationship-key\":\"allotted-resource.id\",\"relationship-value\":\"1114feec-1aef-4890-abba-f8f3a906935f\"}],\"related-to-property\":[{\"property-key\":\"allotted-resource.description\",\"property-value\":\"1114feec-1aef-4890-abba-f8f3a906935f\"},{\"property-key\":\"allotted-resource.allotted-resource-name\",\"property-value\":\"sdwan ar\"}]}]}}");
        ServiceInstanceRsp rsp = new ServiceInstanceRsp();
        rsp.setServiceInstances(instances);
        when(aaiClient.listServiceInstances(customerId, serviceType)).thenReturn(emptyBodyCall());

        ServiceInstanceService service = new DefaultServiceInstanceService(aaiClient);

        Assert.assertTrue(service.listServiceInstances(customerId, serviceType).isEmpty());
    }

    @Test
    public void retrieveServiceInstancesWillThrowExceptionWhenAAIIsNotAvailable() {
        AAIClient aaiClient = mock(AAIClient.class);
        String customerId = "1";
        String serviceType = "service";
        when(aaiClient.listServiceInstances(customerId, serviceType)).thenReturn(failedCall("AAI is not available!"));

        ServiceInstanceService service = new DefaultServiceInstanceService(aaiClient);
        service.listServiceInstances(customerId, serviceType);
    }

    @Test
    public void retrieveServiceInstancesWillThrowExceptionWhenNoServiceInstancesInAAI() {
        AAIClient aaiClient = mock(AAIClient.class);
        String customerId = "1";
        String serviceType = "service";
        when(aaiClient.listServiceInstances(customerId, serviceType)).thenReturn(emptyBodyCall());

        ServiceInstanceService service = new DefaultServiceInstanceService(aaiClient);
        List<String> serviceInstances = service.listServiceInstances(customerId, serviceType);

        Assert.assertTrue("service instances should be empty.", serviceInstances.isEmpty());
    }

    @Test
    public void getRelationShipDataWillThrowExceptionWhenAAIIsNotAvailable() {
        AAIClient aaiClient = mock(AAIClient.class);
        String customerId = "1";
        String serviceType = "service";
        String result="result";
        when(aaiClient.getAAIServiceInstance(customerId, serviceType,result)).thenReturn(failedCall("AAI is not available!"));

        ServiceInstanceService service = new DefaultServiceInstanceService(aaiClient);
        service.getRelationShipData(customerId, serviceType,result);
    }

    @Test
    public void getRelationShipDataWillThrowExceptionWhenNoServiceInstancesInAAI() {
        AAIClient aaiClient = mock(AAIClient.class);
        String customerId = "1";
        String serviceType = "service";
        String result="result";
        when(aaiClient.getAAIServiceInstance(customerId, serviceType,result)).thenReturn(emptyBodyCall());

        ServiceInstanceService service = new DefaultServiceInstanceService(aaiClient);
        String aa = service.getRelationShipData(customerId, serviceType,result);

        Assert.assertTrue("service instances should be empty.", aa.isEmpty());
    }
}
