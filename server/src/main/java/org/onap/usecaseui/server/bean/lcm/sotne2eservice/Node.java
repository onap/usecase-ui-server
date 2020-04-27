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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
//import org.onap.usecaseui.server.bean.orderservice.DataNode;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "shape",
        "image",
        "label",
        "color",
        "dataNode"
})
public class Node {
    @JsonProperty("id")
    private String id;
    @JsonProperty("shape")
    private String shape;
    @JsonProperty("image")
    private String image;
    @JsonProperty("label")
    private String label;
    @JsonProperty("color")
    private String color;
    @JsonProperty("dataNode")
    private String dataNode;

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("shape")
    public String getShape() {
        return shape;
    }

    @JsonProperty("shape")
    public void setShape(String shape) {
        this.shape = shape;
    }

    @JsonProperty("image")
    public String getImage() {
        return image;
    }

    @JsonProperty("image")
    public void setImage(String image) {
        this.image = image;
    }

    @JsonProperty("label")
    public String getLabel() {
        return label;
    }

    @JsonProperty("label")
    public void setLabel(String label) {
        this.label = label;
    }

    @JsonProperty("color")
    public String getColor() {
        return color;
    }

    @JsonProperty("color")
    public void setColor(String color) {
        this.color = color;
    }

    public String getDataNode() {
        return dataNode;
    }

    public void setDataNode(String dataNode) {
        this.dataNode = dataNode;
    }

    @Override
    public String toString() {
        return "{"+
                "\"id\":\"" + id + '\"' +
                ", \"shape\":\"" + shape + '\"' +
                ", \"image\":\"" + image + '\"' +
                ", \"label\":\"" + label + '\"' +
                ", \"color\":\"" + color + '\"' +
                ", \"dataNode\":" + dataNode +
                '}';
    }
}
