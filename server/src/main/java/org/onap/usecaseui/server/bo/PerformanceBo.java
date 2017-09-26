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

import org.onap.usecaseui.server.bean.PerformanceHeader;
import org.onap.usecaseui.server.bean.PerformanceInformation;
import org.onap.usecaseui.server.util.Page;

import java.io.Serializable;

public class PerformanceBo implements Serializable {

    private PerformanceHeader performanceHeader;

    private PerformanceInformation performanceInformation;

    private int currentPage;

    private int pageSize;

    public PerformanceBo() {
    }

    public PerformanceBo(PerformanceHeader performanceHeader, PerformanceInformation performanceInformation, int currentPage, int pageSize) {
        this.performanceHeader = performanceHeader;
        this.performanceInformation = performanceInformation;
        this.currentPage = currentPage;
        this.pageSize = pageSize;
    }

    public PerformanceHeader getPerformanceHeader() {
        return performanceHeader;
    }

    public void setPerformanceHeader(PerformanceHeader performanceHeader) {
        this.performanceHeader = performanceHeader;
    }

    public PerformanceInformation getPerformanceInformation() {
        return performanceInformation;
    }

    public void setPerformanceInformation(PerformanceInformation performanceInformation) {
        this.performanceInformation = performanceInformation;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
