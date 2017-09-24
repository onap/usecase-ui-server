/**
 * Copyright 2016-2017 ZTE Corporation.
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
package org.onap.usecaseui.server.service.lcm.domain.so.bean;

import java.util.Map;

public class ServiceInstantiationRequest {

    private String name;

    private String description;

    private String serviceDefId;

    private String templateId;

    private Map<String, String> parameters;

    public ServiceInstantiationRequest(String name, String description, String serviceDefId, String templateId, Map<String, String> parameters) {
        this.name = name;
        this.description = description;
        this.serviceDefId = serviceDefId;
        this.templateId = templateId;
        this.parameters = parameters;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getServiceDefId() {
        return serviceDefId;
    }

    public String getTemplateId() {
        return templateId;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }
}
