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
public class AAIServiceSubscription {

    private String serviceType;
    
    private String resourceVersion;

    @JsonCreator
    public AAIServiceSubscription(@JsonProperty("service-type") String serviceType,@JsonProperty("resource-version") String resourceVersion) {
        this.serviceType = serviceType;
        this.resourceVersion = resourceVersion;
    }

    @JsonProperty("service-type")
    public String getServiceType() {
        return serviceType;
    }
    
    @JsonProperty("resource-version")
    public String getResourceVersion() {
        return resourceVersion;
    }
}
