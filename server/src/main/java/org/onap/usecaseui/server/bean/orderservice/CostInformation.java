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

import java.util.List;

public class CostInformation {
    @JsonProperty("bandwidthCost")
    private List<BandwidthCost> bandwidthCost = null;
    @JsonProperty("accessCost")
    private List<AccessCost> accessCost = null;
    @JsonProperty("siteCost")
    private List<SiteCost> siteCost = null;
    @JsonProperty("vpnCamerCost")
    private List<VpnCamerCost> vpnCamerCost = null;


    @JsonProperty("bandwidthCost")
    public List<BandwidthCost> getBandwidthCost() {
        return bandwidthCost;
    }

    @JsonProperty("bandwidthCost")
    public void setBandwidthCost(List<BandwidthCost> bandwidthCost) {
        this.bandwidthCost = bandwidthCost;
    }

    @JsonProperty("accessCost")
    public List<AccessCost> getAccessCost() {
        return accessCost;
    }

    @JsonProperty("accessCost")
    public void setAccessCost(List<AccessCost> accessCost) {
        this.accessCost = accessCost;
    }

    @JsonProperty("siteCost")
    public List<SiteCost> getSiteCost() {
        return siteCost;
    }

    @JsonProperty("siteCost")
    public void setSiteCost(List<SiteCost> siteCost) {
        this.siteCost = siteCost;
    }

    @JsonProperty("vpnCamerCost")
    public List<VpnCamerCost> getVpnCamerCost() {
        return vpnCamerCost;
    }

    @JsonProperty("vpnCamerCost")
    public void setVpnCamerCost(List<VpnCamerCost> vpnCamerCost) {
        this.vpnCamerCost = vpnCamerCost;
    }

    @Override
    public String toString() {
        return "CostInformation{" +
                "bandwidthCost=" + bandwidthCost +
                ", accessCost=" + accessCost +
                ", siteCost=" + siteCost +
                ", vpnCamerCost=" + vpnCamerCost +
                '}';
    }
}
