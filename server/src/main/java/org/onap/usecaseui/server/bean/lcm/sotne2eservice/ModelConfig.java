/**
 * Copyright 2020 Huawei Corporation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.onap.usecaseui.server.bean.lcm.sotne2eservice;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ModelConfig {

    @JsonProperty("subscriberId")
    String subscriberId;
    @JsonProperty("subscriptionType")
    String subscriptionType;
    @JsonProperty("status")
    String status;
    @JsonProperty("deleteSleepTime")
    String deleteSleepTime;
    @JsonProperty("createSleepTime")
    String createSleepTime;
    @JsonProperty("servicemodelinformation")
    ModelInfor serviceModel;
    @JsonProperty("resourcemodelinformation")
    List<ModelInfor> resourcemodelinformation;


    public String getSubscriberId() {
        return subscriberId;
    }

    public void setSubscriberId(String subscriberId) {
        this.subscriberId = subscriberId;
    }

    public String getSubscriptionType() {
        return subscriptionType;
    }

    public void setSubscriptionType(String subscriptionType) {
        this.subscriptionType = subscriptionType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDeleteSleepTime() {
        return deleteSleepTime;
    }

    public void setDeleteSleepTime(String deleteSleepTime) {
        this.deleteSleepTime = deleteSleepTime;
    }

    public String getCreateSleepTime() {
        return createSleepTime;
    }

    public void setCreateSleepTime(String createSleepTime) {
        this.createSleepTime = createSleepTime;
    }

    public ModelInfor getServiceModel() {
        return serviceModel;
    }

    public void setServiceModel(ModelInfor serviceModel) {
        this.serviceModel = serviceModel;
    }

    public List<ModelInfor> getResourcemodelinformation() {
        return resourcemodelinformation;
    }

    public void setResourcemodelinformation(List<ModelInfor> resourcemodelinformation) {
        this.resourcemodelinformation = resourcemodelinformation;
    }
}
