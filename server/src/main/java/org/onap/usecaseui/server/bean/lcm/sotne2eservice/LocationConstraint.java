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

public class LocationConstraint {
    /**
     * vnf profile id
     */
    @JsonProperty("vnfProfileId")
    private String vnfProfileId;

    /**
     * location constraints: vimId
     */
    @JsonProperty("locationConstraints")
    private VimLocation locationConstraints;

    /**
     * @return Returns the vnfProfileId.
     */
    public String getVnfProfileId() {
        return vnfProfileId;
    }

    /**
     * @param vnfProfileId The vnfProfileId to set.
     */
    public void setVnfProfileId(String vnfProfileId) {
        this.vnfProfileId = vnfProfileId;
    }


    /**
     * @return Returns the locationConstraints.
     */
    public VimLocation getLocationConstraints() {
        return locationConstraints;
    }


    /**
     * @param locationConstraints The locationConstraints to set.
     */
    public void setLocationConstraints(VimLocation locationConstraints) {
        this.locationConstraints = locationConstraints;
    }
}
