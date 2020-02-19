/*
 * Copyright (C) 2019 CMCC, Inc. and others. All rights reserved.
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
package org.onap.usecaseui.server.service.nsmf;

import org.onap.usecaseui.server.bean.nsmf.common.ServiceResult;

public interface ResourceMgtService {

    ServiceResult querySlicingBusiness(int pageNo, int pageSize);

    ServiceResult querySlicingBusinessByStatus(String processingStatus, int pageNo, int pageSize);

    ServiceResult querySlicingBusinessDetails(String businessId);

    ServiceResult queryNsiInstances(int pageNo, int pageSize);

    ServiceResult queryNsiInstancesByStatus(String instanceStatus, int pageNo, int pageSize);

    ServiceResult queryNsiDetails(String nsiId);

    ServiceResult queryNsiRelatedNssiInfo(String nsiId);

    ServiceResult queryNssiInstances(int pageNo, int pageSize);

    ServiceResult queryNssiInstancesByStatus(String instanceStatus, int pageNo, int pageSize);

    ServiceResult queryNssiInstancesByEnvironment(String environmentContext, int pageNo, int pageSize);

    ServiceResult queryNssiDetails(String nssiId);

    ServiceResult activateSlicingService(String serviceId);

    ServiceResult deactivateSlicingService(String serviceId);

    ServiceResult terminateSlicingService(String serviceId);

    ServiceResult queryOperationProgress(String serviceId);
}
