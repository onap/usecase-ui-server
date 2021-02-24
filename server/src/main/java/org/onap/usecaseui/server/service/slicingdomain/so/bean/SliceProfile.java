/*
 * Copyright (C) 2021 CMCC, Inc. and others. All rights reserved.
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

package org.onap.usecaseui.server.service.slicingdomain.so.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SliceProfile {

    private String  sliceProfileId;
    private String  sNSSAIList;
    private String  pLMNIdList;
    private String  maxNumberOfUEs;
    private String  coverageAreaTAList;
    private String  latency;
    private String  ueMobilityLevel;
    private String  resourceSharingLevel;
    private String  maxBandwidth;
    private String  sST;
    private String  activityFactor;
    private String  survivalTime;
    private String  expDataRateUL;
    private String  expDataRateDL;
    private String  areaTrafficCapUL;
    private String  areaTrafficCapDL;
    private String  jitter;
    private String  csAvailabilityTarget;
    private String  expDataRate;
    private String  maxNumberOfPDUSession;
    private String  overallUserDensity;
    private String  csReliabilityMeanTime;
    private String  msgSizeByte;
    private String  transferIntervalTarget;
    private String  ipAddress;
    private String  logicInterfaceId;
    private String  nextHopInfo;
    @JsonProperty("5QI")
    private String  an5qi;

}
