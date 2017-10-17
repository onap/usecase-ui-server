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
package org.onap.usecaseui.server.service.lcm.domain.aai.bean;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown=true)
public class VimInfo {

    private String cloudOwner;

    private String cloudRegionId;

    @JsonCreator
    public VimInfo(
            @JsonProperty("cloud-owner") String cloudOwner,
            @JsonProperty("cloud-region-id") String cloudRegionId) {
        this.cloudOwner = cloudOwner;
        this.cloudRegionId = cloudRegionId;
    }

    @JsonProperty("cloud-owner")
    public String getCloudOwner() {
        return cloudOwner;
    }

    @JsonProperty("cloud-region-id")
    public String getCloudRegionId() {
        return cloudRegionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VimInfo vimInfo = (VimInfo) o;
        return Objects.equals(cloudOwner, vimInfo.cloudOwner) &&
                Objects.equals(cloudRegionId, vimInfo.cloudRegionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cloudOwner, cloudRegionId);
    }
}
