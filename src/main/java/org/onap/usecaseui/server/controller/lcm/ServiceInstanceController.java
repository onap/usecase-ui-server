package org.onap.usecaseui.server.controller.lcm;

import org.onap.usecaseui.server.service.lcm.ServiceInstanceService;
import org.onap.usecaseui.server.service.lcm.domain.aai.bean.ServiceInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@org.springframework.context.annotation.Configuration
@EnableAspectJAutoProxy
public class ServiceInstanceController {

    private static final Logger logger = LoggerFactory.getLogger(ServiceInstanceController.class);

    @Resource(name="ServiceInstanceService")
    private ServiceInstanceService serviceInstanceService;

    @ResponseBody
    @RequestMapping(value = {"/lcm/service-instances"}, method = RequestMethod.GET , produces = "application/json")
    public List<ServiceInstance> getCustomers(HttpServletRequest request){
        String customerId = request.getParameter("customerId");
        String serviceType = request.getParameter("serviceType");
        logger.info(String.format(
                "list service instances with [customerId=%s, serviceType=%s]",
                customerId,
                serviceType));

        return serviceInstanceService.listServiceInstances(customerId, serviceType);
    }
}
