/*
 * Copyright (C) 2021 CMCC, Inc. and others. All rights reserved.
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

package org.onap.usecaseui.server.service.slicingdomain.so.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TnFHSliceTaskInfo {
    private String suggestNssiId;
    private String suggestNssiName;
    private String progress;
    private String status;
    private String statusDescription;
    @JsonProperty("sliceProfile")
    private SliceProfile sliceProfile;
    private String sliceInstanceId;
    private String scriptName;
    private String vendor;
    private String networkType;
    private String subnetType;
    private String endPointId;
    private NstInfo nsstinfo;

}
