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
package org.onap.usecaseui.server.service.lcm.domain.aai.bean;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class AAICustomer {

    private String globalCustomerId;

    private String subscriberName;

    private String subscriberType;

    @JsonCreator
    public AAICustomer(
            @JsonProperty("global-customer-id") String globalCustomerId,
            @JsonProperty("subscriber-name") String subscriberName,
            @JsonProperty("subscriber-type") String subscriberType) {
        this.globalCustomerId = globalCustomerId;
        this.subscriberName = subscriberName;
        this.subscriberType = subscriberType;
    }

    @JsonProperty("global-customer-id")
    public String getGlobalCustomerId() {
        return globalCustomerId;
    }

    @JsonProperty("subscriber-name")
    public String getSubscriberName() {
        return subscriberName;
    }

    @JsonProperty("subscriber-type")
    public String getSubscriberType() {
        return subscriberType;
    }
}
