/*
 * Copyright (C) 2021 CTC, Inc. and others. All rights reserved.
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
package org.onap.usecaseui.server.bean.intent;
import org.apache.commons.collections.MapUtils;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name="intent_instance")
public class IntentInstance implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "intent_name")
    private String intentName;

    @Column(name = "intent_source")
    private int intentSource;

    @Column(name = "customer")
    private String customer;

    @Column(name = "intent_content")
    private String intentContent;

    @Column(name = "intent_config")
    private String intentConfig;

    @Column(name = "business_instance_id")
    private String businessInstanceId;

    @Column(name = "business_instance")
    private String businessInstance;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIntentName() {
        return intentName;
    }

    public void setIntentName(String intentName) {
        this.intentName = intentName;
    }

    public String getIntentSource() {
        if (this.intentSource == 1) {
            return "ccvpn";
        }
        else {
            return "5gs";
        }
    }

    public void setIntentSource(String intentSource) {
        if ("ccvpn".equals(intentSource)) {
            this.intentSource = 1;
        }
        else {
            this.intentSource = 0;
        }
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getIntentContent() {
        return intentContent;
    }

    public void setIntentContent(String intentContent) {
        this.intentContent = intentContent;
    }

    public String getIntentConfig() {
        return intentConfig;
    }

    public void setIntentConfig(String intentConfig) {
        this.intentConfig = intentConfig;
    }

    public String getBusinessInstanceId() {
        return businessInstanceId;
    }

    public void setBusinessInstanceId(String businessInstanceId) {
        this.businessInstanceId = businessInstanceId;
    }

    public String getBusinessInstance() {
        return businessInstance;
    }

    public void setBusinessInstance(String businessInstance) {
        this.businessInstance = businessInstance;
    }

    public static IntentInstance map2Object(Map map) {
        IntentInstance intentInstance = new IntentInstance();
        if (MapUtils.getIntValue(map, "id", -1) > -1) {
            intentInstance.setId(MapUtils.getIntValue(map, "id", -1));
        }
        intentInstance.setIntentName(MapUtils.getString(map, "intent_name", ""));
        intentInstance.setIntentSource(MapUtils.getString(map, "intent_source", ""));
        intentInstance.setCustomer(MapUtils.getString(map, "customer", ""));
        intentInstance.setIntentContent(MapUtils.getString(map, "intent_content", ""));
        intentInstance.setIntentConfig(MapUtils.getString(map, "intent_config", ""));
        intentInstance.setBusinessInstanceId(MapUtils.getString(map, "business_instance_id", ""));
        intentInstance.setBusinessInstance(MapUtils.getString(map, "business_instance", ""));
        return intentInstance;
    }

    public static Map object2Map(IntentInstance intentInstance) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", intentInstance.getId());
        map.put("intent_name", intentInstance.getIntentName());
        map.put("intent_source", intentInstance.getIntentSource());
        map.put("customer", intentInstance.getCustomer());
        map.put("intent_content", intentInstance.getIntentContent());
        map.put("intent_config", intentInstance.getIntentConfig());
        map.put("business_instance_id", intentInstance.getBusinessInstanceId());
        map.put("business_instance", intentInstance.getBusinessInstance());

        return map;
    }
}