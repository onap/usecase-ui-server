package org.onap.usecaseui.server.service.lcm.impl;

import org.onap.usecaseui.server.service.lcm.CustomerService;
import org.onap.usecaseui.server.service.lcm.domain.aai.AAIService;
import org.onap.usecaseui.server.service.lcm.domain.aai.bean.AAICustomer;
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
}
