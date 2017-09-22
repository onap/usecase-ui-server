package org.onap.usecaseui.server.controller.lcm;

import org.onap.usecaseui.server.bean.lcm.ServiceTemplate;
import org.onap.usecaseui.server.service.lcm.ServiceTemplateService;
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
public class ServiceTemplateController {

    private static final Logger logger = LoggerFactory.getLogger(ServiceTemplateController.class);

    @Resource(name="ServiceTemplateService")
    private ServiceTemplateService serviceTemplateService;

    @ResponseBody
    @RequestMapping(value = {"/lcm/service-templates"}, method = RequestMethod.GET , produces = "application/json")
    public List<ServiceTemplate> getServiceTemplates(HttpServletRequest request){
        return serviceTemplateService.listDistributedServiceTemplate();
    }


}
