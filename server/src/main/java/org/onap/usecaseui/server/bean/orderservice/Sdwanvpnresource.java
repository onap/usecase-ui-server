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
package org.onap.usecaseui.server.bean.orderservice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "sdwanvpn",
        "sdwanlan"
})
public class Sdwanvpnresource {
    @JsonProperty("sdwanvpn")
    private List<Sdwanvpn> sdwanvpn = null;
    @JsonProperty("sdwanlan")
    private List<Sdwanlan> sdwanlan = null;


    @JsonProperty("sdwanvpn")
    public List<Sdwanvpn> getSdwanvpn() {
        return sdwanvpn;
    }

    @JsonProperty("sdwanvpn")
    public void setSdwanvpn(List<Sdwanvpn> sdwanvpn) {
        this.sdwanvpn = sdwanvpn;
    }

    @JsonProperty("sdwanlan")
    public List<Sdwanlan> getSdwanlan() {
        return sdwanlan;
    }

    @JsonProperty("sdwanlan")
    public void setSdwanlan(List<Sdwanlan> sdwanlan) {
        this.sdwanlan = sdwanlan;
    }
}
