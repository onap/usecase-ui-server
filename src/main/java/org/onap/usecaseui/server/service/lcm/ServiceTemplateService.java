package org.onap.usecaseui.server.service.lcm;

import org.onap.usecaseui.server.bean.lcm.ServiceTemplate;

import java.util.List;

public interface ServiceTemplateService {

    List<ServiceTemplate> listDistributedServiceTemplate();
}
