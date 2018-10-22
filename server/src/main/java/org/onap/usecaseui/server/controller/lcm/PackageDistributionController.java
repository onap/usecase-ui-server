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

@RestController
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
    
    @RequestMapping(value = {"/uui-lcm/ns-packages}"}, method = RequestMethod.GET , produces = "application/json")
    public String getNsPackages(){
        return packageDistributionService.getNetworkServicePackages();
    }
    
    @RequestMapping(value = {"/uui-lcm/vnf-packages}"}, method = RequestMethod.GET , produces = "application/json")
    public String getVnfPackages(){
        return packageDistributionService.getVnfPackages();
    }
    
    @RequestMapping(value = {"/uui-lcm/pnf-packages}"}, method = RequestMethod.GET , produces = "application/json")
    public String getPnfPackages(){
        return packageDistributionService.getPnfPackages();
    }
    
    @RequestMapping(value = {"/uui-lcm/createNetworkServiceData}"}, method = RequestMethod.POST , produces = "application/json")
    public String createNetworkServiceData(HttpServletRequest request){
        return packageDistributionService.createNetworkServiceData(request);
    }
    
    @RequestMapping(value = {"/uui-lcm/createVnfData}"}, method = RequestMethod.POST , produces = "application/json")
    public String createVnfData(HttpServletRequest request){
        return packageDistributionService.createVnfData(request);
    }
    
    @RequestMapping(value = {"/uui-lcm/createPnfData}"}, method = RequestMethod.POST , produces = "application/json")
    public String createPnfData(HttpServletRequest request){
        return packageDistributionService.createPnfData(request);
    }
    
    @RequestMapping(value = {"/uui-lcm/getNsdInfo}"}, method = RequestMethod.GET , produces = "application/json")
    public String getNsdInfo(@RequestParam String nsdInfoId){
        return packageDistributionService.getNsdInfo(nsdInfoId);
    }
    
    @RequestMapping(value = {"/uui-lcm/getVnfInfo}"}, method = RequestMethod.GET , produces = "application/json")
    public String getVnfInfo(@RequestParam String vnfPkgId){
        return packageDistributionService.getVnfInfo(vnfPkgId);
    }
    
    @RequestMapping(value = {"/uui-lcm/getPnfInfo}"}, method = RequestMethod.GET , produces = "application/json")
    public String getPnfInfo(@RequestParam String pnfdInfoId){
        return packageDistributionService.getPnfInfo(pnfdInfoId);
    }
    
    @RequestMapping(value = {"/uui-lcm/downLoadNsPackage}"}, method = RequestMethod.GET , produces = "application/json")
    public String downLoadNsPackage(@RequestParam String nsdInfoId){
        return packageDistributionService.downLoadNsPackage(nsdInfoId);
    }
    
    @RequestMapping(value = {"/uui-lcm/downLoadPnfPackage}"}, method = RequestMethod.GET , produces = "application/json")
    public String downLoadPnfPackage(@RequestParam String pnfdInfoId){
        return packageDistributionService.downLoadPnfPackage(pnfdInfoId);
    }
    
    @RequestMapping(value = {"/uui-lcm/downLoadVnfPackage}"}, method = RequestMethod.GET , produces = "application/json")
    public String downLoadVnfPackage(@RequestParam String vnfPkgId){
        return packageDistributionService.downLoadVnfPackage(vnfPkgId);
    }
    
    @RequestMapping(value = {"/uui-lcm/deleteNsdPackage}"}, method = RequestMethod.GET , produces = "application/json")
    public String deleteNsdPackage(@RequestParam String nsdInfoId){
        return packageDistributionService.deleteNsdPackage(nsdInfoId);
    }
    
    @RequestMapping(value = {"/uui-lcm/deleteVnfPackage}"}, method = RequestMethod.GET , produces = "application/json")
    public String deleteVnfPackage(@RequestParam String vnfPkgId){
        return packageDistributionService.deleteVnfPackage(vnfPkgId);
    }
    
    @RequestMapping(value = {"/uui-lcm/deletePnfPackage}"}, method = RequestMethod.GET , produces = "application/json")
    public String deletePnfPackage(@RequestParam String pnfdInfoId){
        return packageDistributionService.deletePnfPackage(pnfdInfoId);
    }
    
    @RequestMapping(value = {"/uui-lcm/getNetworkServiceInfo}"}, method = RequestMethod.GET , produces = "application/json")
    public String getNetworkServiceInfo(){
        return packageDistributionService.getNetworkServiceInfo();
    }
    
    @RequestMapping(value = {"/uui-lcm/getNetworkServiceInfo}"}, method = RequestMethod.POST , produces = "application/json")
    public String createNetworkServiceInstance(HttpServletRequest request){
        return packageDistributionService.createNetworkServiceInstance(request);
    }
    
    @RequestMapping(value = {"/uui-lcm/getNetworkServiceInfo}"}, method = RequestMethod.DELETE , produces = "application/json")
    public String deleteNetworkServiceInstance(@RequestParam String ns_instance_id){
        return packageDistributionService.deleteNetworkServiceInstance(ns_instance_id);
    }
    
    @RequestMapping(value = {"/uui-lcm/terminateNetworkServiceInstance}"}, method = RequestMethod.POST , produces = "application/json")
    public String terminateNetworkServiceInstance(HttpServletRequest request,@RequestParam String ns_instance_id){
        return packageDistributionService.terminateNetworkServiceInstance(request,ns_instance_id);
    }
    
    @RequestMapping(value = {"/uui-lcm/healNetworkServiceInstance}"}, method = RequestMethod.POST , produces = "application/json")
    public String healNetworkServiceInstance(HttpServletRequest request,@RequestParam String ns_instance_id){
        return packageDistributionService.healNetworkServiceInstance(request,ns_instance_id);
    }
    
    @RequestMapping(value = {"/uui-lcm/scaleNetworkServiceInstance}"}, method = RequestMethod.POST , produces = "application/json")
    public String scaleNetworkServiceInstance(HttpServletRequest request,@RequestParam String ns_instance_id){
        return packageDistributionService.scaleNetworkServiceInstance(request,ns_instance_id);
    }
}
