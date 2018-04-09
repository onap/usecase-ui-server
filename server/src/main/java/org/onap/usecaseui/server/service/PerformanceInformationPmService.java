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

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.onap.usecaseui.server.bean.PerformanceInformationPm;
import org.onap.usecaseui.server.util.Page;


public interface PerformanceInformationPmService {
    
    String savePerformanceInformationPm(PerformanceInformationPm performanceInformationPm);
    
    String updatePerformanceInformationPm(PerformanceInformationPm performanceInformationPm);
    
    int getAllCount(PerformanceInformationPm performanceInformationPm, int currentPage, int pageSize);
    
    Page<PerformanceInformationPm> queryPerformanceInformationPm(PerformanceInformationPm performanceInformationPm, int currentPage, int pageSize);
    
    List<PerformanceInformationPm> queryId(String[] id);

    List<PerformanceInformationPm> queryDateBetween(String eventId, Date startDate, Date endDate);

    List<PerformanceInformationPm> queryDateBetween(String resourceId, String name, String startTime, String endTime);

    List<Map<String,String>> queryMaxValueByBetweenDate(String sourceId, String name, String startTime, String endTime);

    List<PerformanceInformationPm> getAllPerformanceInformationByeventId(String eventId);
}
