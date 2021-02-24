package org.onap.usecaseui.server.service.slicingdomain.aai.bean.connectionvo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConnectionListVo {

    private String linkId;

    private EndPointInfoListVo anInfo;

    private EndPointInfoListVo cnInfo;

    private PropertiesVo properties;
}
