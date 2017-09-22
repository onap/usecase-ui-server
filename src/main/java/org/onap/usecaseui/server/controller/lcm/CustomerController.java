package org.onap.usecaseui.server.controller.lcm;

import org.onap.usecaseui.server.service.lcm.CustomerService;
import org.onap.usecaseui.server.service.lcm.domain.aai.bean.AAICustomer;
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
public class CustomerController {

    @Resource(name="CustomerService")
    private CustomerService customerService;

    @ResponseBody
    @RequestMapping(value = {"/lcm/customers"}, method = RequestMethod.GET , produces = "application/json")
    public List<AAICustomer> getCustomers(HttpServletRequest request){
        return customerService.listCustomer();
    }
}
