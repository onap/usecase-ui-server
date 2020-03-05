/**
 * Copyright 2020 Huawei Corporation.
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
package org.onap.usecaseui.server.constant;

import java.util.HashMap;
import java.util.Map;

import org.onap.usecaseui.server.bean.lcm.ServiceTemplateInput;

public final class Constant
{

    public static final String DATE_FORMAT= "yyyy-MM-dd HH:mm:ss";

    public static final String RegEX_DATE_FORMAT = "[^0-9-:]";
    
    public static Map<String,ServiceTemplateInput> netWorkMap  = new HashMap<String,ServiceTemplateInput>();
    
    public static final String CONSTANT_SUCCESS="{\"status\":\"SUCCESS\"}";
    
    public static final String CONSTANT_FAILED="{\"status\":\"FAILED\"}";
}
