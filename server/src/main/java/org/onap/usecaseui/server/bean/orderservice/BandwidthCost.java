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

public class BandwidthCost {
    @JsonProperty("bandWidth")
    private String bandWidth;
    @JsonProperty("bandwidthcost")
    private String bandwidthcost;


    @JsonProperty("bandWidth")
    public String getBandWidth() {
        return bandWidth;
    }

    @JsonProperty("bandWidth")
    public void setBandWidth(String bandWidth) {
        this.bandWidth = bandWidth;
    }

    @JsonProperty("bandwidthcost")
    public String getBandwidthcost() {
        return bandwidthcost;
    }

    @JsonProperty("bandwidthcost")
    public void setBandwidthcost(String bandwidthcost) {
        this.bandwidthcost = bandwidthcost;
    }

    @Override
    public String toString() {
        return "BandwidthCost{" +
                "bandWidth='" + bandWidth + '\'' +
                ", bandwidthcost='" + bandwidthcost + '\'' +
                '}';
    }
}