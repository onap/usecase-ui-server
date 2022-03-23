/*
 * Copyright (C) 2017 CTC, Inc. and others. All rights reserved.
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
package org.onap.usecaseui.server.service.intent;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.onap.usecaseui.server.bean.intent.CCVPNInstance;
import org.onap.usecaseui.server.bean.intent.IntentInstance;
import org.onap.usecaseui.server.bean.nsmf.common.ServiceResult;
import org.onap.usecaseui.server.util.Page;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface IntentInstanceService {
    Page<CCVPNInstance> queryIntentInstance(CCVPNInstance instance, int currentPage, int pageSize);
    int createCCVPNInstance(CCVPNInstance instance);
    void getIntentInstanceProgress();
    void getIntentInstanceCreateStatus();
    List<CCVPNInstance> getFinishedInstanceInfo();
    void getIntentInstanceBandwidth() throws IOException;

    void deleteIntentInstance(String instanceId);

    void activeIntentInstance(String instanceId);

    void invalidIntentInstance(String instanceId);

    Map<String, Object> queryInstancePerformanceData(String instanceId);

    Object queryAccessNodeInfo() throws IOException;

    JSONObject getInstanceStatus(JSONArray ids);

    String formatBandwidth(String strValue);

    String formatCloudPoint(String cloudPoint);

    String formatAccessPoint(String accessPoint);

    void addCustomer() throws IOException;

    IntentInstance createIntentInstance(Object body,String businessInstanceId, String businessInstance,  String type);

    void deleteIntent(int id);

    void verifyIntent(int id);

    Page<IntentInstance> getIntentInstanceList(int currentPage, int pageSize);

    ServiceResult createSlicingServiceWithIntent(Object slicingOrderBody);

    int updateCCVPNInstance(CCVPNInstance instance);
}
