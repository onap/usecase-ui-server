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
package org.onap.usecaseui.server.wrapper;

import org.onap.usecaseui.server.bean.AlarmsHeader;
import org.onap.usecaseui.server.bean.AlarmsInformation;

public class AlarmWrapper {

    private AlarmsHeader alarmsHeader;

    private AlarmsInformation alarmsInformation;

    private int currentPage = 1;

    private int pageSize = 100;


    public AlarmsHeader getAlarmsHeader() {
        return alarmsHeader;
    }

    public void setAlarmsHeader(AlarmsHeader alarmsHeader) {
        this.alarmsHeader = alarmsHeader;
    }

    public AlarmsInformation getAlarmsInformation() {
        return alarmsInformation;
    }

    public void setAlarmsInformation(AlarmsInformation alarmsInformation) {
        this.alarmsInformation = alarmsInformation;
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
