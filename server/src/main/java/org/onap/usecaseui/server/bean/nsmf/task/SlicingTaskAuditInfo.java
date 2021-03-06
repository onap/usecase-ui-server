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

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SlicingTaskAuditInfo {
    //JSONField指定转成Json串时的格式和顺序
    //JsonProperty 指定接受前台的相应时的接口格式
    @JSONField(name="task_id",ordinal=0)
    @JsonProperty("task_id")
    private String taskId;

    @JsonProperty("task_name")
    private String taskName;

    @JsonProperty("create_time")
    private String createTime;

    @JsonProperty("processing_status")
    private String processingStatus;

    @JsonProperty("business_demand_info")
    private BusinessDemandInfo businessDemandInfo;

    @JsonProperty("nst_info")
    private NstInfo nstInfo;

    @JsonProperty("nsi_nssi_info")
    private NsiAndSubNssiInfo nsiAndSubNssiInfo;

}
