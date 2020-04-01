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

import org.onap.usecaseui.server.service.lcm.domain.sdc.bean.SDCServiceTemplate;
import org.onap.usecaseui.server.service.lcm.domain.sdc.bean.Vnf;

import java.util.List;
import java.util.Objects;

public class VfNsPackageInfo {

    private List<SDCServiceTemplate> nsPackage;

    private List<Vnf> vnfPackages;

    public VfNsPackageInfo(List<SDCServiceTemplate> nsPackage, List<Vnf> vnfPackages) {
        this.nsPackage = nsPackage;
        this.vnfPackages = vnfPackages;
    }

    public List<SDCServiceTemplate> getNsPackage() {
        return nsPackage;
    }

    public List<Vnf> getVnfPackages() {
        return vnfPackages;
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
        VfNsPackageInfo that = (VfNsPackageInfo) o;
        return Objects.equals(nsPackage, that.nsPackage) &&
                Objects.equals(vnfPackages, that.vnfPackages);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nsPackage, vnfPackages);
    }
}
