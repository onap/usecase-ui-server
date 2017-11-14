/**
 * Copyright 2016-2017 ZTE Corporation.
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
package org.onap.usecaseui.server.controller.lcm;

import org.onap.usecaseui.server.bean.lcm.VfNsPackageInfo;
import org.onap.usecaseui.server.service.lcm.PackageDistributionService;
import org.onap.usecaseui.server.service.lcm.domain.vfc.beans.Csar;
import org.onap.usecaseui.server.service.lcm.domain.vfc.beans.DistributionResult;
import org.onap.usecaseui.server.service.lcm.domain.vfc.beans.Job;
import org.onap.usecaseui.server.service.lcm.domain.vfc.beans.JobStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
@org.springframework.context.annotation.Configuration
@EnableAspectJAutoProxy
public class PackageDistributionController {

    private static final Logger logger = LoggerFactory.getLogger(PackageDistributionController.class);

    @Resource(name="PackageDistributionService")
    private PackageDistributionService packageDistributionService;

    public void setPackageDistributionService(PackageDistributionService packageDistributionService) {
        this.packageDistributionService = packageDistributionService;
    }

    @ResponseBody
    @RequestMapping(value = {"/uui-lcm/vf-ns-packages"}, method = RequestMethod.GET , produces = "application/json")
    public VfNsPackageInfo retrievePackageInfo(){
        return packageDistributionService.retrievePackageInfo();
    }

    @ResponseBody
    @RequestMapping(value = {"/uui-lcm/ns-packages"}, method = RequestMethod.POST , produces = "application/json")
    public DistributionResult distributeNsPackage(@RequestBody Csar csar){
        return packageDistributionService.postNsPackage(csar);
    }

    @ResponseBody
    @RequestMapping(value = {"/uui-lcm/vf-packages"}, method = RequestMethod.POST , produces = "application/json")
    public Job distributeVfPackage(@RequestBody Csar csar){
        return packageDistributionService.postVfPackage(csar);
    }

    @ResponseBody
    @RequestMapping(value = {"/uui-lcm/jobs/{jobId}"}, method = RequestMethod.GET , produces = "application/json")
    public JobStatus getJobStatus(@PathVariable(value="jobId") String jobId, HttpServletRequest request){
        String responseId = request.getParameter("responseId");
        return packageDistributionService.getJobStatus(jobId, responseId);
    }

    @ResponseBody
    @RequestMapping(value = {"/uui-lcm/ns-packages/{casrId}"}, method = RequestMethod.DELETE , produces = "application/json")
    public DistributionResult deleteNsPackage(@PathVariable("casrId") String casrId){
        return packageDistributionService.deleteNsPackage(casrId);
    }

    @ResponseBody
    @RequestMapping(value = {"/uui-lcm/vf-packages/{casrId}"}, method = RequestMethod.DELETE , produces = "application/json")
    public Job deleteVfPackage(@PathVariable("casrId") String casrId){
        return packageDistributionService.deleteVfPackage(casrId);
    }
}
