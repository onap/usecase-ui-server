/**
 * Copyright 2016-2017 ZTE Corporation.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package org.onap.usecaseui.server.service.slicingdomain.kpi.bean;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.onap.usecaseui.server.bean.nsmf.monitor.SlicingKpiReqInfo;
import org.onap.usecaseui.server.bean.nsmf.monitor.TrafficReqInfo;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class KpiUserNumber {

    private List<UserNumbers> result;

    private SlicingKpiReqInfo request;

    private int result_count;

}
