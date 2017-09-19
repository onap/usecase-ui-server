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
