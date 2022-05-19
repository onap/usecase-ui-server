/*
 * Copyright (C) 2022 CTC, Inc. and others. All rights reserved.
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

public final class IntentConstant {
    public final static String UPLOADPATH = "/home/uui/upload/";
    public final static String NLPLOADPATH = "/home/uuihome/uui/bert-master/upload/";
    public final static String[] QUESTIONS_CCVPN = {"bandwidth", "access point", "cloud point"};
    public final static String[] QUESTIONS_5GS = {"Communication Service Name", "Max Number of UEs", "Data Rate Downlink", "Latency", "Data Rate Uplink", "Resource Sharing Level", "Mobility", "Area"};

    public final static String MODEL_TYPE_CCVPN = "ccvpn";
    public final static String MODEL_TYPE_5GS = "5gs";

    public final static String INTENT_INSTANCE_ID_PREFIX = "IBN";
    public final static String INTENT_INSTANCE_DATA_OWNER = "UUI";

    public final static String NLP_HOST = "http://uui-nlp";
    public final static String NLP_ONLINE_URL_BASE = NLP_HOST+":33011";
    public final static String NLP_OFFLINE_URL_BASE = NLP_HOST+":33012";
    public final static String NLP_FILE_URL_BASE = NLP_HOST+":33013";

    public static Map<String, String> NetWorkNodeAlias = new HashMap<>();
}
