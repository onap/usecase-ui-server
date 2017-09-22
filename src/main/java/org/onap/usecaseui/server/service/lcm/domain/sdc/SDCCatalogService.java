package org.onap.usecaseui.server.service.lcm.domain.sdc;

import okhttp3.ResponseBody;
import org.onap.usecaseui.server.service.lcm.domain.sdc.bean.SDCServiceTemplate;
import org.onap.usecaseui.server.service.lcm.domain.sdc.bean.Vnf;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

import java.util.List;

public interface SDCCatalogService {

    @GET("sdc/v1/catalog/services")
    Call<List<SDCServiceTemplate>> listServices(@Query("category")String category, @Query("distributionStatus") String distributionStatus);

    @GET
    Call<ResponseBody> downloadCsar(@Url String fileUrl);

    @GET("sdc/v1/catalog/resources")
    Call<List<Vnf>> listResources(@Query("resourceType") String resourceType, @Query("distributionStatus") String distributionStatus);
}
