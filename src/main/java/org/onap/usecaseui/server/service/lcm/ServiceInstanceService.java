package org.onap.usecaseui.server.service.lcm;

import org.onap.usecaseui.server.service.lcm.domain.aai.bean.ServiceInstance;

import java.util.List;

public interface ServiceInstanceService {

    List<ServiceInstance> listServiceInstances(String customerId, String serviceType);
}
