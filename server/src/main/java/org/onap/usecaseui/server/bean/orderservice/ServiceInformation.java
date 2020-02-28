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
package org.onap.usecaseui.server.bean.orderservice;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.onap.usecaseui.server.service.lcm.domain.sdc.bean.SDCServiceTemplate;

import java.util.List;

public class ServiceInformation {
    @JsonProperty("serviceInfo")
    private SDCServiceTemplate serviceInfo;

    @JsonProperty("serviceInfo")
    public SDCServiceTemplate getServiceInfo() {
        return serviceInfo;
    }

    @JsonProperty("serviceInfo")
    public void setServiceInfo(SDCServiceTemplate serviceInfo) {
        this.serviceInfo = serviceInfo;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("serviceInfo", serviceInfo).toString();
    }

}
