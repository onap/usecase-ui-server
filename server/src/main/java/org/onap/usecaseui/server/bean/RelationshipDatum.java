/*
 * Copyright (C) 2020 Huawei, Inc. and others. All rights reserved.
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
package org.onap.usecaseui.server.bean;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "relationship-key",
        "relationship-value"
})

public class RelationshipDatum {
    //Map<String, String> relationMap = new HashMap<>();


    @JsonProperty("relationship-key")
    private String relationshipKey;
    @JsonProperty("relationship-value")
    private String relationshipValue;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<>();

    @JsonProperty("relationship-key")
    public String getRelationshipKey() {
        return relationshipKey;
    }

    @JsonProperty("relationship-key")
    public void setRelationshipKey(String relationshipKey) {
        this.relationshipKey = relationshipKey;
    }

    @JsonProperty("relationship-value")
    public String getRelationshipValue() {
        return relationshipValue;
    }

    @JsonProperty("relationship-value")
    public void setRelationshipValue(String relationshipValue) {
        this.relationshipValue = relationshipValue;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
