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
package org.onap.usecaseui.server.service.lcm;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

import org.onap.usecaseui.server.bean.lcm.VfNsPackageInfo;
import org.onap.usecaseui.server.service.lcm.domain.sdc.bean.SDCServiceTemplate;
import org.onap.usecaseui.server.service.lcm.domain.sdc.bean.Vnf;
import org.onap.usecaseui.server.service.lcm.domain.vfc.beans.Csar;
import org.onap.usecaseui.server.service.lcm.domain.vfc.beans.DistributionResult;
import org.onap.usecaseui.server.service.lcm.domain.vfc.beans.Job;
import org.onap.usecaseui.server.service.lcm.domain.vfc.beans.JobStatus;

import com.alibaba.fastjson.JSONObject;

public interface PackageDistributionService {

    VfNsPackageInfo retrievePackageInfo();
    
    List<SDCServiceTemplate> sdcNsPackageInfo();
    
    List<Vnf> sdcVfPackageInfo();

    DistributionResult postNsPackage(Csar csar);

    Job postVfPackage(Csar csar);

    JobStatus getJobStatus(String jobId, String responseId);
    
    JobStatus getNsLcmJobStatus(String serviceInstanceId,String jobId, String responseId,String operationType);

    DistributionResult deleteNsPackage(String csarId);

    Job deleteVfPackage(String csarId);
    
    String listNsTemplates();
    
    String fetchNsTemplateData(HttpServletRequest request);
    
    JSONObject fetchCCVPNTemplateData(HttpServletRequest request, String csarId);
    
    String getVnfPackages();
    
    String getNetworkServicePackages();
    
    String getPnfPackages();
    
    String createNetworkServiceData(HttpServletRequest request);
    
    String createVnfData(HttpServletRequest request);
    
    String createPnfData(HttpServletRequest request);
    
    String getNsdInfo(String nsdInfoId);
    
    String getVnfInfo(String vnfPkgId);
    
    String getPnfInfo(String pnfdInfoId);
    
    String downLoadNsPackage(String nsdInfoId);
    
    String downLoadPnfPackage(String pnfdInfoId);
    
    String downLoadVnfPackage(String vnfPkgId);
    
    String deleteNsdPackage(String nsdInfoId);
    
    String deleteVnfPackage(String vnfPkgId);
    
    String deletePnfPackage(String pnfdInfoId);
    
    List<String> getNetworkServiceInfo();
    
    String createNetworkServiceInstance(HttpServletRequest request);
    
    String instantiateNetworkServiceInstance(HttpServletRequest request ,String serviceInstanceId);
    
    String deleteNetworkServiceInstance(String nsInstanceId);
    
    String terminateNetworkServiceInstance(HttpServletRequest request,String networkServiceInstanceId);
    
    String healNetworkServiceInstance(HttpServletRequest request,String networkServiceInstanceId);
    
    String scaleNetworkServiceInstance(HttpServletRequest request,String networkServiceInstanceId);
    
    String getVnfInfoById(String vnfinstid);
}
