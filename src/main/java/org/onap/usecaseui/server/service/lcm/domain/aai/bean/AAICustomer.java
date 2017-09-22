package org.onap.usecaseui.server.service.lcm.domain.aai.bean;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AAICustomer {

    private String globalCustomerId;

    private String subscriberName;

    private String subscriberType;

    @JsonCreator
    public AAICustomer(
            @JsonProperty("global-customer-id") String globalCustomerId,
            @JsonProperty("subscriber-name") String subscriberName,
            @JsonProperty("subscriber-type") String subscriberType) {
        this.globalCustomerId = globalCustomerId;
        this.subscriberName = subscriberName;
        this.subscriberType = subscriberType;
    }

    @JsonProperty("global-customer-id")
    public String getGlobalCustomerId() {
        return globalCustomerId;
    }

    @JsonProperty("subscriber-name")
    public String getSubscriberName() {
        return subscriberName;
    }

    @JsonProperty("subscriber-type")
    public String getSubscriberType() {
        return subscriberType;
    }
}
