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

    @JsonProperty("an_enableNSSISelection")
    Boolean anEnableNSSISelection;

    @JsonProperty("sliceProfile_AN_sNSSAI")
    String sliceProfile_AN_sNSSAI;

    @JsonProperty("sliceProfile_AN_pLMNIdList")
    String sliceProfile_AN_pLMNIdList;

    @JsonProperty("sliceProfile_AN_maxNumberofUEs")
    String sliceProfile_AN_maxNumberofUEs;

    @JsonProperty("sliceProfile_AN_maxNumberofPDUSession")
    String sliceProfile_AN_maxNumberofPDUSession;

    @JsonProperty("sliceProfile_AN_expDataRateDL")
    String sliceProfile_AN_expDataRateDL;

    @JsonProperty("sliceProfile_AN_expDataRateUL")
    String sliceProfile_AN_expDataRateUL;

    @JsonProperty("sliceProfile_AN_areaTrafficCapDL")
    String sliceProfile_AN_areaTrafficCapDL;

    @JsonProperty("sliceProfile_AN_areaTrafficCapUL")
    String sliceProfile_AN_areaTrafficCapUL;

    @JsonProperty("sliceProfile_AN_overallUserDensity")
    String sliceProfile_AN_overallUserDensity;

    @JsonProperty("sliceProfile_AN_activityFactor")
    String sliceProfile_AN_activityFactor;

    @JsonProperty("sliceProfile_AN_uEMobilityLevel")
    String sliceProfile_AN_uEMobilityLevel;

    @JsonProperty("sliceProfile_AN_resourceSharingLevel")
    String sliceProfile_AN_resourceSharingLevel;

    @JsonProperty("sliceProfile_AN_sST")
    String sliceProfile_AN_sST;

    @JsonProperty("sliceProfile_AN_cSAvailabilityTarget")
    String sliceProfile_AN_cSAvailabilityTarget;

    @JsonProperty("sliceProfile_AN_cSReliabilityMeanTime")
    String sliceProfile_AN_cSReliabilityMeanTime;

    @JsonProperty("sliceProfile_AN_expDataRate")
    String sliceProfile_AN_expDataRate;

    @JsonProperty("sliceProfile_AN_msgSizeByte")
    String sliceProfile_AN_msgSizeByte;

    @JsonProperty("sliceProfile_AN_transferIntervalTarget")
    String sliceProfile_AN_transferIntervalTarget;

    @JsonProperty("sliceProfile_AN_survivalTime")
    String sliceProfile_AN_survivalTime;

    @JsonProperty("sliceProfile_AN_ipAddress")
    String sliceProfile_AN_ipAddress;

    @JsonProperty("sliceProfile_AN_logicInterfaceId")
    String sliceProfile_AN_logicInterfaceId;

    @JsonProperty("sliceProfile_AN_nextHopInfo")
    String sliceProfile_AN_nextHopInfo;

    @JsonProperty("tn_bh_suggest_nssi_id")
    String tnBhSuggestNssiId;

    @JsonProperty("tn_bh_suggest_nssi_name")
    String tnBhSuggestNssiName;

    @JsonProperty("tn_bh_latency")
    String tnBhLatency;

    @JsonProperty("tn_bh_bandwidth")
    String tnBhBandwidth;

    @JsonProperty("tn_bh_script_name")
    String tnBhScriptName;

    @JsonProperty("sliceProfile_TN_BH_jitte")
    String sliceProfile_TN_BH_jitte;

    @JsonProperty("sliceProfile_TN_BH_pLMNIdList")
    String sliceProfile_TN_BH_pLMNIdList;

    @JsonProperty("sliceProfile_TN_BH_sNSSAI")
    String sliceProfile_TN_BH_sNSSAI;

    @JsonProperty("sliceProfile_TN_BH_sST")
    String sliceProfile_TN_BH_sST;

    @JsonProperty("tn_bh_enableNSSISelection")
    Boolean tnEnableNSSISelection;

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

    @JsonProperty("cn_enableNSSISelection")
    Boolean cnEnableNSSISelection;

    @JsonProperty("sliceProfile_CN_pLMNIdList")
    String sliceProfile_CN_pLMNIdList;

    @JsonProperty("sliceProfile_CN_maxNumberofPDUSession")
    String sliceProfile_CN_maxNumberofPDUSession;

    @JsonProperty("sliceProfile_CN_overallUserDensity")
    String sliceProfile_CN_overallUserDensity;

    @JsonProperty("sliceProfile_CN_coverageAreaTAList")
    String sliceProfile_CN_coverageAreaTAList;

    @JsonProperty("sliceProfile_CN_sST")
    String sliceProfile_CN_sST;

    @JsonProperty("sliceProfile_CN_cSAvailabilityTarget")
    String sliceProfile_CN_cSAvailabilityTarget;

    @JsonProperty("sliceProfile_CN_cSReliabilityMeanTime")
    String sliceProfile_CN_cSReliabilityMeanTime;

    @JsonProperty("sliceProfile_CN_expDataRate")
    String sliceProfile_CN_expDataRate;

    @JsonProperty("sliceProfile_CN_msgSizeByte")
    String sliceProfile_CN_msgSizeByte;

    @JsonProperty("sliceProfile_CN_logicInterfaceId")
    String sliceProfile_CN_logicInterfaceId;

    @JsonProperty("sliceProfile_CN_transferIntervalTarget")
    String sliceProfile_CN_transferIntervalTarget;

    @JsonProperty("sliceProfile_CN_survivalTime")
    String sliceProfile_CN_survivalTime;

    @JsonProperty("sliceProfile_CN_ipAddress")
    String sliceProfile_CN_ipAddress;

    @JsonProperty("sliceProfile_CN_nextHopInfo")
    String sliceProfile_CN_nextHopInfo;

}
