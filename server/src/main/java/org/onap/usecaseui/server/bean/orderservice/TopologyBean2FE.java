/**
 * Copyright 2018-2019 Huawei.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.onap.usecaseui.server.bean.orderservice;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "nodes",
        "edges"
})
public class TopologyBean2FE {
    @JsonProperty("nodes")
    private List<Node> nodes = null;
    @JsonProperty("edges")
    private List<Edge> edges = null;

    @JsonProperty("nodes")
    public List<Node> getNodes() {
        return nodes;
    }

    @JsonProperty("nodes")
    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }

    @JsonProperty("edges")
    public List<Edge> getEdges() {
        return edges;
    }

    @JsonProperty("edges")
    public void setEdges(List<Edge> edges) {
        this.edges = edges;
    }

    @Override
    public String toString() {
        return "{" +
                //"\"nodes\":" + nodes + '\"' +
                "\"nodes\":" + nodes +
                //", \"edges\":" + edges + '\"' +
                ", \"edges\":" + edges +
                '}';
    }
}

