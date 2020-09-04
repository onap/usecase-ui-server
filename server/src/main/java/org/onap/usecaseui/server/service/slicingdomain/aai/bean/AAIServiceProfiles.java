/**
 * Copyright 2016-2017 ZTE Corporation.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package org.onap.usecaseui.server.service.slicingdomain.aai.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class AAIServiceProfiles {

    @JsonProperty("profile-id")
    private String profileId;

    @JsonProperty("latency")
    private int latency;

    @JsonProperty("max-number-of-UEs")
    private int maxNumberOfUEs;

    @JsonProperty("coverage-area-TA-list")
    private String coverageAreaTAList;

    @JsonProperty("ue-mobility-level")
    private String ueMobilityLevel;

    @JsonProperty("resource-sharing-level")
    private String resourceSharingLevel;

    @JsonProperty("activity-factor")
    private int activityFactor;

    @JsonProperty("jitter")
    private int jitter;

    @JsonProperty("survival-time")
    private int survivalTime;

    @JsonProperty("reliability")
    private int reliability;

    @JsonProperty("availability")
    private float availability;

    @JsonProperty("dLThptPerSlice")
    private int dLThptPerSlice;

    @JsonProperty("dLThptPerUE")
    private int dLThptPerUE;

    @JsonProperty("uLThptPerSlice")
    private int uLThptPerSlice;

    @JsonProperty("uLThptPerUE")
    private int uLThptPerUE;

    @JsonProperty("maxPktSize")
    private int maxPktSize;

    @JsonProperty("maxNumberofConns")
    private int maxNumberofConns;

    @JsonProperty("termDensity")
    private int termDensity;

    @JsonProperty("resource-version")
    private String resourceVersion;


}
