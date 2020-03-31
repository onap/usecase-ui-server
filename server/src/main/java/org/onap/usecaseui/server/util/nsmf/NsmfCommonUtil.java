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
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.onap.usecaseui.server.bean.nsmf.common.PagedResult;
import org.onap.usecaseui.server.constant.nsmf.NsmfParamConstant;


public class NsmfCommonUtil {

    public static String timestamp2Time(String timeStamp) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(NsmfParamConstant.DATE_FORMAT);
        Date date = simpleDateFormat.parse(timeStamp);
        long time = date.getTime();
        return String.valueOf(time);
    }

    public static String time2Timestamp(String time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(NsmfParamConstant.DATE_FORMAT);
        long longTime = Long.parseLong(time);
        Date date = new Date(longTime);
        return simpleDateFormat.format(date);
    }

    public static <T> PagedResult getPagedList(List<T> list, int pageNo, int pageSize) {
        if (list == null || pageNo < 1 || (pageNo - 1) * pageSize > list.size()) {
            return new PagedResult(0, Collections.emptyList());
        }
        list = list.stream().skip((pageNo - 1) * (long)pageSize).limit(pageSize).collect(Collectors.toList());
        return new PagedResult(list.size(), list );
    }
}
