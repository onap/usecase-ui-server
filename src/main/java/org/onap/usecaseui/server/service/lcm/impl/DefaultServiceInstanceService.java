package org.onap.usecaseui.server.service.lcm.impl;

import org.onap.usecaseui.server.service.lcm.ServiceInstanceService;
import org.onap.usecaseui.server.service.lcm.domain.aai.AAIService;
import org.onap.usecaseui.server.service.lcm.domain.aai.bean.ServiceInstance;
import org.onap.usecaseui.server.service.lcm.domain.aai.exceptions.AAIException;
import org.onap.usecaseui.server.util.RestfulServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service("ServiceInstanceService")
@org.springframework.context.annotation.Configuration
@EnableAspectJAutoProxy
public class DefaultServiceInstanceService implements ServiceInstanceService {

    private static final Logger logger = LoggerFactory.getLogger(DefaultServiceInstanceService.class);

    private AAIService aaiService;

    public DefaultServiceInstanceService() {
        this(RestfulServices.create(AAIService.class));
    }

    public DefaultServiceInstanceService(AAIService aaiService) {
        this.aaiService = aaiService;
    }

    @Override
    public List<ServiceInstance> listServiceInstances(String customerId, String serviceType) {
        try {
            return aaiService.listServiceInstances(customerId, serviceType).execute().body();
        } catch (IOException e) {
            logger.error("list services instances occur exception");
            throw new AAIException("AAI is not available.", e);
        }
    }
}
