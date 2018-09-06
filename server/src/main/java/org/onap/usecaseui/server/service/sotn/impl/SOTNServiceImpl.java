package org.onap.usecaseui.server.service.sotn.impl;

import static org.onap.usecaseui.server.util.RestfulServices.extractBody;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.onap.usecaseui.server.bean.sotn.Pinterface;
import org.onap.usecaseui.server.bean.sotn.PinterfaceRsp;
import org.onap.usecaseui.server.constant.Constant;
import org.onap.usecaseui.server.service.lcm.domain.aai.AAIService;
import org.onap.usecaseui.server.service.lcm.domain.aai.exceptions.AAIException;
import org.onap.usecaseui.server.service.lcm.domain.so.exceptions.SOException;
import org.onap.usecaseui.server.service.sotn.SOTNService;
import org.onap.usecaseui.server.util.RestfulServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Service;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Response;

@Service("SOTNService")
@org.springframework.context.annotation.Configuration
@EnableAspectJAutoProxy
public class SOTNServiceImpl implements SOTNService{
	
    private static final Logger logger = LoggerFactory.getLogger(SOTNServiceImpl.class);

    private AAIService aaiService;

    public SOTNServiceImpl() {
        this(RestfulServices.create(AAIService.class));
    }

    public SOTNServiceImpl(AAIService aaiService) {
    	this.aaiService = aaiService;
	}

	@Override
	public String getNetWorkResources() {
        try {
        	logger.info("aai getNetWorkResources is starting!");
            Response<ResponseBody> response = this.aaiService.listNetWorkResources().execute();
            logger.info("aai getNetWorkResources has finished!");
            if (response.isSuccessful()) {
            	String result=new String(response.body().bytes());
                return result;
            } else {
                logger.info(String.format("Can not get getNetWorkResources[code=%s, message=%s]", response.code(), response.message()));
                return "";
            }
        } catch (IOException e) {
            logger.error("getNetWorkResources occur exception:"+e);
            throw new AAIException("AAI is not available.", e);
        }
	}

	@Override
	public List<Pinterface> getPinterfaceByPnfName(String pnfName) {
        try {
        	logger.info("aai getPinterfaceByPnfName is starting!");
            Response<PinterfaceRsp> response = this.aaiService.getPinterfaceByPnfName(pnfName).execute();
            logger.info("aai getPinterfaceByPnfName has finished!");
            if (response.isSuccessful()) {
                return response.body().getPinterfaces();
            } else {
                logger.info(String.format("Can not get getPinterfaceByPnfName[code=%s, message=%s]", response.code(), response.message()));
                return Collections.emptyList();
            }
        } catch (IOException e) {
            logger.error("getPinterfaceByPnfName occur exception:"+e);
            throw new AAIException("AAI is not available.", e);
        }
	}

	@Override
	public String getLogicalLinks() {
        try {
        	logger.info("aai getLogicalLinks is starting!");
            Response<ResponseBody> response = this.aaiService.getLogicalLinks().execute();
            logger.info("aai getLogicalLinks has finished!");
            if (response.isSuccessful()) {
            	String result=new String(response.body().bytes());
                return result;
            } else {
                logger.info(String.format("Can not get getLogicalLinks[code=%s, message=%s]", response.code(), response.message()));
                return "";
            }
        } catch (IOException e) {
            logger.error("getLogicalLinks occur exception:"+e);
            throw new AAIException("AAI is not available.", e);
        }
	}

	@Override
	public String createTopoNetwork(HttpServletRequest request,String networkId) {
		String result = "";
        try {
        	logger.info("aai createTopoNetwork is starting");
        	RequestBody requestBody = extractBody(request);
            Response<ResponseBody> response = aaiService.createTopoNetwork(requestBody,networkId).execute();
			logger.info("aai createTopoNetwork has finished");
            if (response.isSuccessful()) {
            	result=Constant.CONSTANT_SUCCESS;
            } else {
            	result=Constant.CONSTANT_FAILED;
                logger.error(String.format("Can not createTopoNetwork[code=%s, message=%s]", response.code(), response.message()));
                throw new SOException("aai createTopoNetwork failed!");
            }
        } catch (Exception e) {
        	result=Constant.CONSTANT_FAILED;
        	logger.error("createTopoNetwork occur exception:"+e);
            throw new SOException("aai createTopoNetwork is not available!", e);
        }
        return result;
	}

	@Override
	public String createTerminationPoint(HttpServletRequest request,String pnfName,String tpId) {
		String result = "";
        try {
        	logger.info("aai createTerminationPoint is starting");
        	RequestBody requestBody = extractBody(request);
            Response<ResponseBody> response = aaiService.createTerminationPoint(requestBody,pnfName,tpId).execute();
			logger.info("aai createTerminationPoint has finished");
            if (response.isSuccessful()) {
            	result=Constant.CONSTANT_SUCCESS;
            } else {
            	result=Constant.CONSTANT_FAILED;
                logger.error(String.format("Can not createTerminationPoint[code=%s, message=%s]", response.code(), response.message()));
                throw new SOException("aai createTerminationPoint failed!");
            }
        } catch (Exception e) {
        	result=Constant.CONSTANT_FAILED;
        	logger.error("createTerminationPoint occur exception:"+e);
            throw new SOException("aai createTerminationPoint is not available!", e);
        }
        return result;
	}

	@Override
	public String createLink(HttpServletRequest request,String linkName) {
		String result = "";
        try {
        	logger.info("aai createLink is starting");
        	RequestBody requestBody = extractBody(request);
            Response<ResponseBody> response = aaiService.createLink(requestBody,linkName).execute();
			logger.info("aai createLink has finished");
            if (response.isSuccessful()) {
            	result=Constant.CONSTANT_SUCCESS;
            } else {
            	result=Constant.CONSTANT_FAILED;
                logger.error(String.format("Can not createLink[code=%s, message=%s]", response.code(), response.message()));
                throw new SOException("aai createLink failed!");
            }
        } catch (Exception e) {
        	result="FAILED";
        	logger.error("createLink occur exception:"+e);
            throw new SOException("aai createLink is not available!", e);
        }
        return result;
	}
	
	@Override
	public String createPnf(HttpServletRequest request,String pnfName) {
		String result = "";
        try {
        	logger.info("aai createPnf is starting");
        	RequestBody requestBody = extractBody(request);
            Response<ResponseBody> response = aaiService.createPnf(requestBody,pnfName).execute();
			logger.info("aai createPnf has finished");
            if (response.isSuccessful()) {
            	result=Constant.CONSTANT_SUCCESS;
            } else {
            	result=Constant.CONSTANT_FAILED;
                logger.error(String.format("Can not createPnf[code=%s, message=%s]", response.code(), response.message()));
                throw new SOException("aai createPnf failed!");
            }
        } catch (Exception e) {
        	result=Constant.CONSTANT_FAILED;
        	logger.error("createPnf occur exception:"+e);
        }
        return result;
	}
	
	@Override
	public String deleteLink(String linkName) {
		String result = "";
        try {
        	logger.info("aai deleteLink is starting");
            Response<ResponseBody> response = aaiService.deleteLink(linkName).execute();
			logger.info("aai deleteLink has finished");
            if (response.isSuccessful()) {
            	result=Constant.CONSTANT_SUCCESS;
            } else {
            	result=Constant.CONSTANT_FAILED;
                logger.error(String.format("Can not deleteLink[code=%s, message=%s]", response.code(), response.message()));
                throw new SOException("aai deleteLink failed!");
            }
        } catch (Exception e) {
        	result=Constant.CONSTANT_FAILED;
        	logger.error("deleteLink occur exception:"+e);
            throw new SOException("aai deleteLink is not available!", e);
        }
        return result;
	}

	@Override
	public String getServiceInstances(String customerId, String serviceType) {
        try {
        	logger.info("aai getServiceInstances is starting");
            Response<ResponseBody> response = aaiService.getServiceInstances(customerId, serviceType).execute();
			logger.info("aai getServiceInstances has finished");
            if (response.isSuccessful()) {
            	String result=new String(response.body().bytes());
                return result;
            } else {
                logger.error(String.format("Can not getServiceInstances[code=%s, message=%s]", response.code(), response.message()));
                throw new SOException("aai getServiceInstances failed!");
            }
        } catch (Exception e) {
        	logger.error("getServiceInstances occur exception:"+e);
            throw new SOException("aai getServiceInstances is not available!", e);
        }
	}

	@Override
	public String serviceInstanceInfo(String customerId, String serviceType, String serviceInstanceId) {
        try {
        	logger.info("aai serviceInstanceInfo is starting");
            Response<ResponseBody> response = aaiService.serviceInstaneInfo(customerId, serviceType, serviceInstanceId).execute();
			logger.info("aai serviceInstanceInfo has finished");
            if (response.isSuccessful()) {
            	String result=new String(response.body().bytes());
                return result;
            } else {
                logger.error(String.format("Can not serviceInstanceInfo[code=%s, message=%s]", response.code(), response.message()));
                throw new SOException("aai serviceInstanceInfo failed!");
            }
        } catch (Exception e) {
        	logger.error("serviceInstanceInfo occur exception:"+e);
            throw new SOException("aai serviceInstanceInfo is not available!", e);
        }
	}
}
