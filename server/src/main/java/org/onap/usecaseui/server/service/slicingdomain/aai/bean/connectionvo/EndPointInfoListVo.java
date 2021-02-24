package org.onap.usecaseui.server.service.slicingdomain.aai.bean.connectionvo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class EndPointInfoListVo {


    private String ipAddress;
    private String logicId;
    private String nextHop;
}
