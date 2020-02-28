/*
 * Copyright (C) 2017 CMCC, Inc. and others. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.onap.usecaseui.server.bean.activateEdge;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "related-to",
        "relationship-label",
        "related-link",
        "relationship-data",
        "related-to-property"
})

public class Relationship {

    @JsonProperty("related-to")
    private String relatedTo;
    @JsonProperty("relationship-label")
    private String relationshipLabel;
    @JsonProperty("related-link")
    private String relatedLink;
    @JsonProperty("relationship-data")
    private List<RelationshipDatum> relationshipData = null;
    @JsonProperty("related-to-property")
    private List<RelatedToProperty> relatedToProperty = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("related-to")
    public String getRelatedTo() {
        return relatedTo;
    }

    @JsonProperty("related-to")
    public void setRelatedTo(String relatedTo) {
        this.relatedTo = relatedTo;
    }

    @JsonProperty("relationship-label")
    public String getRelationshipLabel() {
        return relationshipLabel;
    }

    @JsonProperty("relationship-label")
    public void setRelationshipLabel(String relationshipLabel) {
        this.relationshipLabel = relationshipLabel;
    }

    @JsonProperty("related-link")
    public String getRelatedLink() {
        return relatedLink;
    }

    @JsonProperty("related-link")
    public void setRelatedLink(String relatedLink) {
        this.relatedLink = relatedLink;
    }

    @JsonProperty("relationship-data")
    public List<RelationshipDatum> getRelationshipData() {
        return relationshipData;
    }

    @JsonProperty("relationship-data")
    public void setRelationshipData(List<RelationshipDatum> relationshipData) {
        this.relationshipData = relationshipData;
    }

    @JsonProperty("related-to-property")
    public List<RelatedToProperty> getRelatedToProperty() {
        return relatedToProperty;
    }

    @JsonProperty("related-to-property")
    public void setRelatedToProperty(List<RelatedToProperty> relatedToProperty) {
        this.relatedToProperty = relatedToProperty;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        return "{" +
                "\"related-to\":\"" + relatedTo + '\"' +
                ", \"relationship-label\":\"" + relationshipLabel + '\"' +
                ", \"related-link\":\"" + relatedLink + '\"' +
                ", \"relationship-data\":" + relationshipData +
                ", \"related-to-property\":" + relatedToProperty +
                '}';
    }

}
