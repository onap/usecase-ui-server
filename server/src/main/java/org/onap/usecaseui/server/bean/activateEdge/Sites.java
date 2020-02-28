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
package org.onap.usecaseui.server.bean.activateEdge;

import com.fasterxml.jackson.annotation.*;

import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "siteId",
        "sotnSiteName",
        "description",
        "zipCode",
        "address",
        "vlan"
})
public class Sites {

    public class Site {

        @JsonProperty("siteId")
        private String siteId;
        @JsonProperty("sotnSiteName")
        private String sotnSiteName;
        @JsonProperty("description")
        private String description;
        @JsonProperty("zipCode")
        private String zipCode;
        @JsonProperty("address")
        private String address;
        @JsonProperty("vlan")
        private String vlan;

        @JsonProperty("siteId")
        public String getSiteId() {
            return siteId;
        }

        @JsonProperty("siteId")
        public void setSiteId(String siteId) {
            this.siteId = siteId;
        }

        @JsonProperty("sotnSiteName")
        public String getSotnSiteName() {
            return sotnSiteName;
        }

        @JsonProperty("sotnSiteName")
        public void setSotnSiteName(String sotnSiteName) {
            this.sotnSiteName = sotnSiteName;
        }

        @JsonProperty("description")
        public String getDescription() {
            return description;
        }

        @JsonProperty("description")
        public void setDescription(String description) {
            this.description = description;
        }

        @JsonProperty("zipCode")
        public String getZipCode() {
            return zipCode;
        }

        @JsonProperty("zipCode")
        public void setZipCode(String zipCode) {
            this.zipCode = zipCode;
        }

        @JsonProperty("address")
        public String getAddress() {
            return address;
        }

        @JsonProperty("address")
        public void setAddress(String address) {
            this.address = address;
        }

        @JsonProperty("vlan")
        public String getVlan() {
            return vlan;
        }

        @JsonProperty("vlan")
        public void setVlan(String vlan) {
            this.vlan = vlan;
        }
    }
}
