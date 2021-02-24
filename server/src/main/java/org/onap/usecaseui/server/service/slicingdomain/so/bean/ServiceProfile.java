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
