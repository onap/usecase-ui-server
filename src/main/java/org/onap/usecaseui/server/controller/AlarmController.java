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

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <告警rest控制器实现>
 * <功能详细描述>
 * @author  donghu
 * @version  [版本号, 2017年8月17日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Controller
@org.springframework.context.annotation.Configuration
@EnableAspectJAutoProxy
public class AlarmController
{


    private static final Logger logger = LoggerFactory.getLogger(AlarmController.class);


    @ResponseBody
    @RequestMapping(value = {"/alarm/getData"}, method = RequestMethod.GET , produces = "application/json")
    public String getAlarmData(HttpServletRequest request){
        String eventId = request.getParameter("eventId");
        String vfStatus = request.getParameter("vfStatus");
        String status = request.getParameter("status");

        return "";
    }

    @RequestMapping(value = { "/alarm/genCsv" } , method = RequestMethod.GET , produces = "application/json")
    public String generateCsvFile(HttpServletRequest request){
        String result = "{result:failure}";
        String ids = request.getParameter("ids");

        return result;
    }

    @RequestMapping(value = { "/alarm/updateStatus" } , method = RequestMethod.GET , produces = "application/json")
    public String updateStatus(HttpServletRequest request){
        return "";
    }




}
