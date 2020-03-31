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

    @JsonProperty("exp-data-rate-UL")
    private int expDataRateUL;

    @JsonProperty("exp-data-rate-DL")
    private int expDataRateDL;

    @JsonProperty("area-traffic-cap-UL")
    private int areaTrafficCapUL;

    @JsonProperty("area-traffic-cap-DL")
    private int areaTrafficCapDL;

    @JsonProperty("activity-factor")
    private int activityFactor;

    @JsonProperty("jitter")
    private int jitter;

    @JsonProperty("survival-time")
    private int survivalTime;

    @JsonProperty("cs-availability")
    private int csAvailability;

    @JsonProperty("reliability")
    private int reliability;

    @JsonProperty("exp-data-rate")
    private int expDataRate;

    @JsonProperty("traffic-density")
    private int trafficDensity;

    @JsonProperty("conn-density")
    private int connDensity;

    @JsonProperty("resource-version")
    private String resourceVersion;

    public String getProfileId() {
        return profileId;
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }

    public int getLatency() {
        return latency;
    }

    public void setLatency(int latency) {
        this.latency = latency;
    }

    public int getMaxNumberOfUEs() {
        return maxNumberOfUEs;
    }

    public void setMaxNumberOfUEs(int maxNumberOfUEs) {
        this.maxNumberOfUEs = maxNumberOfUEs;
    }

    public String getCoverageAreaTAList() {
        return coverageAreaTAList;
    }

    public void setCoverageAreaTAList(String coverageAreaTAList) {
        this.coverageAreaTAList = coverageAreaTAList;
    }

    public String getUeMobilityLevel() {
        return ueMobilityLevel;
    }

    public void setUeMobilityLevel(String ueMobilityLevel) {
        this.ueMobilityLevel = ueMobilityLevel;
    }

    public String getResourceSharingLevel() {
        return resourceSharingLevel;
    }

    public void setResourceSharingLevel(String resourceSharingLevel) {
        this.resourceSharingLevel = resourceSharingLevel;
    }

    public int getExpDataRateUL() {
        return expDataRateUL;
    }

    public void setExpDataRateUL(int expDataRateUL) {
        this.expDataRateUL = expDataRateUL;
    }

    public int getExpDataRateDL() {
        return expDataRateDL;
    }

    public void setExpDataRateDL(int expDataRateDL) {
        this.expDataRateDL = expDataRateDL;
    }

    public int getAreaTrafficCapUL() {
        return areaTrafficCapUL;
    }

    public void setAreaTrafficCapUL(int areaTrafficCapUL) {
        this.areaTrafficCapUL = areaTrafficCapUL;
    }

    public int getAreaTrafficCapDL() {
        return areaTrafficCapDL;
    }

    public void setAreaTrafficCapDL(int areaTrafficCapDL) {
        this.areaTrafficCapDL = areaTrafficCapDL;
    }

    public int getActivityFactor() {
        return activityFactor;
    }

    public void setActivityFactor(int activityFactor) {
        this.activityFactor = activityFactor;
    }

    public int getJitter() {
        return jitter;
    }

    public void setJitter(int jitter) {
        this.jitter = jitter;
    }

    public int getSurvivalTime() {
        return survivalTime;
    }

    public void setSurvivalTime(int survivalTime) {
        this.survivalTime = survivalTime;
    }

    public int getCsAvailability() {
        return csAvailability;
    }

    public void setCsAvailability(int csAvailability) {
        this.csAvailability = csAvailability;
    }

    public int getReliability() {
        return reliability;
    }

    public void setReliability(int reliability) {
        this.reliability = reliability;
    }

    public int getExpDataRate() {
        return expDataRate;
    }

    public void setExpDataRate(int expDataRate) {
        this.expDataRate = expDataRate;
    }

    public int getTrafficDensity() {
        return trafficDensity;
    }

    public void setTrafficDensity(int trafficDensity) {
        this.trafficDensity = trafficDensity;
    }

    public int getConnDensity() {
        return connDensity;
    }

    public void setConnDensity(int connDensity) {
        this.connDensity = connDensity;
    }

    public String getResourceVersion() {
        return resourceVersion;
    }

    public void setResourceVersion(String resourceVersion) {
        this.resourceVersion = resourceVersion;
    }


}
