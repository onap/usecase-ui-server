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

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RelationshipData {

	private String relationshipValue;

	private String relationshipKey;

	@JsonProperty("relationship-key")
	public String getRelationshipValue() {
		return relationshipValue;
	}

	public void setRelationshipValue(String relationshipValue) {
		this.relationshipValue = relationshipValue;
	}

	@JsonProperty("relationship-value")
	public String getRelationshipKey() {
		return relationshipKey;
	}

	public void setRelationshipKey(String relationshipKey) {
		this.relationshipKey = relationshipKey;
	}

}
