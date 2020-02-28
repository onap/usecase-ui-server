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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties
public class SotnSites {
    String siteId;
    String sotnSiteName;
    String description;
    String zipCode;
    String address;
    String vlan;

    public String getSotnSiteName() {
        return sotnSiteName;
    }

    public void setSotnSiteName(String sotnSiteName) {
        this.sotnSiteName = sotnSiteName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getVlan() {
        return vlan;
    }

    public void setVlan(String vlan) {
        this.vlan = vlan;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }



    @Override
    public String toString() {
        return "{" +
                "\"name\":\"" + sotnSiteName + "\"" +
                ", \"description\":\"" + description + "\"" +
                ", \"postcode\":\"" + zipCode + '\"' +
                ", \"address\":\"" + address + '\"' +
                ", \"cVLAN\":\"" + vlan + '\"' +
                '}';
    }
}
