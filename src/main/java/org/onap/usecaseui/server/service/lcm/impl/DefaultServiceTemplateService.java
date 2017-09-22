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

import org.onap.usecaseui.server.bean.lcm.ServiceTemplate;
import org.onap.usecaseui.server.service.lcm.ServiceTemplateService;
import org.onap.usecaseui.server.service.lcm.domain.sdc.SDCCatalogService;
import org.onap.usecaseui.server.service.lcm.domain.sdc.bean.SDCServiceTemplate;
import org.onap.usecaseui.server.service.lcm.domain.sdc.exceptions.SDCCatalogException;
import org.onap.usecaseui.server.util.RestfulServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;

import static org.onap.usecaseui.server.service.lcm.domain.sdc.consts.SDCConsts.CATEGORY_E2E_SERVICE;
import static org.onap.usecaseui.server.service.lcm.domain.sdc.consts.SDCConsts.DISTRIBUTION_STATUS_DISTRIBUTED;

@Service("ServiceTemplateService")
@Transactional
@org.springframework.context.annotation.Configuration
@EnableAspectJAutoProxy
public class DefaultServiceTemplateService implements ServiceTemplateService {

    private static final Logger logger = LoggerFactory.getLogger(DefaultServiceTemplateService.class);

    private SDCCatalogService sdcCatalog;

    public DefaultServiceTemplateService() {
        this(RestfulServices.create(SDCCatalogService.class));
    }

    public DefaultServiceTemplateService(SDCCatalogService sdcCatalog) {
        this.sdcCatalog = sdcCatalog;
    }

    @Override
    public List<ServiceTemplate> listDistributedServiceTemplate() {
        try {
            List<SDCServiceTemplate> serviceTemplate = this.sdcCatalog.listServices(CATEGORY_E2E_SERVICE, DISTRIBUTION_STATUS_DISTRIBUTED).execute().body();
            return translate(serviceTemplate);
        } catch (IOException e) {
            logger.error("Visit SDC Catalog occur exception");
            logger.info("SDC Catalog Exception: ", e);
            throw new SDCCatalogException("SDC Catalog is not available.", e);
        }
    }

    private List<ServiceTemplate> translate(List<SDCServiceTemplate> serviceTemplate) {
        return null;
    }
}
