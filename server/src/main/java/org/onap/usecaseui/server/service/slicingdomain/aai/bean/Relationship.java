/**
 * Copyright 2016-2017 ZTE Corporation.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package org.onap.usecaseui.server.service.slicingdomain.aai.bean;

import java.util.List;

public class Relationship {


    private String relatedTo;

    private String relationshipLabel;

    private String relatedLink;

    private List<RelationshipData> relationshipData;

    private List<RelatedToProperty> relatedToProperty;

    public String getRelatedTo() {
        return relatedTo;
    }

    public void setRelatedTo(String relatedTo) {
        this.relatedTo = relatedTo;
    }

    public String getRelationshipLabel() {
        return relationshipLabel;
    }

    public void setRelationshipLabel(String relationshipLabel) {
        this.relationshipLabel = relationshipLabel;
    }

    public String getRelatedLink() {
        return relatedLink;
    }

    public void setRelatedLink(String relatedLink) {
        this.relatedLink = relatedLink;
    }

    public List<RelationshipData> getRelationshipData() {
        return relationshipData;
    }

    public void setRelationshipData(List<RelationshipData> relationshipData) {
        this.relationshipData = relationshipData;
    }

    public List<RelatedToProperty> getRelatedToProperty() {
        return relatedToProperty;
    }

    public void setRelatedToProperty(List<RelatedToProperty> relatedToProperty) {
        this.relatedToProperty = relatedToProperty;
    }


}
