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
package org.onap.usecaseui.server.service;

import java.util.List;
import java.util.Map;

import org.onap.usecaseui.server.bean.AlarmsHeader;
import org.onap.usecaseui.server.bean.AlarmsInformation;
import org.onap.usecaseui.server.util.Page;


public interface AlarmsInformationService {
    
    String saveAlarmsInformation(AlarmsInformation alarmsInformation);
    
    String updateAlarmsInformation(AlarmsInformation alarmsInformation);
    
    int getAllCount(AlarmsInformation alarmsInformation, int currentPage, int pageSize);
    
    Page<AlarmsInformation> queryAlarmsInformation(AlarmsInformation alarmsInformation, int currentPage, int pageSize);

    List<AlarmsInformation> queryId(String[] id);

    List<Map<String,String>> queryDateBetween(String sourceId, String startTime, String endTime);

    String queryCountValueByDataBetween(String sourceId, String startTime, String endTime);
}
