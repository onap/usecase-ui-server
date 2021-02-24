package org.onap.usecaseui.server.service.slicingdomain.aai.bean.connection;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Relationship {
    @JsonProperty("related-to")
    private String relatedTo;
    @JsonProperty("relationship-label")
    private String relationshipLabel;
    @JsonProperty("related-link")
    private String relatedLink;
    @JsonProperty("relationship-data")
    private List<RelationshipData> relationshipDataList;



}
