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
public class PInterface {

	private String speedValue;

	private RelationshipList relationshipList;

	private String inMaint;

	private String interfaceType;

	private String portDescription;

	private String resourceVersion;

	private String networkRef;

	private String interfaceName;

	private String speedUnits;

	private String operationalStatus;

	private String networkInterfaceType;

	@JsonProperty("speed-value")
	public String getSpeedValue() {
		return speedValue;
	}

	public void setSpeedValue(String speedValue) {
		this.speedValue = speedValue;
	}

	@JsonProperty("relationship-list")
	public RelationshipList getRelationshipList() {
		return relationshipList;
	}

	public void setRelationshipList(RelationshipList relationshipList) {
		this.relationshipList = relationshipList;
	}

	@JsonProperty("in-maint")
	public String getInMaint() {
		return inMaint;
	}

	public void setInMaint(String inMaint) {
		this.inMaint = inMaint;
	}

	@JsonProperty("interface-type")
	public String getInterfaceType() {
		return interfaceType;
	}

	public void setInterfaceType(String interfaceType) {
		this.interfaceType = interfaceType;
	}

	@JsonProperty("port-description")
	public String getPortDescription() {
		return portDescription;
	}

	public void setPortDescription(String portDescription) {
		this.portDescription = portDescription;
	}

	@JsonProperty("resource-version")
	public String getResourceVersion() {
		return resourceVersion;
	}

	public void setResourceVersion(String resourceVersion) {
		this.resourceVersion = resourceVersion;
	}

	@JsonProperty("network-ref")
	public String getNetworkRef() {
		return networkRef;
	}

	public void setNetworkRef(String networkRef) {
		this.networkRef = networkRef;
	}

	@JsonProperty("interface-name")
	public String getInterfaceName() {
		return interfaceName;
	}

	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}

	@JsonProperty("speed-units")
	public String getSpeedUnits() {
		return speedUnits;
	}

	public void setSpeedUnits(String speedUnits) {
		this.speedUnits = speedUnits;
	}

	@JsonProperty("operational-status")
	public String getOperationalStatus() {
		return operationalStatus;
	}

	public void setOperationalStatus(String operationalStatus) {
		this.operationalStatus = operationalStatus;
	}

	@JsonProperty("network-interface-type")
	public String getNetworkInterfaceType() {
		return networkInterfaceType;
	}

	public void setNetworkInterfaceType(String networkInterfaceType) {
		this.networkInterfaceType = networkInterfaceType;
	}

}
