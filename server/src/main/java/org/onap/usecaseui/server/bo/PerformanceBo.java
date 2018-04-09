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

    private PerformanceHeaderPm performanceHeaderPm;

    private List<PerformanceInformationPm> performanceInformationPm;

    private PerformanceHeaderVm performanceHeaderVm;

    private List<PerformanceInformationVm> performanceInformationVm;


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


    public PerformanceHeaderPm getPerformanceHeaderPm() {
        return performanceHeaderPm;
    }

    public void setPerformanceHeaderPm(PerformanceHeaderPm performanceHeaderPm) {
        this.performanceHeaderPm = performanceHeaderPm;
    }

    public List<PerformanceInformationPm> getPerformanceInformationPm() {
        return performanceInformationPm;
    }

    public void setPerformanceInformationPm(List<PerformanceInformationPm> performanceInformationPm) {
        this.performanceInformationPm = performanceInformationPm;
    }




    public PerformanceHeaderVm getPerformanceHeaderVm() {
        return performanceHeaderVm;
    }

    public void setPerformanceHeaderVm(PerformanceHeaderVm performanceHeaderVm) {
        this.performanceHeaderVm = performanceHeaderVm;
    }

    public List<PerformanceInformationVm> getPerformanceInformationVm() {
        return performanceInformationVm;
    }

    public void setPerformanceInformationVm(List<PerformanceInformationVm> performanceInformationVm) {
        this.performanceInformationVm = performanceInformationVm;
    }
}
