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

package org.onap.usecaseui.server.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jakarta.annotation.Resource;

import org.onap.usecaseui.server.bean.PerformanceHeader;
import org.onap.usecaseui.server.bean.PerformanceInformation;
import org.onap.usecaseui.server.constant.CommonConstant;
import org.onap.usecaseui.server.service.PerformanceHeaderService;
import org.onap.usecaseui.server.service.PerformanceInformationService;
import org.onap.usecaseui.server.util.Page;
import org.onap.usecaseui.server.util.UuiCommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@Configuration
@EnableAspectJAutoProxy
public class PerformanceController {

    @Resource(name = "PerformanceHeaderService")
    private PerformanceHeaderService performanceHeaderService;

    @Resource(name = "PerformanceInformationService")
    private PerformanceInformationService performanceInformationService;

    private Logger logger = LoggerFactory.getLogger(PerformanceController.class);

    public void setPerformanceHeaderService(PerformanceHeaderService performanceHeaderService) {
        this.performanceHeaderService = performanceHeaderService;
    }

    public void setPerformanceInformationService(PerformanceInformationService performanceInformationService) {
        this.performanceInformationService = performanceInformationService;
    }

    private ObjectMapper omPerformance = new ObjectMapper();

    @GetMapping(value = {"/performance/{currentPage}/{pageSize}"}, produces = "application/json")
    public String getPerformanceData(@PathVariable String currentPage,
                                     @PathVariable String pageSize,
                                     @RequestParam(required = false) String sourceName,
                                     @RequestParam(required = false) String startTime,@RequestParam(required = false) String endTime) throws JsonProcessingException, ParseException {
        Page<PerformanceHeader> pa = new Page<PerformanceHeader>();
            PerformanceHeader performanceHeader = new PerformanceHeader.PerformanceHeaderBuilder().createPerformanceHeader();
            performanceHeader.setSourceName(sourceName);
            performanceHeader.setStartEpochMicrosec(!UuiCommonUtil.isNotNullOrEmpty(startTime)?null:new SimpleDateFormat(CommonConstant.DATE_FORMAT).parse(startTime).getTime()+"");
            performanceHeader.setLastEpochMicroSec(!UuiCommonUtil.isNotNullOrEmpty(endTime)?null:new SimpleDateFormat(CommonConstant.DATE_FORMAT).parse(endTime).getTime()+"");
            pa = performanceHeaderService.queryPerformanceHeader(performanceHeader,Integer.parseInt(currentPage),Integer.parseInt(pageSize));
        try {
            Map<String,Object> map = new HashMap<>();
            map.put("performances",pa.getList());
            map.put("totalRecords",pa.getTotalRecords());
            omPerformance.setDateFormat(new SimpleDateFormat(CommonConstant.DATE_FORMAT));
            return omPerformance.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            logger.error("JsonProcessingException"+e.getMessage());
            return omPerformance.writeValueAsString("failed");
        }
    }

    @GetMapping(value = {"/performance/queryAllSourceNames"})
    public String getSourceIds(){
        try {
            return omPerformance.writeValueAsString(performanceHeaderService.queryAllSourceNames());
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage());
            return "";
        }
    }

    @RequestMapping("/performance/getPerformanceHeaderDetail/{id}")
    public String getPerformanceHeaderDetail(@PathVariable String id) throws JsonProcessingException {
        PerformanceHeader performanceHeader= performanceHeaderService.getPerformanceHeaderById(id);
        List<PerformanceInformation> list =new ArrayList<>();
        if(UuiCommonUtil.isNotNullOrEmpty(performanceHeader)){
        	String headerId = performanceHeader.getId();
        	list = performanceInformationService.getAllPerformanceInformationByHeaderId(headerId);
        }

        Map map = new HashMap();
        map.put("performanceHeader",performanceHeader);
        map.put("list",list);

        String string =omPerformance.writeValueAsString(map);
        return string;
    }

    @GetMapping(value = {"/performance/getSourceNames/{currentPage}/{pageSize}"}, produces = "application/json")
    public String getPerformanceSourceNames(@PathVariable String currentPage,@PathVariable String pageSize,
            @RequestParam(required = false) String sourceName) throws JsonProcessingException{
        PerformanceHeader performanceHeader = new PerformanceHeader.PerformanceHeaderBuilder().createPerformanceHeader();
        Page<PerformanceHeader> page = new Page<PerformanceHeader>();
        Set<String> names = new HashSet<String>();
        performanceHeader.setSourceName(sourceName);
        page = performanceHeaderService.queryPerformanceHeader(performanceHeader,1,Integer.MAX_VALUE);
        if(!UuiCommonUtil.isNotNullOrEmpty(page)){
            page = new Page<>();
            List<PerformanceHeader> list = new ArrayList<>();
            performanceHeader =  new PerformanceHeader.PerformanceHeaderBuilder().createPerformanceHeader();
            list.add(performanceHeader);
            page.setList(list);

        }
        for(int a=0;a<page.getList().size();a++){
        	performanceHeader  = (PerformanceHeader)page.getList().get(a);
            String name = performanceHeader.getSourceName();
            names.add(name);
        }
        Map<String,Object> map = new HashMap<>();
        map.put("names",names.size());
        map.put("totalRecords",UuiCommonUtil.getPageList(new ArrayList(names), Integer.parseInt(currentPage),Integer.parseInt(pageSize)));
    	return omPerformance.writeValueAsString(map);
    }
}
