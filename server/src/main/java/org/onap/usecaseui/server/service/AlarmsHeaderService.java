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


import java.sql.Timestamp;
import java.util.List;

import org.onap.usecaseui.server.bean.AlarmsHeader;
import org.onap.usecaseui.server.util.Page;

public interface AlarmsHeaderService {
    
    String saveAlarmsHeader(AlarmsHeader alarmsHeader);
    
    String updateAlarmsHeader(AlarmsHeader alarmsHeader);
    
    int getAllCount(AlarmsHeader alarmsHeader, int currentPage, int pageSize);
    
    Page<AlarmsHeader> queryAlarmsHeader(AlarmsHeader alarmsHeader, int currentPage, int pageSize);
    
    List<AlarmsHeader> queryId(String[] id);
    
    public String updateAlarmsHeader2018(String status, Timestamp date, String startEpochMicrosecCleared, String lastEpochMicroSecCleared, String eventName, String reportingEntityName, String specificProblem);
    
    /**
     * 
      * queryStatusCount
      * 
      * @Auther YYY
      * @Date   2018/5/17 15:59:23
      * @Title: queryStatusCount
      * @Description: 
      * @param status
      * @return
     */
    String queryStatusCount(String status);
    
    /**
     * 
      * getAlarmsHeaderById
      * 
      * @Auther YYY
      * @Date   2018/5/17 16:05:59
      * @Title: getAlarmsHeaderById
      * @Description: 
      * @param id
      * @return
     */
    AlarmsHeader getAlarmsHeaderById(String id);
}
