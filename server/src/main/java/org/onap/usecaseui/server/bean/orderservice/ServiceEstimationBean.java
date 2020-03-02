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



import java.util.List;

public class ServiceEstimationBean {

    private String bandWidth;
    private List<String> wlanAccess;
    private String serviceCost;
    private String bandwidthCost;
    private String cameraCost;
    private String vpnCost;
    private List<VpnInformation> vpnInformations;

    public String getBandWidth() {
        return bandWidth;
    }

    public void setBandWidth(String bandWidth) {
        this.bandWidth = bandWidth;
    }

    public List<String> getWlanAccess() {
        return wlanAccess;
    }

    public void setWlanAccess(List<String> wlanAccess) {
        this.wlanAccess = wlanAccess;
    }

    public String getServiceCost() {
        return serviceCost;
    }

    public void setServiceCost(String serviceCost) {
        this.serviceCost = serviceCost;
    }

    public String getCameraCost() {
        return cameraCost;
    }

    public void setCameraCost(String cameraCost) {
        this.cameraCost = cameraCost;
    }

    public String getVpnCost() {
        return vpnCost;
    }

    public void setVpnCost(String vpnCost) {
        this.vpnCost = vpnCost;
    }

    public List<VpnInformation> getVpnInformations() {
        return vpnInformations;
    }

    public void setVpnInformations(List<VpnInformation> vpnInformations) {
        this.vpnInformations = vpnInformations;
    }

    public String getBandwidthCost() {
        return bandwidthCost;
    }

    public void setBandwidthCost(String bandwidthCost) {
        this.bandwidthCost = bandwidthCost;
    }

    @Override
    public String toString() {
        return "ServiceEstimationBean{" +
                "bandWidth='" + bandWidth + '\'' +
                ", wlanAccess=" + wlanAccess +
                ", serviceCost='" + serviceCost + '\'' +
                ", cameraCost='" + cameraCost + '\'' +
                ", vpnCost='" + vpnCost + '\'' +
                ", vpnInformations=" + vpnInformations +
                '}';
    }
}

    /*

    public ServiceEstimationBean ServiceEstimationBean(ServiceEstimationBuilder serviceEstimationBuilder)
    {
        this.bandwidth=serviceEstimationBuilder.bandwidth;
        this.wlanAccess=serviceEstimationBuilder.wlanAccess;
        this.serviceCost=serviceEstimationBuilder.serviceCost;
        this.cameraCost=serviceEstimationBuilder.cameraCost;
        this.vpnCostInfoList=serviceEstimationBuilder.vpnCostInfoList;
        return this;
    }


    // Builder for service estimation bean

    public static class ServiceEstimationBuilder
    {
        private String bandwidth;
        private List<String> wlanAccess;
        private String serviceCost;
        private String cameraCost;
        private List<VpnCostInfo> vpnCostInfoList;

        public String getBandwidth() {
            return bandwidth;
        }

        public void setBandwidth(String bandwidth) {
            this.bandwidth = bandwidth;
        }

        public List<String> getWlanAccess() {
            return wlanAccess;
        }

        public void setWlanAccess(List<String> wlanAccess) {
            this.wlanAccess = wlanAccess;
        }

        public String getServiceCost() {
            return serviceCost;
        }

        public void setServiceCost(String serviceCost) {
            this.serviceCost = serviceCost;
        }

        public String getCameraCost() {
            return cameraCost;
        }

        public void setCameraCost(String cameraCost) {
            this.cameraCost = cameraCost;
        }

        public List<VpnCostInfo> getVpnCostInfoList() {
            return vpnCostInfoList;
        }

        public void setVpnCostInfoList(List<VpnCostInfo> vpnCostInfoList) {
            this.vpnCostInfoList = vpnCostInfoList;
        }

        public ServiceEstimationBean Build()
        {
            return new ServiceEstimationBean(this);
        }
    }
    */


