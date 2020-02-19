/**
 *  * Copyright 2019-2020 Fujitsu.
 *   *
 *    * Licensed under the Apache License, Version 2.0 (the "License");
 *     * you may not use this file except in compliance with the License.
 *      * You may obtain a copy of the License at
 *       *
 *        *     http://www.apache.org/licenses/LICENSE-2.0
 *         *
 *          * Unless required by applicable law or agreed to in writing, software
 *           * distributed under the License is distributed on an "AS IS" BASIS,
 *            * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *             * See the License for the specific language governing permissions and
 *              * limitations under the License.
 *               */

package org.onap.usecaseui.server.service.lcm.domain.aai.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Relationship {

	private String relatedTo;

	private RelationshipData[] relationshipData;

	private String relatedLink;

	private String relationshipLabel;

	@JsonProperty("related-to")
	public String getRelatedTo() {
		return relatedTo;
	}

	public void setRelatedTo(String relatedTo) {
		this.relatedTo = relatedTo;
	}

	@JsonProperty("relationship-data")
	public RelationshipData[] getRelationshipData() {
		return relationshipData;
	}

	public void setRelationshipData(RelationshipData[] relationshipData) {
		this.relationshipData = relationshipData;
	}

	@JsonProperty("related-link")
	public String getRelatedLink() {
		return relatedLink;
	}

	public void setRelatedLink(String relatedLink) {
		this.relatedLink = relatedLink;
	}

	@JsonProperty("relationship-label")
	public String getRelationshipLabel() {
		return relationshipLabel;
	}

	public void setRelationshipLabel(String relationshipLabel) {
		this.relationshipLabel = relationshipLabel;
	}

}
