/*
 * Copyright (C) 2019 CMCC, Inc. and others. All rights reserved.
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
package org.onap.usecaseui.server.bean.nsmf.task;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SlicingTaskCreationProgress {
    @JsonProperty("an_progress")
    private String anProgress;

    @JsonProperty("an_status")
    private String anStatus;

    @JsonProperty("an_statusDescription")
    private String anStatusDescription;

    @JsonProperty("tn_progress")
    private String tnProgress;

    @JsonProperty("tn_status")
    private String tnStatus;

    @JsonProperty("tn_statusDescription")
    private String tnStatusDescription;

    @JsonProperty("cn_progress")
    private String cnProgress;

    @JsonProperty("cn_status")
    private String cnStatus;

    @JsonProperty("cn_statusDescription")
    private String cnStatusDescription;

}
