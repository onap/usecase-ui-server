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
