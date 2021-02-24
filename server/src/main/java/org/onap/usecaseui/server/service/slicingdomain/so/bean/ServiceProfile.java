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
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ServiceProfile {

    private String termDensity;
    private String maxPktSize;
    private String maxNumberofUEs;
    private String survivalTime;
    private String reliability;
    private String latency;
    private String dLThptPerSlice;
    private String availability;
    private String sNSSAI;
    private String jitter;
    private String sST;
    private String maxNumberofConns;
    private String dLThptPerUE;
    private String uEMobilityLevel;
    private String uLThptPerUE;
    private String pLMNIdList;
    private String coverageAreaTAList;
    private String uLThptPerSlice;
    private String activityFactor;
    private String resourceSharingLevel;
    private String expDataRateDL;
    private String expDataRateUL;
    private String areaTrafficCapDL;
    private String areaTrafficCapUL;
}
