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
package org.onap.usecaseui.server.bean.lcm;

public class TemplateInput {

    private String name;

    private String type;

    private String description;

    private String isRequired;

    private String defaultValue;

    public TemplateInput(String name, String type, String description, String isRequired, String defaultValue) {
        this.name = name;
        this.type = type;
        this.description = description;
        this.isRequired = isRequired;
        this.defaultValue = defaultValue;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public String getIsRequired() {
        return isRequired;
    }

    public String getDefaultValue() {
        return defaultValue;
    }
}
