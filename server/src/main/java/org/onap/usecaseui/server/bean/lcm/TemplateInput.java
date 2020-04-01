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

import com.google.common.base.MoreObjects;

import java.util.Objects;

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
        if ("{}".equals(defaultValue)) {
            this.defaultValue = "";
        } else {
            this.defaultValue = defaultValue;
        }
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

    @Override
    public boolean equals(Object o) {
        if (this == o) 
	{
		return true;
	}
        if (o == null || getClass() != o.getClass()) 
	{
		return false;
	}
        TemplateInput that = (TemplateInput) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(type, that.type) &&
                Objects.equals(description, that.description) &&
                Objects.equals(isRequired, that.isRequired) &&
                Objects.equals(defaultValue, that.defaultValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type, description, isRequired, defaultValue);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("name", name)
                .add("type", type)
                .add("description", description)
                .add("isRequired", isRequired)
                .add("defaultValue", defaultValue)
                .toString();
    }
}
