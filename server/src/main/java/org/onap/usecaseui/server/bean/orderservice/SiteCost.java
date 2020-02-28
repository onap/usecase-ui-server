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

public class SiteCost {
    @JsonProperty("siteType")
    private String siteType;
    @JsonProperty("siteCost")
    private String siteCost;


    @JsonProperty("siteType")
    public String getSiteType() {
        return siteType;
    }

    @JsonProperty("siteType")
    public void setSiteType(String siteType) {
        this.siteType = siteType;
    }

    @JsonProperty("siteCost")
    public String getSiteCost() {
        return siteCost;
    }

    @JsonProperty("siteCost")
    public void setSiteCost(String siteCost) {
        this.siteCost = siteCost;
    }

    @Override
    public String toString() {
        return "SiteCost{" +
                "siteType='" + siteType + '\'' +
                ", siteCost='" + siteCost + '\'' +
                '}';
    }
}
