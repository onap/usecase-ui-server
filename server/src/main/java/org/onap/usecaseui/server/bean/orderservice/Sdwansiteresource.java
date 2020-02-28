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
        "sdwandevice",
        "site",
        "sitewanport"
})
public class Sdwansiteresource {

    @JsonProperty("sdwandevice")
    private List<Sdwandevice> sdwandevice = null;
    @JsonProperty("site")
    private List<Sites> site = null;
    @JsonProperty("sitewanport")
    private List<Sitewanport> sitewanport = null;


    @JsonProperty("sdwandevice")
    public List<Sdwandevice> getSdwandevice() {
        return sdwandevice;
    }

    @JsonProperty("sdwandevice")
    public void setSdwandevice(List<Sdwandevice> sdwandevice) {
        this.sdwandevice = sdwandevice;
    }

    @JsonProperty("site")
    public List<Sites> getSite() {
        return site;
    }

    @JsonProperty("site")
    public void setSite(List<Sites> site) {
        this.site = site;
    }

    @JsonProperty("sitewanport")
    public List<Sitewanport> getSitewanport() {
        return sitewanport;
    }

    @JsonProperty("sitewanport")
    public void setSitewanport(List<Sitewanport> sitewanport) {
        this.sitewanport = sitewanport;
    }
}
