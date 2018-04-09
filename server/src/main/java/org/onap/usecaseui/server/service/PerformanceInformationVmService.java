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

import org.onap.usecaseui.server.bean.PerformanceInformationVm;
import org.onap.usecaseui.server.util.Page;


public interface PerformanceInformationVmService {
    
    String savePerformanceInformationVm(PerformanceInformationVm performanceInformationVm);
    
    String updatePerformanceInformationVm(PerformanceInformationVm performanceInformationVm);
    
    int getAllCount(PerformanceInformationVm performanceInformationVm, int currentPage, int pageSize);
    
    Page<PerformanceInformationVm> queryPerformanceInformationVm(PerformanceInformationVm performanceInformationVm, int currentPage, int pageSize);
    
    List<PerformanceInformationVm> queryId(String[] id);

    List<PerformanceInformationVm> queryDateBetween(String eventId, Date startDate, Date endDate);

    List<PerformanceInformationVm> queryDateBetween(String resourceId, String name, String startTime, String endTime);

    List<Map<String,String>> queryMaxValueByBetweenDate(String sourceId, String name, String startTime, String endTime);

    List<PerformanceInformationVm> getAllPerformanceInformationByeventId(String eventId);
}
