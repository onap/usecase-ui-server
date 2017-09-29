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

public class Vnf {

    private String uuid;

    private String invariantUUID;

    private String name;

    @JsonCreator
    public Vnf(
            @JsonProperty String uuid,
            @JsonProperty String invariantUUID,
            @JsonProperty String name) {
        this.uuid = uuid;
        this.invariantUUID = invariantUUID;
        this.name = name;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vnf vnf = (Vnf) o;
        return Objects.equals(uuid, vnf.uuid) &&
                Objects.equals(invariantUUID, vnf.invariantUUID) &&
                Objects.equals(name, vnf.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, invariantUUID, name);
    }
}
