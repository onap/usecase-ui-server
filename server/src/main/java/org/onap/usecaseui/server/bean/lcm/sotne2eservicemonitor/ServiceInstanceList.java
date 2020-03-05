/**
 * Copyright 2020 Huawei Corporation.
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
package org.onap.usecaseui.server.bean.lcm.sotne2eservicemonitor;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "service-instance-id"
})
public class ServiceInstanceList {

    @JsonProperty("service-instance-id")
    String serviceInstance;
    @JsonProperty("service-instance-name")

    String serviceInstancename;
    public String getServiceInstance() {
        return serviceInstance;
    }

    public void setServiceInstance(String serviceInstance) {
        this.serviceInstance = serviceInstance;
    }

    public String getServiceInstancename() {
        return serviceInstancename;
    }

    public void setServiceInstancename(String serviceInstancename) {
        this.serviceInstancename = serviceInstancename;
    }

    @Override
    public String toString() {
        return "{" +
                "\"serviceInstance\":\"" + serviceInstance + '\"' +
                ",\"serviceInstancename\":\"" + serviceInstancename + '\"' +
                '}';
    }
}
