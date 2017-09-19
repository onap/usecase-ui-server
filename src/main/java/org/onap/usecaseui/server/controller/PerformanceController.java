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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@Configuration
@EnableAspectJAutoProxy
public class PerformanceController {

    private Logger looger = LoggerFactory.getLogger(PerformanceController.class);

    @ResponseBody
    @RequestMapping(value = {"/pro/getData"},method = RequestMethod.GET, produces = "application/json")
    public String getPerformanceData(HttpServletRequest request){

        return "";
    }

    @RequestMapping(value = {"/pro/genCsv"}, method = RequestMethod.GET, produces = "application/json")
    public String generateCsvFile(HttpServletRequest request){
        String[] headers = new String[]{};
        return "";
    }

    @RequestMapping(value = {"/pro/genDia"}, method = RequestMethod.GET, produces = "application/json")
    public String generateDiagram(HttpServletRequest request){

        return "";
    }


}
