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
package org.onap.usecaseui.server.service.lcm.domain.vfc;

import org.onap.usecaseui.server.service.lcm.domain.aai.bean.nsServiceRsp;
import org.onap.usecaseui.server.service.lcm.domain.vfc.beans.Csar;
import org.onap.usecaseui.server.service.lcm.domain.vfc.beans.DistributionResult;
import org.onap.usecaseui.server.service.lcm.domain.vfc.beans.Job;
import org.onap.usecaseui.server.service.lcm.domain.vfc.beans.JobStatus;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

public interface VfcService {

    @POST("/api/catalog/v1/nspackages")
    Call<DistributionResult> distributeNsPackage(@Body Csar csar);

    @POST("/api/catalog/v1/vnfpackages")
    Call<Job> distributeVnfPackage(@Body Csar csar);

    @GET("/api/catalog/v1/jobs/{jobId}")
    Call<JobStatus> getJobStatus(@Path("jobId") String jobId, @Query("responseId") String responseId);
    
    @GET("/api/nslcm/v1/jobs/{jobId}")
    Call<JobStatus> getNsLcmJobStatus(@Path("jobId") String jobId, @Query("responseId") String responseId);

    @DELETE("/api/catalog/v1/nspackages/{csarId}")
    Call<DistributionResult> deleteNsPackage(@Path("csarId") String csarId);

    @DELETE("/api/catalog/v1/vnfpackages/{csarId}")
    Call<Job> deleteVnfPackage(@Path("csarId") String csarId);
    
    @GET("/api/catalog/v1/nspackages")
    Call<ResponseBody> listNsTemplates();
    
    @POST("/api/parser/v1/parsernsd")
    Call<ResponseBody> fetchNsTemplateData(@Body RequestBody body);
    
    @POST("/api/parser/v1/parser")
    Call<ResponseBody> fetchTemplateInfo(@Body RequestBody body);
    
    @GET("/api/nslcm/v1/ns")
    Call<nsServiceRsp> getNetworkServiceInfo();
    
    @POST("/api/nslcm/v1/ns")
    Call<ResponseBody> createNetworkServiceInstance(@Body RequestBody body);
    
    @POST("/api/nslcm/v1/ns/{ns_instance_id}/instantiate")
    Call<ResponseBody> instantiateNetworkServiceInstance(@Body RequestBody body,@Path("ns_instance_id") String nsInstanceId);
    
    @DELETE("/api/nslcm/v1/ns/{ns_instance_id}")
    Call<ResponseBody> deleteNetworkServiceInstance(@Path("ns_instance_id") String nsInstanceId);
    
    @POST("/api/nslcm/v1/ns/{ns_instance_id}/terminate")
    Call<ResponseBody> terminateNetworkServiceInstance(@Path("ns_instance_id") String nsInstanceId,@Body RequestBody body);
    
    @POST("/api/nslcm/v1/ns/{ns_instance_id}/heal")
    Call<ResponseBody> healNetworkServiceInstance(@Path("ns_instance_id") String nsInstanceId,@Body RequestBody body);
    
    @POST("/api/nslcm/v1/ns/{ns_instance_id}/scale")
    Call<ResponseBody> scaleNetworkServiceInstance(@Path("ns_instance_id") String nsInstanceId,@Body RequestBody body);
    
    @GET("/api/nsd/v1/ns_descriptors")
    Call<ResponseBody> getNetworkServicePackages();
    
    @GET("/api/vnfpkgm/v1/vnf_packages")
    Call<ResponseBody> getVnfPackages();
    
    @GET("/api/nsd/v1/pnf_descriptors")
    Call<ResponseBody> getPnfPackages();
    
    @POST("/api/nsd/v1/ns_descriptors")
    Call<ResponseBody> createNetworkServiceData(@Body RequestBody body);
    
    @POST("/api/vnfpkgm/v1/vnf_packages")
    Call<ResponseBody> createVnfData(@Body RequestBody body);
    
    @POST("/api/nsd/v1/pnf_descriptors")
    Call<ResponseBody> createPnfData(@Body RequestBody body);
    
    @GET("/api/nsd/v1/ns_descriptors/{nsdInfoId}/nsd_content")
    Call<ResponseBody> downLoadNsPackage(@Path("nsdInfoId") String nsdInfoId);
    
    @GET("/api/nsd/v1/ns_descriptors/{nsdInfoId}")
    Call<ResponseBody> getNsdInfo(@Path("nsdInfoId") String nsdInfoId);
    
    @GET("/api/vnfpkgm/v1/vnf_packages/{vnfPkgId}")
    Call<ResponseBody> getVnfInfo(@Path("vnfPkgId") String vnfPkgId);
    
    @GET("/api/nsd/v1/pnf_descriptors/{pnfdInfoId}")
    Call<ResponseBody> getPnfInfo(@Path("pnfdInfoId") String pnfdInfoId);
    
    @GET("/api/nsd/v1/pnf_descriptors/{pnfdInfoId}/pnfd_content")
    Call<ResponseBody> downLoadPnfPackage(@Path("pnfdInfoId") String pnfdInfoId);
    
    @GET("/api/vnfpkgm/v1/vnf_packages/{vnfPkgId}/package_content")
    Call<ResponseBody> downLoadVnfPackage(@Path("vnfPkgId") String vnfPkgId);
    
    @DELETE("/api/nsd/v1/ns_descriptors/{nsdInfoId}")
    Call<ResponseBody> deleteNsdPackage(@Path("nsdInfoId") String nsdInfoId);
    
    @DELETE("/api/vnfpkgm/v1/vnf_packages/{vnfPkgId}")
    Call<ResponseBody> deleteVnfdPackage(@Path("vnfPkgId") String vnfPkgId);
    
    @DELETE("/api/nsd/v1/pnf_descriptors/{pnfdInfoId}")
    Call<ResponseBody> deletePnfdPackage(@Path("pnfdInfoId") String pnfdInfoId);
    
    @GET("/api/nslcm/v1/ns/vnfs/{vnfinstid}")
    Call<ResponseBody> getVnfInfoById(@Path("vnfinstid") String vnfinstid);

    @GET("/api/catalog/v1/service_packages")
    Call<ResponseBody> servicePackages();
    
    @GET("/api/catalog/v1/service_packages/{csarId}")
    Call<ResponseBody> servicePackages(@Path("csarId") String csarId);
    
    @POST("/api/catalog/v1/service_packages")
    Call<ResponseBody> servicePackages(@Body RequestBody body);
    
    @DELETE("/api/catalog/v1/service_packages/{csarId}")
    Call<ResponseBody> deleteServicePackages(@Path("csarId") String csarId);
}
