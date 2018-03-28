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
package org.onap.usecaseui.server.bo;

import org.onap.usecaseui.server.bean.*;
import org.onap.usecaseui.server.util.Page;

import java.io.Serializable;
import java.util.List;

public class PerformanceBo implements Serializable {

    private PerformanceHeader performanceHeader;

    private List<PerformanceInformation> performanceInformation;




    public PerformanceBo() {
    }


    public PerformanceHeader getPerformanceHeader() {
        return performanceHeader;
    }

    public void setPerformanceHeader(PerformanceHeader performanceHeader) {
        this.performanceHeader = performanceHeader;
    }

    public List<PerformanceInformation> getPerformanceInformation() {
        return performanceInformation;
    }

    public void setPerformanceInformation(List<PerformanceInformation> performanceInformation) {
        this.performanceInformation = performanceInformation;
    }








}
