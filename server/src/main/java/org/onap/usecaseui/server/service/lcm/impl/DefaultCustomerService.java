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

import org.onap.usecaseui.server.service.lcm.CustomerService;
import org.onap.usecaseui.server.service.lcm.domain.aai.AAIService;
import org.onap.usecaseui.server.service.lcm.domain.aai.bean.AAICustomer;
import org.onap.usecaseui.server.service.lcm.domain.aai.bean.AAIServiceSubscription;
import org.onap.usecaseui.server.service.lcm.domain.aai.exceptions.AAIException;
import org.onap.usecaseui.server.util.RestfulServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service("CustomerService")
@org.springframework.context.annotation.Configuration
@EnableAspectJAutoProxy
public class DefaultCustomerService implements CustomerService {

    private static final Logger logger = LoggerFactory.getLogger(DefaultCustomerService.class);

    private AAIService aaiService;

    public DefaultCustomerService() {
        this(RestfulServices.create(AAIService.class));
    }

    public DefaultCustomerService(AAIService aaiService) {
        this.aaiService = aaiService;
    }

    @Override
    public List<AAICustomer> listCustomer() {
        try {
            return this.aaiService.listCustomer().execute().body();
        } catch (IOException e) {
            logger.error("list customers occur exception");
            throw new AAIException("AAI is not available.", e);
        }
    }

    @Override
    public List<AAIServiceSubscription> listServiceSubscriptions(String customerId) {
        try {
            return this.aaiService.listServiceSubscriptions(customerId).execute().body();
        } catch (IOException e) {
            logger.error("list customers occur exception");
            throw new AAIException("AAI is not available.", e);
        }
    }
}
