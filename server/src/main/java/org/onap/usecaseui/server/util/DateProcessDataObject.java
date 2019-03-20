/*-
 * ============LICENSE_START=======================================================
 *  Copyright (C) 2019 Samsung Electronics Co., Ltd. All rights reserved.
 * ================================================================================
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * SPDX-License-Identifier: Apache-2.0
 * ============LICENSE_END=========================================================
 */
package org.onap.usecaseui.server.util;

public class DateProcessDataObject {
    private String sourceName;
    private long startTimeL;
    private long endTimeL;
    private long timeIteraPlusVal;
    private long keyVal;
    private long keyValIteraVal;
    private String format;
    private String level;


    public String getSourceName() {
        return sourceName;
    }

    public DateProcessDataObject setSourceName(String sourceName) {
        this.sourceName = sourceName;
        return this;
    }

    public long getStartTimeL() {
        return startTimeL;
    }

    public DateProcessDataObject setStartTimeL(long startTimeL) {
        this.startTimeL = startTimeL;
        return this;
    }

    public long getEndTimeL() {
        return endTimeL;
    }

    public DateProcessDataObject setEndTimeL(long endTimeL) {
        this.endTimeL = endTimeL;
        return this;
    }

    public long getTimeIteraPlusVal() {
        return timeIteraPlusVal;
    }

    public DateProcessDataObject setTimeIteraPlusVal(long timeIteraPlusVal) {
        this.timeIteraPlusVal = timeIteraPlusVal;
        return this;
    }

    public long getKeyVal() {
        return keyVal;
    }

    public DateProcessDataObject setKeyVal(long keyVal) {
        this.keyVal = keyVal;
        return this;
    }

    public long getKeyValIteraVal() {
        return keyValIteraVal;
    }

    public DateProcessDataObject setKeyValIteraVal(long keyValIteraVal) {
        this.keyValIteraVal = keyValIteraVal;
        return this;
    }

    public String getFormat() {
        return format;
    }

    public DateProcessDataObject setFormat(String format) {
        this.format = format;
        return this;
    }

    public String getLevel() {
        return level;
    }

    public DateProcessDataObject setLevel(String level) {
        this.level = level;
        return this;
    }


}
