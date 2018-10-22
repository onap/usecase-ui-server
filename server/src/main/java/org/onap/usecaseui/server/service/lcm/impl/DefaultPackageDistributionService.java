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
package org.onap.usecaseui.server.service.lcm.impl;

import org.onap.usecaseui.server.bean.lcm.VfNsPackageInfo;
import org.onap.usecaseui.server.constant.Constant;
import org.onap.usecaseui.server.service.lcm.PackageDistributionService;
import org.onap.usecaseui.server.service.lcm.domain.sdc.SDCCatalogService;
import org.onap.usecaseui.server.service.lcm.domain.sdc.bean.SDCServiceTemplate;
import org.onap.usecaseui.server.service.lcm.domain.sdc.bean.Vnf;
import org.onap.usecaseui.server.service.lcm.domain.sdc.exceptions.SDCCatalogException;
import org.onap.usecaseui.server.service.lcm.domain.vfc.VfcService;
import org.onap.usecaseui.server.service.lcm.domain.vfc.beans.Csar;
import org.onap.usecaseui.server.service.lcm.domain.vfc.beans.DistributionResult;
import org.onap.usecaseui.server.service.lcm.domain.vfc.beans.Job;
import org.onap.usecaseui.server.service.lcm.domain.vfc.beans.JobStatus;
import org.onap.usecaseui.server.service.lcm.domain.vfc.exceptions.VfcException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Service;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Response;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import static org.onap.usecaseui.server.service.lcm.domain.sdc.consts.SDCConsts.*;
import static org.onap.usecaseui.server.util.RestfulServices.create;
import static org.onap.usecaseui.server.util.RestfulServices.extractBody;

@Service("PackageDistributionService")
@org.springframework.context.annotation.Configuration
@EnableAspectJAutoProxy
public class DefaultPackageDistributionService implements PackageDistributionService {

    private static final Logger logger = LoggerFactory.getLogger(DefaultPackageDistributionService.class);

    private SDCCatalogService sdcCatalogService;

    private VfcService vfcService;

    public DefaultPackageDistributionService() {
        this(create(SDCCatalogService.class), create(VfcService.class));
    }

    public DefaultPackageDistributionService(SDCCatalogService sdcCatalogService, VfcService vfcService) {
        this.sdcCatalogService = sdcCatalogService;
        this.vfcService = vfcService;
    }

    @Override
    public VfNsPackageInfo retrievePackageInfo() {
        try {
            List<SDCServiceTemplate> nsTemplate = getNsTemplate();
            List<Vnf> vnf = getVFResource();
            return new VfNsPackageInfo(nsTemplate, vnf);
        } catch (IOException e) {
            throw new SDCCatalogException("SDC Service is not available!", e);
        }
    }

    private List<Vnf> getVFResource() throws IOException {
        Response<List<Vnf>> response = sdcCatalogService.listResources(RESOURCETYPE_VF).execute();
        if (response.isSuccessful()) {
            return response.body();
        } else {
            logger.info(String.format("Can not get VF resources[code=%s, message=%s]", response.code(), response.message()));
            return Collections.emptyList();
        }
    }

    private List<SDCServiceTemplate> getNsTemplate() throws IOException {
        Response<List<SDCServiceTemplate>> response = sdcCatalogService.listServices(CATEGORY_NS, DISTRIBUTION_STATUS_DISTRIBUTED).execute();
        if (response.isSuccessful()) {
            return response.body();
        } else {
            logger.info(String.format("Can not get NS services[code=%s, message=%s]", response.code(), response.message()));
            return Collections.emptyList();
        }
    }

    @Override
    public DistributionResult postNsPackage(Csar csar) {
        try {
            Response<DistributionResult> response = vfcService.distributeNsPackage(csar).execute();
            if (response.isSuccessful()) {
                return response.body();
            } else {
                logger.info(String.format("Can not post NS packages[code=%s, message=%s]", response.code(), response.message()));
                throw new VfcException("VFC service is not available!");
            }
        } catch (IOException e) {
            throw new VfcException("VFC service is not available!", e);
        }
    }

    @Override
    public Job postVfPackage(Csar csar) {
        try {
            Response<Job> response = vfcService.distributeVnfPackage(csar).execute();
            if (response.isSuccessful()) {
                return response.body();
            } else {
                logger.info(String.format("Can not get VF packages[code=%s, message=%s]", response.code(), response.message()));
                throw new VfcException("VFC service is not available!");
            }
        } catch (IOException e) {
            throw new VfcException("VFC service is not available!", e);
        }
    }

    @Override
    public JobStatus getJobStatus(String jobId, String responseId) {
        try {
            Response<JobStatus> response = vfcService.getJobStatus(jobId, responseId).execute();
            if (response.isSuccessful()) {
                return response.body();
            } else {
                logger.info(String.format("Can not get Job status[code=%s, message=%s]", response.code(), response.message()));
                throw new VfcException("VFC service is not available!");
            }
        } catch (IOException e) {
            throw new VfcException("VFC service is not available!", e);
        }
    }

    @Override
    public DistributionResult deleteNsPackage(String csarId) {
        try {
            Response<DistributionResult> response = vfcService.deleteNsPackage(csarId).execute();
            if (response.isSuccessful()) {
                return response.body();
            } else {
                logger.info(String.format("Can not delete NS packages[code=%s, message=%s]", response.code(), response.message()));
                throw new VfcException("VFC service is not available!");
            }
        } catch (IOException e) {
            throw new VfcException("VFC service is not available!", e);
        }
    }

    @Override
    public Job deleteVfPackage(String csarId) {
        try {
            Response<Job> response = vfcService.deleteVnfPackage(csarId).execute();
            if (response.isSuccessful()) {
                return response.body();
            } else {
                logger.info(String.format("Can not delete VF packages[code=%s, message=%s]", response.code(), response.message()));
                throw new VfcException("VFC service is not available!");
            }
        } catch (IOException e) {
            throw new VfcException("VFC service is not available!", e);
        }
    }

	@Override
	public String getVnfPackages() {
		String result="";
        try {
        	logger.info("vfc getVnfPackages is starting!");
            Response<ResponseBody> response = this.vfcService.getVnfPackages().execute();
            logger.info("vfc getVnfPackages has finished!");
            if (response.isSuccessful()) {
            	result=new String(response.body().bytes());
            } else {
                logger.info(String.format("Can not get getVnfPackages[code=%s, message=%s]", response.code(), response.message()));
                result=Constant.CONSTANT_FAILED;;
            }
        } catch (IOException e) {
            logger.error("getVnfPackages occur exception:"+e);
            result=Constant.CONSTANT_FAILED;;
        }
        return result;
	}

	@Override
	public String getNetworkServicePackages() {

		String result="";
        try {
        	logger.info("vfc getNetworkServicePackages is starting!");
            Response<ResponseBody> response = this.vfcService.getNetworkServicePackages().execute();
            logger.info("vfc getNetworkServicePackages has finished!");
            if (response.isSuccessful()) {
            	result=new String(response.body().bytes());
            } else {
                logger.info(String.format("Can not get getNetworkServicePackages[code=%s, message=%s]", response.code(), response.message()));
                result=Constant.CONSTANT_FAILED;;
            }
        } catch (IOException e) {
            logger.error("getNetworkServicePackages occur exception:"+e);
            result=Constant.CONSTANT_FAILED;;
        }
        return result;
	
	}

	@Override
	public String getPnfPackages() {

		String result="";
        try {
        	logger.info("vfc getPnfPackages is starting!");
            Response<ResponseBody> response = this.vfcService.getPnfPackages().execute();
            logger.info("vfc getPnfPackages has finished!");
            if (response.isSuccessful()) {
            	result=new String(response.body().bytes());
            } else {
                logger.info(String.format("Can not get getPnfPackages[code=%s, message=%s]", response.code(), response.message()));
                result=Constant.CONSTANT_FAILED;;
            }
        } catch (IOException e) {
            logger.error("getPnfPackages occur exception:"+e);
            result=Constant.CONSTANT_FAILED;;
        }
        return result;
	
	}

	@Override
	public String downLoadNsPackage(String nsdInfoId) {

		String result="";
        try {
        	logger.info("vfc downLoadNsPackage is starting!");
            Response<ResponseBody> response = this.vfcService.downLoadNsPackage(nsdInfoId).execute();
            logger.info("vfc downLoadNsPackage has finished!");
            if (response.isSuccessful()) {
            	result=Constant.CONSTANT_SUCCESS;
            } else {
                logger.info(String.format("Can not get downLoadNsPackage[code=%s, message=%s]", response.code(), response.message()));
                result=Constant.CONSTANT_FAILED;;
            }
        } catch (IOException e) {
            logger.error("downLoadNsPackage occur exception:"+e);
            result=Constant.CONSTANT_FAILED;;
        }
        return result;
	
	}

	@Override
	public String downLoadPnfPackage(String pnfdInfoId) {

		String result="";
        try {
        	logger.info("vfc downLoadPnfPackage is starting!");
            Response<ResponseBody> response = this.vfcService.downLoadNsPackage(pnfdInfoId).execute();
            logger.info("vfc downLoadPnfPackage has finished!");
            if (response.isSuccessful()) {
            	result=Constant.CONSTANT_SUCCESS;
            } else {
                logger.info(String.format("Can not get downLoadPnfPackage[code=%s, message=%s]", response.code(), response.message()));
                result=Constant.CONSTANT_FAILED;;
            }
        } catch (IOException e) {
            logger.error("downLoadPnfPackage occur exception:"+e);
            result=Constant.CONSTANT_FAILED;;
        }
        return result;
	
	}

	@Override
	public String downLoadVnfPackage(String vnfPkgId) {

		String result="";
        try {
        	logger.info("vfc downLoadVnfPackage is starting!");
            Response<ResponseBody> response = this.vfcService.downLoadNsPackage(vnfPkgId).execute();
            logger.info("vfc downLoadVnfPackage has finished!");
            if (response.isSuccessful()) {
            	result=Constant.CONSTANT_SUCCESS;
            } else {
                logger.info(String.format("Can not get downLoadVnfPackage[code=%s, message=%s]", response.code(), response.message()));
                result=Constant.CONSTANT_FAILED;;
            }
        } catch (IOException e) {
            logger.error("downLoadVnfPackage occur exception:"+e);
            result=Constant.CONSTANT_FAILED;;
        }
        return result;
	
	}

	@Override
	public String deleteNsdPackage(String nsdInfoId) {

		String result="";
        try {
        	logger.info("vfc deleteNsdPackage is starting!");
            Response<ResponseBody> response = this.vfcService.deleteNsdPackage(nsdInfoId).execute();
            logger.info("vfc deleteNsdPackage has finished!");
            if (response.isSuccessful()) {
            	result=Constant.CONSTANT_SUCCESS;
            } else {
                logger.info(String.format("Can not get deleteNsdPackage[code=%s, message=%s]", response.code(), response.message()));
                result=Constant.CONSTANT_FAILED;;
            }
        } catch (IOException e) {
            logger.error("deleteNsdPackage occur exception:"+e);
            result=Constant.CONSTANT_FAILED;;
        }
        return result;
	
	}

	@Override
	public String deleteVnfPackage(String vnfPkgId) {

		String result="";
        try {
        	logger.info("vfc deleteVnfPackage is starting!");
            Response<ResponseBody> response = this.vfcService.deleteVnfdPackage(vnfPkgId).execute();
            logger.info("vfc deleteVnfPackage has finished!");
            if (response.isSuccessful()) {
            	result=Constant.CONSTANT_SUCCESS;
            } else {
                logger.info(String.format("Can not get deleteNsdPackage[code=%s, message=%s]", response.code(), response.message()));
                result=Constant.CONSTANT_FAILED;;
            }
        } catch (IOException e) {
            logger.error("deleteVnfPackage occur exception:"+e);
            result=Constant.CONSTANT_FAILED;;
        }
        return result;
	
	}

	@Override
	public String deletePnfPackage(String pnfdInfoId) {

		String result="";
        try {
        	logger.info("vfc deletePnfPackage is starting!");
            Response<ResponseBody> response = this.vfcService.deletePnfdPackage(pnfdInfoId).execute();
            logger.info("vfc deletePnfPackage has finished!");
            if (response.isSuccessful()) {
            	result=Constant.CONSTANT_SUCCESS;
            } else {
                logger.info(String.format("Can not get deletePnfPackage[code=%s, message=%s]", response.code(), response.message()));
                result=Constant.CONSTANT_FAILED;;
            }
        } catch (IOException e) {
            logger.error("deletePnfPackage occur exception:"+e);
            result=Constant.CONSTANT_FAILED;;
        }
        return result;
	
	}

	@Override
	public String getNetworkServiceInfo() {

		String result="";
        try {
        	logger.info("vfc getNetworkServiceInfo is starting!");
            Response<ResponseBody> response = this.vfcService.getNetworkServiceInfo().execute();
            logger.info("vfc getNetworkServiceInfo has finished!");
            if (response.isSuccessful()) {
            	result=new String(response.body().bytes());
            } else {
                logger.info(String.format("Can not get getNetworkServiceInfo[code=%s, message=%s]", response.code(), response.message()));
                result=Constant.CONSTANT_FAILED;;
            }
        } catch (IOException e) {
            logger.error("getNetworkServiceInfo occur exception:"+e);
            result=Constant.CONSTANT_FAILED;;
        }
        return result;
	
	}

	@Override
	public String createNetworkServiceInstance(HttpServletRequest request) {
		String result = "";
        try {
        	logger.info("aai createNetworkServiceInstance is starting");
        	RequestBody requestBody = extractBody(request);
            Response<ResponseBody> response = vfcService.createNetworkServiceInstance(requestBody).execute();
			logger.info("aai createNetworkServiceInstance has finished");
            if (response.isSuccessful()) {
            	result=new String(response.body().bytes());
            } else {
            	result=Constant.CONSTANT_FAILED;
                logger.error(String.format("Can not createNetworkServiceInstance[code=%s, message=%s]", response.code(), response.message()));
            }
        } catch (Exception e) {
        	result=Constant.CONSTANT_FAILED;
        	logger.error("createNetworkServiceInstance occur exception:"+e);
        }
        return result;
	}

	@Override
	public String deleteNetworkServiceInstance(String nsInstanceId) {

		String result="";
        try {
        	logger.info("vfc deleteNetworkServiceInstance is starting!");
            Response<ResponseBody> response = this.vfcService.deleteNetworkServiceInstance(nsInstanceId).execute();
            logger.info("vfc deleteNetworkServiceInstance has finished!");
            if (response.isSuccessful()) {
            	result=Constant.CONSTANT_SUCCESS;
            } else {
                logger.info(String.format("Can not get deleteNetworkServiceInstance[code=%s, message=%s]", response.code(), response.message()));
                result=Constant.CONSTANT_FAILED;;
            }
        } catch (IOException e) {
            logger.error("deleteNetworkServiceInstance occur exception:"+e);
            result=Constant.CONSTANT_FAILED;;
        }
        return result;
	
	}

	@Override
	public String terminateNetworkServiceInstance(HttpServletRequest request,String networkServiceInstanceId) {
		String result = "";
        try {
        	logger.info("aai terminateNetworkServiceInstance is starting");
        	RequestBody requestBody = extractBody(request);
            Response<ResponseBody> response = vfcService.terminateNetworkServiceInstance(networkServiceInstanceId,requestBody).execute();
			logger.info("aai terminateNetworkServiceInstance has finished");
            if (response.isSuccessful()) {
            	result=new String(response.body().bytes());
            } else {
            	result=Constant.CONSTANT_FAILED;
                logger.error(String.format("Can not terminateNetworkServiceInstance[code=%s, message=%s]", response.code(), response.message()));
            }
        } catch (Exception e) {
        	result=Constant.CONSTANT_FAILED;
        	logger.error("terminateNetworkServiceInstance occur exception:"+e);
        }
        return result;
	}

	@Override
	public String healNetworkServiceInstance(HttpServletRequest request,String networkServiceInstanceId) {
		String result = "";
        try {
        	logger.info("aai healNetworkServiceInstance is starting");
        	RequestBody requestBody = extractBody(request);
            Response<ResponseBody> response = vfcService.healNetworkServiceInstance(networkServiceInstanceId,requestBody).execute();
			logger.info("aai healNetworkServiceInstance has finished");
            if (response.isSuccessful()) {
            	result=new String(response.body().bytes());
            } else {
            	result=Constant.CONSTANT_FAILED;
                logger.error(String.format("Can not healNetworkServiceInstance[code=%s, message=%s]", response.code(), response.message()));
            }
        } catch (Exception e) {
        	result=Constant.CONSTANT_FAILED;
        	logger.error("healNetworkServiceInstance occur exception:"+e);
        }
        return result;
	}

	@Override
	public String scaleNetworkServiceInstance(HttpServletRequest request,String networkServiceInstanceId) {
		String result = "";
        try {
        	logger.info("aai scaleNetworkServiceInstance is starting");
        	RequestBody requestBody = extractBody(request);
            Response<ResponseBody> response = vfcService.scaleNetworkServiceInstance(networkServiceInstanceId,requestBody).execute();
			logger.info("aai scaleNetworkServiceInstance has finished");
            if (response.isSuccessful()) {
            	result=new String(response.body().bytes());
            } else {
            	result=Constant.CONSTANT_FAILED;
                logger.error(String.format("Can not scaleNetworkServiceInstance[code=%s, message=%s]", response.code(), response.message()));
            }
        } catch (Exception e) {
        	result=Constant.CONSTANT_FAILED;
        	logger.error("scaleNetworkServiceInstance occur exception:"+e);
        }
        return result;
	}

	@Override
	public String createNetworkServiceData(HttpServletRequest request) {
		String result = "";
        try {
        	logger.info("aai createNetworkServiceData is starting");
        	RequestBody requestBody = extractBody(request);
            Response<ResponseBody> response = vfcService.createNetworkServiceData(requestBody).execute();
			logger.info("aai createNetworkServiceData has finished");
            if (response.isSuccessful()) {
            	result=new String(response.body().bytes());
            } else {
            	result=Constant.CONSTANT_FAILED;
                logger.error(String.format("Can not createNetworkServiceData[code=%s, message=%s]", response.code(), response.message()));
            }
        } catch (Exception e) {
        	result=Constant.CONSTANT_FAILED;
        	logger.error("createNetworkServiceData occur exception:"+e);
        }
        return result;
	}

	@Override
	public String createVnfData(HttpServletRequest request) {
		String result = "";
        try {
        	logger.info("aai createVnfData is starting");
        	RequestBody requestBody = extractBody(request);
            Response<ResponseBody> response = vfcService.createVnfData(requestBody).execute();
			logger.info("aai createVnfData has finished");
            if (response.isSuccessful()) {
            	result=new String(response.body().bytes());
            } else {
            	result=Constant.CONSTANT_FAILED;
                logger.error(String.format("Can not createVnfData[code=%s, message=%s]", response.code(), response.message()));
            }
        } catch (Exception e) {
        	result=Constant.CONSTANT_FAILED;
        	logger.error("createVnfData occur exception:"+e);
        }
        return result;
	}

	@Override
	public String createPnfData(HttpServletRequest request) {
		String result = "";
        try {
        	logger.info("aai createPnfData is starting");
        	RequestBody requestBody = extractBody(request);
            Response<ResponseBody> response = vfcService.createPnfData(requestBody).execute();
			logger.info("aai createPnfData has finished");
            if (response.isSuccessful()) {
            	result=new String(response.body().bytes());
            } else {
            	result=Constant.CONSTANT_FAILED;
                logger.error(String.format("Can not createPnfData[code=%s, message=%s]", response.code(), response.message()));
            }
        } catch (Exception e) {
        	result=Constant.CONSTANT_FAILED;
        	logger.error("createPnfData occur exception:"+e);
        }
        return result;
	}

	@Override
	public String getNsdInfo(String nsdInfoId) {

		String result="";
        try {
        	logger.info("vfc getNsdInfo is starting!");
            Response<ResponseBody> response = this.vfcService.getNsdInfo(nsdInfoId).execute();
            logger.info("vfc getNsdInfo has finished!");
            if (response.isSuccessful()) {
            	result=Constant.CONSTANT_SUCCESS;
            } else {
                logger.info(String.format("Can not get getNsdInfo[code=%s, message=%s]", response.code(), response.message()));
                result=Constant.CONSTANT_FAILED;;
            }
        } catch (IOException e) {
            logger.error("getNsdInfo occur exception:"+e);
            result=Constant.CONSTANT_FAILED;;
        }
        return result;
	
	}

	@Override
	public String getVnfInfo(String vnfPkgId) {

		String result="";
        try {
        	logger.info("vfc getVnfInfo is starting!");
            Response<ResponseBody> response = this.vfcService.getVnfInfo(vnfPkgId).execute();
            logger.info("vfc getVnfInfo has finished!");
            if (response.isSuccessful()) {
            	result=Constant.CONSTANT_SUCCESS;
            } else {
                logger.info(String.format("Can not get getVnfInfo[code=%s, message=%s]", response.code(), response.message()));
                result=Constant.CONSTANT_FAILED;;
            }
        } catch (IOException e) {
            logger.error("getVnfInfo occur exception:"+e);
            result=Constant.CONSTANT_FAILED;;
        }
        return result;
	
	}

	@Override
	public String getPnfInfo(String pnfdInfoId) {

		String result="";
        try {
        	logger.info("vfc getPnfInfo is starting!");
            Response<ResponseBody> response = this.vfcService.getPnfInfo(pnfdInfoId).execute();
            logger.info("vfc getPnfInfo has finished!");
            if (response.isSuccessful()) {
            	result=Constant.CONSTANT_SUCCESS;
            } else {
                logger.info(String.format("Can not get getPnfInfo[code=%s, message=%s]", response.code(), response.message()));
                result=Constant.CONSTANT_FAILED;;
            }
        } catch (IOException e) {
            logger.error("getPnfInfo occur exception:"+e);
            result=Constant.CONSTANT_FAILED;;
        }
        return result;
	
	}
}
