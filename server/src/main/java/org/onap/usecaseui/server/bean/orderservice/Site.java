/*
 * Copyright (C) 2020 Huawei, Inc. and others. All rights reserved.
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

package org.onap.usecaseui.server.bean.orderservice;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Site {

    @JsonProperty("siteId")
    private String siteId;

    @JsonProperty("siteName")
    private String siteName;

    @JsonProperty("isCloudSite")
    private String isCloudSite;

    @JsonProperty("location")
    private String location;

    @JsonProperty("zipCode")
    private String zipCode;

    @JsonProperty("role")
    private String role;

    @JsonProperty("capacity")
    private String capacity;

    @JsonProperty("interface")
    private String siteinterface;

    @JsonProperty("subnet")
    private String subnet;

    @JsonProperty("price")
    private String price;

    @JsonProperty("siteStatus")
    private String siteStatus;

    @JsonProperty("description")
    private String description;

    @JsonProperty("site_address")
    private String address;

    @JsonProperty("site_email")
    private String email;

    @JsonProperty("site_type")
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getIsCloudSite() {
        return isCloudSite;
    }

    public void setIsCloudSite(String isCloudSite) {
        this.isCloudSite = isCloudSite;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getSiteinterface() {
        return siteinterface;
    }

    public void setSiteinterface(String siteinterface) {
        this.siteinterface = siteinterface;
    }

    public String getSubnet() {
        return subnet;
    }

    public void setSubnet(String subnet) {
        this.subnet = subnet;
    }

    public String getSiteStatus() {
        return siteStatus;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSiteStatus(String siteStatus) {
        this.siteStatus = siteStatus;
    }



    @Override
    public String toString() {
        return "Site{" +
                "siteId='" + siteId + '\'' +
                ", siteName='" + siteName + '\'' +
                ", isCloudSite='" + isCloudSite + '\'' +
                ", location='" + location + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", role='" + role + '\'' +
                ", capacity='" + capacity + '\'' +
                ", siteinterface='" + siteinterface + '\'' +
                ", subnet='" + subnet + '\'' +
                ", price='" + price + '\'' +
                ", siteStatus='" + siteStatus + '\'' +
                '}';
    }
}
