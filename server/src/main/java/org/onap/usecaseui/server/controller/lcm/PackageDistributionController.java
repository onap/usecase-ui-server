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

import java.text.ParseException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.onap.usecaseui.server.bean.ServiceBean;
import org.onap.usecaseui.server.bean.ServiceInstanceOperations;
import org.onap.usecaseui.server.bean.lcm.VfNsPackageInfo;
import org.onap.usecaseui.server.constant.CommonConstant;
import org.onap.usecaseui.server.service.lcm.PackageDistributionService;
import org.onap.usecaseui.server.service.lcm.ServiceLcmService;
import org.onap.usecaseui.server.service.lcm.domain.sdc.bean.SDCServiceTemplate;
import org.onap.usecaseui.server.service.lcm.domain.sdc.bean.Vnf;
import org.onap.usecaseui.server.service.lcm.domain.vfc.beans.Csar;
import org.onap.usecaseui.server.service.lcm.domain.vfc.beans.DistributionResult;
import org.onap.usecaseui.server.service.lcm.domain.vfc.beans.Job;
import org.onap.usecaseui.server.service.lcm.domain.vfc.beans.JobStatus;
import org.onap.usecaseui.server.util.DateUtils;
import org.onap.usecaseui.server.util.UuiCommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

@RestController
@CrossOrigin(origins="*")
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
    @GetMapping(value = {"/uui-lcm/vf-ns-packages"}, produces = "application/json")
    public VfNsPackageInfo retrievePackageInfo(){
        return packageDistributionService.retrievePackageInfo();
    }
    
    @ResponseBody
    @GetMapping(value = {"/uui-lcm/sdc-ns-packages"}, produces = "application/json")
    public List<SDCServiceTemplate> sdcNsPackageInfo(){
        return packageDistributionService.sdcNsPackageInfo();
    }
    
    @ResponseBody
    @GetMapping(value = {"/uui-lcm/sdc-vf-packages"}, produces = "application/json")
    public List<Vnf> sdcVfPackageInfo(){
        return packageDistributionService.sdcVfPackageInfo();
    }
    
    @ResponseBody
    @PostMapping(value = {"/uui-lcm/ns-packages"}, produces = "application/json")
    public DistributionResult distributeNsPackage(@RequestBody Csar csar){
        return packageDistributionService.postNsPackage(csar);
    }

    @ResponseBody
    @PostMapping(value = {"/uui-lcm/vf-packages"}, produces = "application/json")
    public Job distributeVfPackage(@RequestBody Csar csar){
        return packageDistributionService.postVfPackage(csar);
    }

    @ResponseBody
    @GetMapping(value = {"/uui-lcm/jobs/{jobId}"}, produces = "application/json")
    public JobStatus getJobStatus(@PathVariable(value="jobId") String jobId, HttpServletRequest request){
        String responseId = request.getParameter("responseId");
        return packageDistributionService.getJobStatus(jobId, responseId);
    }

    @ResponseBody
    @DeleteMapping(value = {"/uui-lcm/ns-packages/{casrId}"}, produces = "application/json")
    public DistributionResult deleteNsPackage(@PathVariable("casrId") String casrId){
        return packageDistributionService.deleteNsPackage(casrId);
    }
    
    @ResponseBody
    @GetMapping(value = {"/uui-lcm/jobs/getNsLcmJobStatus/{jobId}"}, produces = "application/json")
    public JobStatus getNsLcmJobStatus(@PathVariable(value="jobId") String jobId, HttpServletRequest request){
        String responseId = request.getParameter("responseId");
        String serviceInstanceId = request.getParameter("serviceInstanceId");
        String operationType = request.getParameter("operationType");
        JobStatus jobStatus = packageDistributionService.getNsLcmJobStatus(serviceInstanceId,jobId, responseId,operationType);
        if(UuiCommonUtil.isNotNullOrEmpty(jobStatus)&&UuiCommonUtil.isNotNullOrEmpty(jobStatus.getResponseDescriptor())&&UuiCommonUtil.isNotNullOrEmpty(jobStatus.getResponseDescriptor().getProgress())){
            String processNum = jobStatus.getResponseDescriptor().getProgress();
            String operationResult = CommonConstant.IN_PROGRESS_CODE;
            if(Integer.parseInt(processNum)==100){
                operationResult = CommonConstant.SUCCESS_CODE;
            }else if(Integer.parseInt(processNum)>100){
                operationResult= CommonConstant.FAIL_CODE;
            }
            serviceLcmService.updateServiceInstanceOperation(serviceInstanceId,operationType,processNum,operationResult);
        }
        return jobStatus;
    
    }
    
    @ResponseBody
    @DeleteMapping(value = {"/uui-lcm/vf-packages/{casrId}"}, produces = "application/json")
    public Job deleteVfPackage(@PathVariable("casrId") String casrId){
        return packageDistributionService.deleteVfPackage(casrId);
    }
    
    @PostMapping(value = {"/uui-lcm/fetchNsTemplateData"}, produces = "application/json")
    public String fetchNsTemplateData(HttpServletRequest request){
        return packageDistributionService.fetchNsTemplateData(request);
    }
    
    @PostMapping(value={"/uui-lcm/fetchCCVPNTemplateData/{csarId}"},produces="application/json")
    public JSONObject fetchCCVPNTemplateData(HttpServletRequest request, @PathVariable String csarId){
        JSONObject model = (JSONObject) packageDistributionService.fetchCCVPNTemplateData(request, csarId).get("result");
        return model;
    }
    @GetMapping(value = {"/uui-lcm/listNsTemplates"}, produces = "application/json")
    public String listNsTemplates(){
        return packageDistributionService.listNsTemplates();
    }
    
    @GetMapping(value = {"/uui-lcm/ns-packages"}, produces = "application/json")
    public String getNsPackages(){
        return packageDistributionService.getNetworkServicePackages();
    }
    
    @GetMapping(value = {"/uui-lcm/vnf-packages"}, produces = "application/json")
    public String getVnfPackages(){
        return packageDistributionService.getVnfPackages();
    }
    
    @GetMapping(value = {"/uui-lcm/pnf-packages"}, produces = "application/json")
    public String getPnfPackages(){
        return packageDistributionService.getPnfPackages();
    }
    
    @PostMapping(value = {"/uui-lcm/createNetworkServiceData"}, produces = "application/json")
    public String createNetworkServiceData(HttpServletRequest request){
        return packageDistributionService.createNetworkServiceData(request);
    }
    
    @PostMapping(value = {"/uui-lcm/createVnfData"}, produces = "application/json")
    public String createVnfData(HttpServletRequest request){
        return packageDistributionService.createVnfData(request);
    }
    
    @PostMapping(value = {"/uui-lcm/createPnfData"}, produces = "application/json")
    public String createPnfData(HttpServletRequest request){
        return packageDistributionService.createPnfData(request);
    }
    
    @GetMapping(value = {"/uui-lcm/getNsdInfo"}, produces = "application/json")
    public String getNsdInfo(@RequestParam String nsdInfoId){
        return packageDistributionService.getNsdInfo(nsdInfoId);
    }
    
    @GetMapping(value = {"/uui-lcm/getVnfInfo"}, produces = "application/json")
    public String getVnfInfo(@RequestParam String vnfPkgId){
        return packageDistributionService.getVnfInfo(vnfPkgId);
    }
    
    @GetMapping(value = {"/uui-lcm/getPnfInfo"}, produces = "application/json")
    public String getPnfInfo(@RequestParam String pnfdInfoId){
        return packageDistributionService.getPnfInfo(pnfdInfoId);
    }
    
    @GetMapping(value = {"/uui-lcm/downLoadNsPackage"}, produces = "application/json")
    public String downLoadNsPackage(@RequestParam String nsdInfoId){
        return packageDistributionService.downLoadNsPackage(nsdInfoId);
    }
    
    @GetMapping(value = {"/uui-lcm/downLoadPnfPackage"}, produces = "application/json")
    public String downLoadPnfPackage(@RequestParam String pnfdInfoId){
        return packageDistributionService.downLoadPnfPackage(pnfdInfoId);
    }
    
    @GetMapping(value = {"/uui-lcm/downLoadVnfPackage"}, produces = "application/json")
    public String downLoadVnfPackage(@RequestParam String vnfPkgId){
        return packageDistributionService.downLoadVnfPackage(vnfPkgId);
    }
    
    @DeleteMapping(value = {"/uui-lcm/deleteNsdPackage"}, produces = "application/json")
    public String deleteNsdPackage(@RequestParam String nsdInfoId){
        return packageDistributionService.deleteNsdPackage(nsdInfoId);
    }
    
    @DeleteMapping(value = {"/uui-lcm/deleteVnfPackage"}, produces = "application/json")
    public String deleteVnfPackage(@RequestParam String vnfPkgId){
        return packageDistributionService.deleteVnfPackage(vnfPkgId);
    }
    
    @DeleteMapping(value = {"/uui-lcm/deletePnfPackage"} , produces = "application/json")
    public String deletePnfPackage(@RequestParam String pnfdInfoId){
        return packageDistributionService.deletePnfPackage(pnfdInfoId);
    }
    
    @GetMapping(value = {"/uui-lcm/getNetworkServiceInfo"} , produces = "application/json")
    public List<String> getNetworkServiceInfo(){
        return packageDistributionService.getNetworkServiceInfo();
    }
    
    @PostMapping(value = {"/uui-lcm/createNetworkServiceInstance"}, produces = "application/json")
    public String createNetworkServiceInstance(HttpServletRequest request){
        return packageDistributionService.createNetworkServiceInstance(request);
    }
    
    @DeleteMapping(value = {"/uui-lcm/deleteNetworkServiceInstance"}, produces = "application/json")
    public String deleteNetworkServiceInstance(@RequestParam String ns_instance_id){
        return packageDistributionService.deleteNetworkServiceInstance(ns_instance_id);
    }
    
    @PostMapping(value = {"/uui-lcm/instantiateNetworkServiceInstance"} , produces = "application/json")
    public String instantiateNetworkServiceInstance(HttpServletRequest request) throws ParseException{
        String customerId = request.getParameter("customerId");
        String serviceType = request.getParameter("serviceType");
        String serviceDomain = request.getParameter("serviceDomain");
        String ns_instance_id = request.getParameter("ns_instance_id");
        String object = packageDistributionService.instantiateNetworkServiceInstance(request,ns_instance_id);
        JSONObject jobObject = JSONObject.parseObject(object);
        String jobId = jobObject.getString("jobId");
        ServiceBean serviceBean = new ServiceBean(UuiCommonUtil.getUUID(),ns_instance_id,customerId,serviceType,serviceDomain,null,null);
        ServiceInstanceOperations serviceOpera = new ServiceInstanceOperations(ns_instance_id,jobId, CommonConstant.CREATING_CODE,"0", CommonConstant.IN_PROGRESS_CODE,DateUtils.dateToString(DateUtils.now()),null);
        serviceLcmService.saveOrUpdateServiceInstanceOperation(serviceOpera);
        serviceLcmService.saveOrUpdateServiceBean(serviceBean);
        return object;
    }
    
    @PostMapping(value = {"/uui-lcm/terminateNetworkServiceInstance"} , produces = "application/json")
    public String terminateNetworkServiceInstance(HttpServletRequest request,@RequestParam String ns_instance_id) throws ParseException{
        String result = packageDistributionService.terminateNetworkServiceInstance(request,ns_instance_id);
        String jobId = "";
        if(UuiCommonUtil.isNotNullOrEmpty(result)){
            JSONObject jobIdObject = JSONObject.parseObject(result);
            jobId = jobIdObject.getString("jobId");
        }
        ServiceInstanceOperations serviceOpera = new ServiceInstanceOperations(ns_instance_id,jobId, CommonConstant.DELETING_CODE,"0", CommonConstant.IN_PROGRESS_CODE,DateUtils.dateToString(DateUtils.now()),null);
        serviceLcmService.saveOrUpdateServiceInstanceOperation(serviceOpera);
        return result;
    }
    
    @PostMapping(value = {"/uui-lcm/healNetworkServiceInstance"} , produces = "application/json")
    public String healNetworkServiceInstance(HttpServletRequest request,@RequestParam String ns_instance_id) throws ParseException{
        String result= packageDistributionService.healNetworkServiceInstance(request,ns_instance_id);
        String jobId = "";
        if(UuiCommonUtil.isNotNullOrEmpty(result)){
            JSONObject jobIdObject = JSONObject.parseObject(result);
            jobId = jobIdObject.getString("jobId");
        }
        ServiceInstanceOperations serviceOpera = new ServiceInstanceOperations(ns_instance_id,jobId, CommonConstant.HEALING_CODE,"0", CommonConstant.IN_PROGRESS_CODE,DateUtils.dateToString(DateUtils.now()),null);
        serviceLcmService.saveOrUpdateServiceInstanceOperation(serviceOpera);
        return result;
    }
    
    @PostMapping(value = {"/uui-lcm/scaleNetworkServiceInstance"} , produces = "application/json")
    public String scaleNetworkServiceInstance(HttpServletRequest request,@RequestParam String ns_instance_id){
        return packageDistributionService.scaleNetworkServiceInstance(request,ns_instance_id);
    }
    
    @GetMapping(value = {"/uui-lcm/VnfInfo/{vnfinstid}"} , produces = "application/json")
    public String getVnfInfoById(@PathVariable String vnfinstid){
        return packageDistributionService.getVnfInfoById(vnfinstid);
    }
}
