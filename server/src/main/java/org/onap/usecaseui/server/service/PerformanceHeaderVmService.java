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

import org.onap.usecaseui.server.bean.PerformanceHeader;
import org.onap.usecaseui.server.bean.PerformanceHeaderVm;
import org.onap.usecaseui.server.util.Page;


public interface PerformanceHeaderVmService {
    
    String savePerformanceHeaderVm(PerformanceHeaderVm performanceHederVm);
    
    String updatePerformanceHeaderVm(PerformanceHeaderVm performanceHederVm);
    
    int getAllCount(PerformanceHeaderVm performanceHederVm, int currentPage, int pageSize);
    
    Page<PerformanceHeaderVm> queryPerformanceHeaderVm(PerformanceHeaderVm performanceHederVm, int currentPage, int pageSize);
    
    List<PerformanceHeaderVm> queryId(String[] id);

    List<String> queryAllSourceId();



    public int getAllCountByEventType();
    public List<PerformanceHeaderVm> getAllByEventType(String eventName, String sourceName, String reportingEntityName, Date createTime, Date endTime);


    public PerformanceHeaderVm getPerformanceHeaderDetail(Integer id);

    public int getAllByDatetime(String eventId,String createTime);
}
