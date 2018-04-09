/**
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

import org.onap.usecaseui.server.bean.PerformanceHeader;
import org.onap.usecaseui.server.bean.PerformanceHeaderPm;
import org.onap.usecaseui.server.util.Page;


public interface PerformanceHeaderPmService {
    
    String savePerformanceHeaderPm(PerformanceHeaderPm performanceHederPm);
    
    String updatePerformanceHeaderPm(PerformanceHeaderPm performanceHederPm);
    
    int getAllCount(PerformanceHeaderPm performanceHederPm, int currentPage, int pageSize);
    
    Page<PerformanceHeaderPm> queryPerformanceHeaderPm(PerformanceHeaderPm performanceHederPm, int currentPage, int pageSize);
    
    List<PerformanceHeaderPm> queryId(String[] id);

    List<String> queryAllSourceId();


    public int getAllCountByEventType();
    public List<PerformanceHeaderPm> getAllByEventType(String eventName, String sourceName, String reportingEntityName, Date createTime, Date endTime);

    public PerformanceHeaderPm getPerformanceHeaderDetail(Integer id);

    public int getAllByDatetime(String eventId,String createTime);
}
