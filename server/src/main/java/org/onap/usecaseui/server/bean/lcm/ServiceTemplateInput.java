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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ServiceTemplateInput {

    private String invariantUUID;

    private String uuid;

    private String name;

    private String type;

    private String version;

    private String description;

    private String category;

    private String subcategory;
    
    private String customizationUuid;
    
    private List<TemplateInput> inputs;
    
    private List<ServiceTemplateInput> nestedTemplates;

    public ServiceTemplateInput(
            String invariantUUID,
            String uuid,
            String name,
            String type,
            String version,
            String description,
            String category,
            String subcategory,
            String customizationUuid,
            List<TemplateInput> inputs) {
        this.invariantUUID = invariantUUID;
        this.uuid = uuid;
        this.name = name;
        this.type = type;
        this.version = version;
        this.description = description;
        this.category = category;
        this.subcategory = subcategory;
        this.customizationUuid = customizationUuid;
        this.inputs = inputs;
        this.nestedTemplates = new ArrayList<>();
    }

    public String getInvariantUUID() {
        return invariantUUID;
    }

    public String getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getVersion() {
        return version;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public String getSubcategory() {
        return subcategory;
    }
    
    public String getCustomizationUuid() {
		return customizationUuid;
	}

	public List<TemplateInput> getInputs() {
        return inputs;
    }

    public List<ServiceTemplateInput> getNestedTemplates() {
        return nestedTemplates;
    }

    public void addNestedTemplate(ServiceTemplateInput template) {
        this.nestedTemplates.add(template);
    }

    public void addInputs(List<TemplateInput> inputs) {
        this.inputs.addAll(inputs);
    }

    public void addInput(TemplateInput input) {
        this.inputs.add(input);
    }

    public void setType(String type) {
        this.type = type;
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
        ServiceTemplateInput that = (ServiceTemplateInput) o;
        return Objects.equals(invariantUUID, that.invariantUUID) &&
                Objects.equals(uuid, that.uuid) &&
                Objects.equals(name, that.name) &&
                Objects.equals(type, that.type) &&
                Objects.equals(version, that.version) &&
                Objects.equals(description, that.description) &&
                Objects.equals(category, that.category) &&
                Objects.equals(subcategory, that.subcategory) &&
                Objects.equals(customizationUuid, that.customizationUuid) &&
                Objects.equals(inputs, that.inputs) &&
                Objects.equals(nestedTemplates, that.nestedTemplates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(invariantUUID, uuid, name, type, version, description, category, subcategory,customizationUuid, inputs, nestedTemplates);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("invariantUUID", invariantUUID)
                .add("uuid", uuid)
                .add("name", name)
                .add("type", type)
                .add("version", version)
                .add("description", description)
                .add("category", category)
                .add("subcategory", subcategory)
                .add("customizationUuid", customizationUuid)
                .add("inputs", inputs)
                .add("nestedTemplates", nestedTemplates)
                .toString();
    }
}
