package org.onap.usecaseui.server.service.lcm.domain.aai.bean;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ServiceInstance {

    private String globalCustomerId;

    private String serviceType;

    private String serviceInstanceId;

    private String subscriberName;

    private String subscriberType;

    private String serviceInstanceName;

    private String serviceInstanceLocationId;

    @JsonCreator
    public ServiceInstance(
            @JsonProperty("global-customer-id") String globalCustomerId,
            @JsonProperty("service-type") String serviceType,
            @JsonProperty("service-instance-id") String serviceInstanceId,
            @JsonProperty("subscriber-name") String subscriberName,
            @JsonProperty("subscriber-type") String subscriberType,
            @JsonProperty("service-instance-name") String serviceInstanceName,
            @JsonProperty("service-instance-location-id") String serviceInstanceLocationId) {
        this.globalCustomerId = globalCustomerId;
        this.serviceType = serviceType;
        this.serviceInstanceId = serviceInstanceId;
        this.subscriberName = subscriberName;
        this.subscriberType = subscriberType;
        this.serviceInstanceName = serviceInstanceName;
        this.serviceInstanceLocationId = serviceInstanceLocationId;
    }

    @JsonProperty("global-customer-id")
    public String getGlobalCustomerId() {
        return globalCustomerId;
    }

    @JsonProperty("service-type")
    public String getServiceType() {
        return serviceType;
    }

    @JsonProperty("service-instance-id")
    public String getServiceInstanceId() {
        return serviceInstanceId;
    }

    @JsonProperty("subscriber-name")
    public String getSubscriberName() {
        return subscriberName;
    }

    @JsonProperty("subscriber-type")
    public String getSubscriberType() {
        return subscriberType;
    }

    @JsonProperty("service-instance-name")
    public String getServiceInstanceName() {
        return serviceInstanceName;
    }

    @JsonProperty("service-instance-location-id")
    public String getServiceInstanceLocationId() {
        return serviceInstanceLocationId;
    }
}
