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

import org.onap.usecaseui.server.bean.AlarmsHeader;
import org.onap.usecaseui.server.bean.AlarmsInformation;

import java.io.Serializable;
import java.util.List;


public class AlarmBo implements Serializable {

    private AlarmsHeader alarmsHeader;

    private List<AlarmsInformation> alarmsInformation;


    public AlarmBo() {
    }

    public AlarmBo(AlarmsHeader alarmsHeader, List<AlarmsInformation> alarmsInformation) {
        this.alarmsHeader = alarmsHeader;
        this.alarmsInformation = alarmsInformation;
    }

    public AlarmsHeader getAlarmsHeader() {
        return alarmsHeader;
    }

    public void setAlarmsHeader(AlarmsHeader alarmsHeader) {
        this.alarmsHeader = alarmsHeader;
    }

    public List<AlarmsInformation> getAlarmsInformation() {
        return alarmsInformation;
    }

    public void setAlarmsInformation(List<AlarmsInformation> alarmsInformation) {
        this.alarmsInformation = alarmsInformation;
    }


}
