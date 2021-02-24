package org.onap.usecaseui.server.service.slicingdomain.aai.bean.connection;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class NetworkPolicy {
    @JsonProperty("latency")
    private String latency;
    @JsonProperty("jitter")
    private String jitter;
    @JsonProperty("max-bandwidth")
    private String maxBandwidth;
}
