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
package org.onap.usecaseui.server.bean.lcm.sotne2eservice;


import com.fasterxml.jackson.annotation.JsonProperty;

public class SotnService {

    @JsonProperty("serviceName")
    String name;
    @JsonProperty("description")
    String description;
    @JsonProperty("startTime")
    String startTime;
    @JsonProperty("endTime")
    String endTime;
    @JsonProperty("cos")
    String cos;
    @JsonProperty("reRoute")
    String reRoute;
    @JsonProperty("specification")
    String specification;
    @JsonProperty("dualLink")
    String dualLink;
    @JsonProperty("cir")
    String cir;                 // Bandwidth in KBPS, min value 1 MB
    @JsonProperty("eir")
    String eir;
    @JsonProperty("cbs")
    String cbs;
    @JsonProperty("ebs")
    String ebs;                 //refer gmail
    @JsonProperty("colorAware")
    String colorAware;
    @JsonProperty("couplingFlag")
    String couplingFlag;
    @JsonProperty("serviceClassificationType")
    String serviceClassificationType;
    @JsonProperty("svLan")
    String svLan;
    @JsonProperty("highAvailability")
    String highAvailability;
    @JsonProperty("SLAType")
    String sLAType;
//    @JsonProperty("serviceCost")
//    String serviceCost;
//    @JsonProperty("vpnCost")
//    String vpnCost;
//    @JsonProperty("siteCost")
//    String siteCost;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getCos() {
        return cos;
    }

    public void setCos(String cos) {
        this.cos = cos;
    }

    public String getReRoute() {
        return reRoute;
    }

    public void setReRoute(String reRoute) {
        this.reRoute = reRoute;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getDualLink() {
        return dualLink;
    }

    public void setDualLink(String dualLink) {
        this.dualLink = dualLink;
    }

    public String getCir() {
        return cir;
    }

    public void setCir(String cir) {
        this.cir = cir;
    }

    public String getEir() {
        return eir;
    }

    public void setEir(String eir) {
        this.eir = eir;
    }

    public String getCbs() {
        return cbs;
    }

    public void setCbs(String cbs) {
        this.cbs = cbs;
    }

    public String getEbs() {
        return ebs;
    }

    public void setEbs(String ebs) {
        this.ebs = ebs;
    }

    public String getColorAware() {
        return colorAware;
    }

    public void setColorAware(String colorAware) {
        this.colorAware = colorAware;
    }

    public String getCouplingFlag() {
        return couplingFlag;
    }

    public void setCouplingFlag(String couplingFlag) {
        this.couplingFlag = couplingFlag;
    }

    public String getServiceClassificationType() {
        return serviceClassificationType;
    }

    public void setServiceClassificationType(String serviceClassificationType) {
        this.serviceClassificationType = serviceClassificationType;
    }

    public String getSvLan() {
        return svLan;
    }

    public void setSvLan(String svLan) {
        this.svLan = svLan;
    }


    public String getHighAvailability() {
        return highAvailability;
    }

    public void setHighAvailability(String highAvailability) {
        this.highAvailability = highAvailability;
    }

    public String getsLAType() {
        return sLAType;
    }

    public void setsLAType(String sLAType) {
        this.sLAType = sLAType;
    }

    //    public String getServiceCost() {
//        return serviceCost;
//    }
//
//    public void setServiceCost(String serviceCost) {
//        this.serviceCost = serviceCost;
//    }
//
//    public String getVpnCost() {
//        return vpnCost;
//    }
//
//    public void setVpnCost(String vpnCost) {
//        this.vpnCost = vpnCost;
//    }
//
//    public String getSiteCost() {
//        return siteCost;
//    }
//
//    public void setSiteCost(String siteCost) {
//        this.siteCost = siteCost;
//    }

    @Override
    public String toString() {
        return "{" +
                "\"name\":\"" + name + '\"' +
                ", \"description\":\"" + description + '\"' +
                ", \"startTime\":\"" + startTime + '\"' +
                ", \"endTime\":\"" + endTime + '\"' +
                ", \"cos\":\"" + cos + '\"' +
                ", \"reRoute\":\"" + reRoute + '\"' +
                ", \"specification\":\"" + specification + '\"' +
                ", \"dualLink\":\"" + dualLink + '\"' +
                ", \"cir\":\"" + cir + '\"' +
                ", \"eir\":\"" + eir + '\"' +
                ", \"cbs\":\"" + cbs + '\"' +
                ", \"ebs\":\"" + ebs + '\"' +
                ", \"colorAware\":\"" + colorAware + '\"' +
                ", \"couplingFlag\":\"" + couplingFlag + '\"' +
                ", \"highAvailability\":\"" + highAvailability + '\"' +
                ", \"SLAType\":\"" + sLAType + '\"' +
                ", \"serviceClassificationType\":\"" + serviceClassificationType + '\"' +
//                ", \"siteCost\":\"" + siteCost + '\"' +
//                ", \"serviceCost\":\"" + serviceCost + '\"' +
                '}';
    }
}
