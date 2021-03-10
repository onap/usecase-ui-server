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

import java.util.List;

import org.onap.usecaseui.server.bean.intent.IntentModel;

public interface IntentService {
    public String addModel(IntentModel model);
    List<IntentModel> listModels();
    public String deleteModel(String modelId);
    public IntentModel getModel(String modelId);
    public IntentModel activeModel(String modelId);
    String activeModelFile(IntentModel model);
    String calcFieldValue(String key, String strValue);
}
