package org.onap.usecaseui.server.service.slicingdomain.aai.bean.connection;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConnectionLink {
    @JsonProperty("link-name")
    private String linkName;
    @JsonProperty("in-maint")
    private String inMaint;
    @JsonProperty("link-type")
    private String linkType;
    @JsonProperty("resource-version")
    private String resourceVersion;
    @JsonProperty("link-name2")
    private String linkName2;
    @JsonProperty("link-id")
    private String linkId;
    @JsonProperty("service-function")
    private String serviceFunction;
    @JsonProperty("relationship-list")
    private RelationshipList relationshipList;


}
