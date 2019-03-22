/**
 * Copyright (C) 2019 Verizon. All Rights Reserved.
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

package org.onap.usecaseui.server.service.lcm.domain.aai.bean;

import com.fasterxml.jackson.annotation.JsonProperty;


public class AAISingleOrchestratorRsp {

    @JsonProperty("nfvo-id")
    private String nfvoId;

    @JsonProperty("resource-version")
    private String resourceVersion;

    @JsonProperty("api-root")
    private String apiRoot;

    @JsonProperty("esr-system-info-list")
    private EsrSystemInfoList esrSystemInfoList;

    public String getNfvoId() {
        return nfvoId;
    }

    public void setNfvoId(String nfvoId) {
        this.nfvoId = nfvoId;
    }

    public String getResourceVersion() {
        return resourceVersion;
    }

    public void setResourceVersion(String resourceVersion) {
        this.resourceVersion = resourceVersion;
    }

    public EsrSystemInfoList getEsrSystemInfoList() {
        return esrSystemInfoList;
    }

    public void setEsrSystemInfo(EsrSystemInfoList esrSystemInfoList) {
        this.esrSystemInfoList = esrSystemInfoList;
    }

    public String getApiRoot() {
        return apiRoot;
    }

    public void setApiRoot(String apiRoot) {
        this.apiRoot = apiRoot;
    }
}
