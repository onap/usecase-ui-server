package org.onap.usecaseui.server.service.lcm.domain.aai;

import org.onap.usecaseui.server.service.lcm.domain.aai.bean.AAICustomer;
import org.onap.usecaseui.server.service.lcm.domain.aai.bean.ServiceInstance;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

import java.util.List;

public interface AAIService {

    @Headers({
            "X-TransactionId: 7777",
            "X-FromAppId: uui",
            "Authorization: QUFJOkFBSQ=="
    })
    @GET("/api/aai-business/v11/customers")
    Call<List<AAICustomer>> listCustomer();

    @Headers({
            "X-TransactionId: 7777",
            "X-FromAppId: uui",
            "Authorization: QUFJOkFBSQ=="
    })
    @GET("/api/aai-business/v11/customers/customer/{global-customer-id}/service-subscriptions/service-subscription/{service-type}/service-instances")
    Call<List<ServiceInstance>> listServiceInstances(@Path("global-customer-id") String customerId, @Path("service-type") String serviceType);
}
