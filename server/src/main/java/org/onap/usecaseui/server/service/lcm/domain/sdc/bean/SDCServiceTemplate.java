/**
 * Copyright 2016-2017 ZTE Corporation.
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
package org.onap.usecaseui.server.service.lcm.domain.sdc.bean;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class SDCServiceTemplate {

    private String uuid;

    private String invariantUUID;

    private String name;

    private String version;

    private String toscaModelURL;

    private String category;

    @JsonCreator
    public SDCServiceTemplate(
            @JsonProperty String uuid,
            @JsonProperty String invariantUUID,
            @JsonProperty String name,
            @JsonProperty String version,
            @JsonProperty String toscaModelURL,
            @JsonProperty String category) {
        this.uuid = uuid;
        this.invariantUUID = invariantUUID;
        this.name = name;
        this.version = version;
        this.toscaModelURL = toscaModelURL;
        this.category = category;
    }

    public String getUuid() {
        return uuid;
    }

    public String getInvariantUUID() {
        return invariantUUID;
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    public String getToscaModelURL() {
        return toscaModelURL;
    }

    public String getCategory() {
        return category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SDCServiceTemplate that = (SDCServiceTemplate) o;
        return Objects.equals(uuid, that.uuid) &&
                Objects.equals(invariantUUID, that.invariantUUID) &&
                Objects.equals(name, that.name) &&
                Objects.equals(version, that.version) &&
                Objects.equals(toscaModelURL, that.toscaModelURL) &&
                Objects.equals(category, that.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, invariantUUID, name, version, toscaModelURL, category);
    }
}
