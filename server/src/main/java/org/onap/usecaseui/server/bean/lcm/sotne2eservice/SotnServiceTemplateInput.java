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

package org.onap.usecaseui.server.bean.lcm.sotne2eservice;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class SotnServiceTemplateInput {

    @JsonProperty("service")
    private SotnService service;
    @JsonProperty("sites")
    private List<SotnSites> sites;

    public SotnService getService() {
        return service;
    }

    public void setService(SotnService service) {
        this.service = service;
    }

    public List<SotnSites> getSites() {
        return sites;
    }

    public void setSites(List<SotnSites> sites) {
        this.sites = sites;
    }
}
