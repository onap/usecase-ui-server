/**
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
package org.onap.usecaseui.server.constant;

import java.util.HashMap;
import java.util.Map;

import org.onap.usecaseui.server.bean.lcm.ServiceTemplateInput;

public final class CommonConstant
{
    public static final String DATE_FORMAT= "yyyy-MM-dd HH:mm:ss";

    public static final String RegEX_DATE_FORMAT = "[^0-9-:]";

    public static Map<String,ServiceTemplateInput> netWorkMap  = new HashMap<String,ServiceTemplateInput>();

    public static final String CONSTANT_SUCCESS="{\"status\":\"SUCCESS\"}";

    public static final String CONSTANT_FAILED="{\"status\":\"FAILED\"}";

    public static final String CREATING_CODE="1001";

    public static final String DELETING_CODE="1002";

    public static final String SCALING_CODE="1003";

    public static final String HEALING_CODE="1004";

    public static final String UPDATING_CODE="1005";

    public static final String SUCCESS_CODE="2001";

    public static final String FAIL_CODE="2002";

    public static final String IN_PROGRESS_CODE="2003";

    public static final String ENCODING_UTF8="utf-8";

    public static final String BLANK="";
}
