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
package org.onap.usecaseui.server.util.nsmf;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.Test;
import org.onap.usecaseui.server.bean.nsmf.resource.SlicingBusinessInfo;

public class NsmfCommonUtilTest {

    @Test
    public void testTimestamp2Time() {
        try {
            System.out.println(NsmfCommonUtil.timestamp2Time("2019-12-23 11:31:19"));
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testTime2Timestamp() {
        System.out.println(NsmfCommonUtil.time2Timestamp("1577071879000"));
    }

    @Test
    public void testGetPagedList() {
        List<SlicingBusinessInfo> slicingBusinessInfoList = new ArrayList<>();
        NsmfCommonUtil.getPagedList(slicingBusinessInfoList, 1, 100);

        List<SlicingBusinessInfo> slicingBusinessInfoListNull = null;
        NsmfCommonUtil.getPagedList(slicingBusinessInfoListNull, 1, 100);
    }
}
