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
package org.onap.usecaseui.server.bean.lcm.sotne2eservice;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CombinedUUIService {
    public String getL2vpn_COS() {
        return l2vpn_COS;
    }

    public void setL2vpn_COS(String l2vpn_COS) {
        this.l2vpn_COS = l2vpn_COS;
    }

    public String getL2vpn_dualLink() {
        return l2vpn_dualLink;
    }

    public void setL2vpn_dualLink(String l2vpn_dualLink) {
        this.l2vpn_dualLink = l2vpn_dualLink;
    }

    public String getL2vpn_description() {
        return l2vpn_description;
    }

    public void setL2vpn_description(String l2vpn_description) {
        this.l2vpn_description = l2vpn_description;
    }

    public String getL2vpn_name() {
        return l2vpn_name;
    }

    public void setL2vpn_name(String l2vpn_name) {
        this.l2vpn_name = l2vpn_name;
    }

    public String getL2vpn_tenantid() {
        return l2vpn_tenantid;
    }

    public void setL2vpn_tenantid(String l2vpn_tenantid) {
        this.l2vpn_tenantid = l2vpn_tenantid;
    }

    public String getL2vpn_vpnType() {
        return l2vpn_vpnType;
    }

    public void setL2vpn_vpnType(String l2vpn_vpnType) {
        this.l2vpn_vpnType = l2vpn_vpnType;
    }

    public String getL2vpn_cbs() {
        return l2vpn_cbs;
    }

    public void setL2vpn_cbs(String l2vpn_cbs) {
        this.l2vpn_cbs = l2vpn_cbs;
    }

    public String getL2vpn_ebs() {
        return l2vpn_ebs;
    }

    public void setL2vpn_ebs(String l2vpn_ebs) {
        this.l2vpn_ebs = l2vpn_ebs;
    }

    public String getL2vpn_colorAware() {
        return l2vpn_colorAware;
    }

    public void setL2vpn_colorAware(String l2vpn_colorAware) {
        this.l2vpn_colorAware = l2vpn_colorAware;
    }

    public String getL2vpn_reroute() {
        return l2vpn_reroute;
    }

    public void setL2vpn_reroute(String l2vpn_reroute) {
        this.l2vpn_reroute = l2vpn_reroute;
    }

    public String getL2vpn_couplingFlag() {
        return l2vpn_couplingFlag;
    }

    public void setL2vpn_couplingFlag(String l2vpn_couplingFlag) {
        this.l2vpn_couplingFlag = l2vpn_couplingFlag;
    }

    public String getL2vpn_cir() {
        return l2vpn_cir;
    }

    public void setL2vpn_cir(String l2vpn_cir) {
        this.l2vpn_cir = l2vpn_cir;
    }

    public String getL2vpn_startTime() {
        return l2vpn_startTime;
    }

    public void setL2vpn_startTime(String l2vpn_startTime) {
        this.l2vpn_startTime = l2vpn_startTime;
    }

    public String getL2vpn_endTime() {
        return l2vpn_endTime;
    }

    public void setL2vpn_endTime(String l2vpn_endTime) {
        this.l2vpn_endTime = l2vpn_endTime;
    }

    public String getL2vpn_eir() {
        return l2vpn_eir;
    }

    public void setL2vpn_eir(String l2vpn_eir) {
        this.l2vpn_eir = l2vpn_eir;
    }

    public String getL2vpn_SLS() {
        return l2vpn_SLS;
    }

    public void setL2vpn_SLS(String l2vpn_SLS) {
        this.l2vpn_SLS = l2vpn_SLS;
    }

    @JsonProperty("l2vpn_COS")
    String l2vpn_COS;
    @JsonProperty("l2vpn_dualLink")
    String l2vpn_dualLink;
    @JsonProperty("l2vpn_description")
    String l2vpn_description;
     @JsonProperty("l2vpn_name")
    String l2vpn_name;
    @JsonProperty("l2vpn_tenantid")
    String l2vpn_tenantid;
    @JsonProperty("l2vpn_vpnType")
    String l2vpn_vpnType;
    @JsonProperty("l2vpn_cbs")
    String l2vpn_cbs;
    @JsonProperty("l2vpn_ebs")
    String l2vpn_ebs;
    @JsonProperty("l2vpn_colorAware")
    String l2vpn_colorAware;
    @JsonProperty("l2vpn_reroute")
    String l2vpn_reroute;                 // Bandwidth in KBPS, min value 1 MB
    @JsonProperty("l2vpn_couplingFlag")
    String l2vpn_couplingFlag;
    @JsonProperty("l2vpn_cir")
    String l2vpn_cir;
    @JsonProperty("l2vpn_startTime")
    String l2vpn_startTime;
    @JsonProperty("l2vpn_endTime")
    String l2vpn_endTime;
    @JsonProperty("l2vpn_eir")
    String l2vpn_eir;                 //refer gmail
    @JsonProperty("l2vpn_SLS")
    String l2vpn_SLS;
//    @JsonProperty("couplingFlag")
//    String couplingFlag;
//    @JsonProperty("serviceClassificationType")
//    String serviceClassificationType;
//    @JsonProperty("svLan")
//    String svLan;
//    @JsonProperty("highAvailability")
//    String highAvailability;
//    @JsonProperty("SLAType")
//    String sLAType;


//    @Override
//    public String toString() {
//        return "{" +
//                "\"name\":\"" + name + '\"' +
//                ", \"description\":\"" + description + '\"' +
//                ", \"startTime\":\"" + startTime + '\"' +
//                ", \"endTime\":\"" + endTime + '\"' +
//                ", \"cos\":\"" + cos + '\"' +
//                ", \"reRoute\":\"" + reRoute + '\"' +
//                ", \"specification\":\"" + specification + '\"' +
//                ", \"dualLink\":\"" + dualLink + '\"' +
//                ", \"cir\":\"" + cir + '\"' +
//                ", \"eir\":\"" + eir + '\"' +
//                ", \"cbs\":\"" + cbs + '\"' +
//                ", \"ebs\":\"" + ebs + '\"' +
//                ", \"colorAware\":\"" + colorAware + '\"' +
//                ", \"couplingFlag\":\"" + couplingFlag + '\"' +
//                ", \"highAvailability\":\"" + highAvailability + '\"' +
//                ", \"SLAType\":\"" + sLAType + '\"' +
//                ", \"serviceClassificationType\":\"" + serviceClassificationType + '\"' +
////                ", \"siteCost\":\"" + siteCost + '\"' +
////                ", \"serviceCost\":\"" + serviceCost + '\"' +
//                '}';
//    }


}
