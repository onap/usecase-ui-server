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
package org.onap.usecaseui.server.service.lcm.domain.sdc.bean;

public class SDCServiceTemplate {

    private String uuid;

    private String invariantUUID;

    private String name;

    private String toscaModelURL;

    private String category;

    public SDCServiceTemplate(String uuid, String invariantUUID, String name, String toscaModelURL, String category) {
        this.uuid = uuid;
        this.invariantUUID = invariantUUID;
        this.name = name;
        this.toscaModelURL = toscaModelURL;
        this.category = category;
    }

    public String getUuid() {
        return uuid;
    }

    public String getInvariantUUID() {
        return invariantUUID;
    }

    public String getName() {
        return name;
    }

    public String getToscaModelURL() {
        return toscaModelURL;
    }

    public String getCategory() {
        return category;
    }
}
