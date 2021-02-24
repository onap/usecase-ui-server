package org.onap.usecaseui.server.service.slicingdomain.aai.bean.connection;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class RelationshipData {
    @JsonProperty("relationship-key")
    private String relationshipKey;
    @JsonProperty("relationship-value")
    private String relationshipValue;
}
