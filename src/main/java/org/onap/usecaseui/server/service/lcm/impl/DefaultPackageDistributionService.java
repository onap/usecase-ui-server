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
import org.onap.usecaseui.server.service.lcm.domain.aai.AAIService;
import org.onap.usecaseui.server.service.lcm.domain.aai.bean.VimInfo;
import org.onap.usecaseui.server.service.lcm.domain.sdc.SDCCatalogService;
import org.onap.usecaseui.server.service.lcm.domain.sdc.bean.SDCServiceTemplate;
import org.onap.usecaseui.server.service.lcm.domain.sdc.bean.Vnf;
import org.onap.usecaseui.server.service.lcm.domain.sdc.exceptions.SDCCatalogException;
import org.onap.usecaseui.server.util.RestfulServices;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

import static org.onap.usecaseui.server.service.lcm.domain.sdc.consts.SDCConsts.*;

@Service("PackageDistributionService")
@org.springframework.context.annotation.Configuration
@EnableAspectJAutoProxy
public class DefaultPackageDistributionService implements PackageDistributionService {

    private SDCCatalogService sdcCatalogService;

    private AAIService aaiService;

    public DefaultPackageDistributionService() {
        this(RestfulServices.create(SDCCatalogService.class), RestfulServices.create(AAIService.class));
    }

    public DefaultPackageDistributionService(SDCCatalogService sdcCatalogService, AAIService aaiService) {
        this.sdcCatalogService = sdcCatalogService;
        this.aaiService = aaiService;
    }

    @Override
    public VfNsPackageInfo retrievePackageInfo() {
        try {
            List<SDCServiceTemplate> nsTemplate = sdcCatalogService.listServices(CATEGORY_NS, DISTRIBUTION_STATUS_DISTRIBUTED).execute().body();
            List<Vnf> vnfs = sdcCatalogService.listResources(RESOURCETYPE_VF, DISTRIBUTION_STATUS_DISTRIBUTED).execute().body();
            List<VimInfo> vim = aaiService.listVimInfo().execute().body();
            return new VfNsPackageInfo(nsTemplate, vnfs, vim);
        } catch (IOException e) {
            throw new SDCCatalogException("SDC Service is not available!", e);
        }
    }
}
