package org.onap.usecaseui.server.service.lcm;

import org.onap.usecaseui.server.service.lcm.domain.aai.bean.AAICustomer;

import java.util.List;

public interface CustomerService {
    List<AAICustomer> listCustomer();
}
