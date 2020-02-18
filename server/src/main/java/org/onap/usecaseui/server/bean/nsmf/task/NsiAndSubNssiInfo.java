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
public class NsiAndSubNssiInfo {

    @JsonProperty("suggest_nsi_id")
    String suggestNsiId;

    @JsonProperty("suggest_nsi_name")
    String suggestNsiName;

    @JsonProperty("an_suggest_nssi_id")
    String anSuggestNssiId;

    @JsonProperty("an_suggest_nssi_name")
    String anSuggestNssiName;

    @JsonProperty("an_latency")
    String anLatency;

    @JsonProperty("an_5qi")
    String an5qi;

    @JsonProperty("an_coverage_area_ta_list")
    List<String> anCoverageAreaTaList;

    @JsonProperty("an_script_name")
    String anScriptName;

    @JsonProperty("tn_suggest_nssi_id")
    String tnSuggestNssiId;

    @JsonProperty("tn_suggest_nssi_name")
    String tnSuggestNssiName;

    @JsonProperty("tn_latency")
    String tnLatency;

    @JsonProperty("tn_bandwidth")
    String tnBandwidth;

    @JsonProperty("tn_script_name")
    String tnScriptName;

    @JsonProperty("cn_suggest_nssi_id")
    String cnSuggestNssiId;

    @JsonProperty("cn_suggest_nssi_name")
    String cnSuggestNssiName;

    @JsonProperty("cn_service_snssai")
    String cnServiceSnssai;

    @JsonProperty("cn_resource_sharing_level")
    String cnResourceSharingLevel;

    @JsonProperty("cn_ue_mobility_level")
    String cnUeMobilityLevel;

    @JsonProperty("cn_latency")
    String cnLatency;

    @JsonProperty("cn_max_number_of_ues")
    String cnMaxNumberOfUes;

    @JsonProperty("cn_activity_factor")
    String cnActivityFactor;

    @JsonProperty("cn_exp_data_rate_dl")
    String cnExpDataRateDl;

    @JsonProperty("cn_exp_data_rate_ul")
    String cnExpDataRateUl;

    @JsonProperty("cn_area_traffic_cap_dl")
    String cnAreaTrafficCapDl;

    @JsonProperty("cn_area_traffic_cap_ul")
    String cnAreaTrafficCapUl;

    @JsonProperty("cn_script_name")
    String cnScriptName;
}
