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

public class AccessCost {
    @JsonProperty("type")
    private String type;
    @JsonProperty("accesscost")
    private String accesscost;


    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    @JsonProperty("accesscost")
    public String getAccesscost() {
        return accesscost;
    }

    @JsonProperty("accesscost")
    public void setAccesscost(String accesscost) {
        this.accesscost = accesscost;
    }

    @Override
    public String toString() {
        return "AccessCost{" +
                "type='" + type + '\'' +
                ", accesscost='" + accesscost + '\'' +
                '}';
    }
}
