package org.onap.usecaseui.server.service.slicingdomain.aai.bean.connection;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class RelationshipList {
    @JsonProperty("relationship")
    private List<Relationship> relationship;
}
