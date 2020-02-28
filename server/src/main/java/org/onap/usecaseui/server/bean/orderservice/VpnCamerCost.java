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

public class VpnCamerCost {

    @JsonProperty("cameraCost")
    private String cameraCost;
    @JsonProperty("vpnCost")
    private String vpnCost;


    @JsonProperty("cameraCost")
    public String getCameraCost() {
        return cameraCost;
    }

    @JsonProperty("cameraCost")
    public void setCameraCost(String cameraCost) {
        this.cameraCost = cameraCost;
    }

    @JsonProperty("vpnCost")
    public String getVpnCost() {
        return vpnCost;
    }

    @JsonProperty("vpnCost")
    public void setVpnCost(String vpnCost) {
        this.vpnCost = vpnCost;
    }

    @Override
    public String toString() {
        return "VpnCamerCost{" +
                "cameraCost='" + cameraCost + '\'' +
                ", vpnCost='" + vpnCost + '\'' +
                '}';
    }
}
