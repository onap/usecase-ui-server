package org.onap.usecaseui.server.service.slicingdomain.aai.bean.connectionvo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class PropertiesVo {

    private String latency;

    private String jitter;

    private String maxBandwidth;

    private String resourceSharingLevel;
}
