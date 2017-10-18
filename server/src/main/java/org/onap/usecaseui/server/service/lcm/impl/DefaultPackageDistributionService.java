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
import retrofit2.Response;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static org.onap.usecaseui.server.service.lcm.domain.sdc.consts.SDCConsts.*;
import static org.onap.usecaseui.server.util.RestfulServices.create;

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
        Response<List<Vnf>> response = sdcCatalogService.listResources(RESOURCETYPE_VF, DISTRIBUTION_STATUS_DISTRIBUTED).execute();
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
    public JobStatus getJobStatus(String jobId) {
        try {
            Response<JobStatus> response = vfcService.getJobStatus(jobId).execute();
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
}
