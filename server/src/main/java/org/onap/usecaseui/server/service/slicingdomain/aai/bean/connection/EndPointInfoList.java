package org.onap.usecaseui.server.service.slicingdomain.aai.bean.connection;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class EndPointInfoList {


    @JsonProperty("ip-address")
    private String ipAddress;
    @JsonProperty("logic-id")
    private String logicId;
    @JsonProperty("next-hop")
    private String nextHop;
}
