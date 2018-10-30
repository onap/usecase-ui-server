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

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.onap.usecaseui.server.bean.ServiceBean;
import org.onap.usecaseui.server.bean.lcm.VfNsPackageInfo;
import org.onap.usecaseui.server.service.lcm.PackageDistributionService;
import org.onap.usecaseui.server.service.lcm.ServiceLcmService;
import org.onap.usecaseui.server.service.lcm.domain.sdc.bean.SDCServiceTemplate;
import org.onap.usecaseui.server.service.lcm.domain.sdc.bean.Vnf;
import org.onap.usecaseui.server.service.lcm.domain.vfc.beans.Csar;
import org.onap.usecaseui.server.service.lcm.domain.vfc.beans.DistributionResult;
import org.onap.usecaseui.server.service.lcm.domain.vfc.beans.Job;
import org.onap.usecaseui.server.service.lcm.domain.vfc.beans.JobStatus;
import org.onap.usecaseui.server.util.UuiCommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

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
    
    @Resource(name="ServiceLcmService")
    private ServiceLcmService serviceLcmService;

    public void setServiceLcmService(ServiceLcmService serviceLcmService) {
        this.serviceLcmService = serviceLcmService;
    }
    
    @ResponseBody
    @RequestMapping(value = {"/uui-lcm/vf-ns-packages"}, method = RequestMethod.GET , produces = "application/json")
    public VfNsPackageInfo retrievePackageInfo(){
        return packageDistributionService.retrievePackageInfo();
    }
    
    @ResponseBody
    @RequestMapping(value = {"/uui-lcm/sdc-ns-packages"}, method = RequestMethod.GET , produces = "application/json")
    public List<SDCServiceTemplate> sdcNsPackageInfo(){
        return packageDistributionService.sdcNsPackageInfo();
    }
    
    @ResponseBody
    @RequestMapping(value = {"/uui-lcm/sdc-vf-packages"}, method = RequestMethod.GET , produces = "application/json")
    public List<Vnf> sdcVfPackageInfo(){
        return packageDistributionService.sdcVfPackageInfo();
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
    @RequestMapping(value = {"/uui-lcm/jobs/getNsLcmJobStatus/{jobId}"}, method = RequestMethod.GET , produces = "application/json")
    public JobStatus getNsLcmJobStatus(@PathVariable(value="jobId") String jobId, HttpServletRequest request){
        String responseId = request.getParameter("responseId");
        return packageDistributionService.getNsLcmJobStatus(jobId, responseId);
    }
    
    @ResponseBody
    @RequestMapping(value = {"/uui-lcm/vf-packages/{casrId}"}, method = RequestMethod.DELETE , produces = "application/json")
    public Job deleteVfPackage(@PathVariable("casrId") String casrId){
        return packageDistributionService.deleteVfPackage(casrId);
    }
    
    @RequestMapping(value = {"/uui-lcm/fetchNsTemplateData}"}, method = RequestMethod.GET , produces = "application/json")
    public String fetchNsTemplateData(HttpServletRequest request){
        return packageDistributionService.fetchNsTemplateData(request);
    }
    
    @RequestMapping(value = {"/uui-lcm/listNsTemplates}"}, method = RequestMethod.GET , produces = "application/json")
    public String listNsTemplates(){
        return packageDistributionService.listNsTemplates();
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
    public List<String> getNetworkServiceInfo(){
        return packageDistributionService.getNetworkServiceInfo();
    }
    
    @RequestMapping(value = {"/uui-lcm/createNetworkServiceInstance}"}, method = RequestMethod.POST , produces = "application/json")
    public String createNetworkServiceInstance(HttpServletRequest request){
        return packageDistributionService.createNetworkServiceInstance(request);
    }
    
    @RequestMapping(value = {"/uui-lcm/deleteNetworkServiceInstance}"}, method = RequestMethod.DELETE , produces = "application/json")
    public String deleteNetworkServiceInstance(@RequestParam String ns_instance_id){
        return packageDistributionService.deleteNetworkServiceInstance(ns_instance_id);
    }
    
    @RequestMapping(value = {"/uui-lcm/instantiateNetworkServiceInstance}"}, method = RequestMethod.POST , produces = "application/json")
    public String instantiateNetworkServiceInstance(HttpServletRequest request){
    	String customerId = request.getParameter("customerId");
    	String serviceType = request.getParameter("serviceType");
    	String serviceDomain = request.getParameter("serviceDomain");
    	String ns_instance_id = request.getParameter("ns_instance_id");
    	ServiceBean serviceBean = new ServiceBean(UuiCommonUtil.getUUID(),ns_instance_id,customerId,serviceType,serviceDomain,null,null,null);
    	serviceLcmService.saveOrUpdateServiceBean(serviceBean);
        return packageDistributionService.instantiateNetworkServiceInstance(request,ns_instance_id);
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
