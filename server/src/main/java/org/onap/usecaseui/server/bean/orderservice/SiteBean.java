/*
 * Copyright (C) 2017 CMCC, Inc. and others. All rights reserved.
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

import java.io.Serializable;

public class SiteBean implements Serializable {

    private String siteName;

    private boolean isCloudSite;

    private String postCode;

    private String address;

    private String capacity;

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public boolean isCloudSite() {
        return isCloudSite;
    }

    public void setCloudSite(boolean cloudSite) {
        isCloudSite = cloudSite;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    @Override
    public String toString() {
        return "SiteBean{" +
                "siteName='" + siteName + '\'' +
                ", isCloudSite=" + isCloudSite +
                ", postCode='" + postCode + '\'' +
                ", address='" + address + '\'' +
                ", capacity='" + capacity + '\'' +
                '}';
    }

}
