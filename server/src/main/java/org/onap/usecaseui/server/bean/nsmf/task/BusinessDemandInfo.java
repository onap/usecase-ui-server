/*
 * Copyright (C) 2019 CMCC, Inc. and others. All rights reserved.
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
package org.onap.usecaseui.server.bean.nsmf.task;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusinessDemandInfo {

    @JsonProperty("service_name")
    String serviceName;

    @JsonProperty("service_snssai")
    String serviceSnssai;

    @JsonProperty("exp_data_rate_dl")
    String expDataRateDL;

    @JsonProperty("exp_data_rate_ul")
    String expDataRateUL;

    @JsonProperty("ue_mobility_level")
    String ueMobilityLevel;

    @JsonProperty("latency")
    String latency;

    @JsonProperty("use_interval")
    String useInterval;

    @JsonProperty("coverage_area_ta_list")
    List<String> coverageAreaTaList;

    @JsonProperty("activity_factor")
    String activityFactor;

    @JsonProperty("resource_sharing_level")
    String resourceSharingLevel;

    @JsonProperty("area_traffic_cap_ul")
    String areaTrafficCapUL;

    @JsonProperty("area_traffic_cap_dl")
    String areaTrafficCapDL;

    @JsonProperty("max_number_of_ues")
    String maxNumberOfUEs;

    @JsonProperty("serviceProfile_Availability")
    String serviceProfileAvailability;

    @JsonProperty("serviceProfile_PLMNIdList")
    String serviceProfilePLMNIdList;

    @JsonProperty("serviceProfile_Reliability")
    String serviceProfileReliability;

    @JsonProperty("serviceProfile_DLThptPerSlice")
    String serviceProfileDLThptPerSlice;

    @JsonProperty("serviceProfile_DLThptPerUE")
    String serviceProfileDLThptPerUE;

    @JsonProperty("serviceProfile_ULThptPerSlice")
    String serviceProfileULThptPerSlice;

    @JsonProperty("serviceProfile_ULThptPerUE")
    String serviceProfileULThptPerUE;

    @JsonProperty("serviceProfile_MaxPktSize")
    String serviceProfileMaxPktSize;

    @JsonProperty("serviceProfile_MaxNumberofConns")
    String serviceProfileMaxNumberofConns;

    @JsonProperty("serviceProfile_TermDensity")
    String serviceProfileTermDensity;

    @JsonProperty("serviceProfile_Jitter")
    String serviceProfileJitter;

    @JsonProperty("serviceProfile_SurvivalTime")
    String serviceProfileSurvivalTime;

}
