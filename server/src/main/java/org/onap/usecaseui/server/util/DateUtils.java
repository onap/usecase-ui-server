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
package org.onap.usecaseui.server.util;

import org.onap.usecaseui.server.constant.Constant;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {




    /**
     *
     * @param d
     * @param unit time unit
     * @param addVal add value
     * @return
     * @throws ParseException
     */
    public static String addDate(Date d,String unit,int addVal) throws ParseException {
        Instant instant = d.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime ldt = LocalDateTime.ofInstant(instant,zone);
        switch (unit.toLowerCase()){
            case "year":
                ldt = ldt.withYear(ldt.getYear()+addVal);
                break;
            case "month":
                if (ldt.getMonthValue() + addVal < 13) {
                    ldt = ldt.withMonth(ldt.getMonthValue() + addVal);
                }else{
                    ldt = ldt.withYear(ldt.getYear()+1).withMonth(1);
                }
                break;
            case "day":
                if(ldt.getDayOfMonth()+addVal <= ldt.getMonth().length((ldt.getYear()%4==0?true:false))){
                    ldt = ldt.withDayOfMonth(ldt.getDayOfMonth()+addVal);
                }else{
                    ldt = ldt.withMonth(ldt.getMonthValue()+1).withDayOfMonth(1);
                }
                break;
            case "hour":
                if (ldt.getHour()+addVal < 24)
                    ldt = ldt.withHour(ldt.getHour()+addVal);
                else{
                    ldt = ldt.withDayOfMonth(ldt.getDayOfMonth()+1).withHour(1);
                }
                break;
            case "minute":
                if (ldt.getMinute()+addVal < 60)
                    ldt = ldt.withMinute(ldt.getMinute()+addVal);
                else{
                    ldt = ldt.withMinute(0).withHour(ldt.getHour()+1);
                }
                break;
        }
        return ldt.format(DateTimeFormatter.ofPattern(Constant.DATE_FORMAT));
    }

    public static String dateToString(Date d){
        return new SimpleDateFormat(Constant.DATE_FORMAT).format(d);
    }

    public static Date stringToDate(String d) throws ParseException {
        return new SimpleDateFormat(Constant.DATE_FORMAT).parse(d);
    }

    public static Date now() throws ParseException {
        return new SimpleDateFormat(Constant.DATE_FORMAT).parse(dateToString(new Date()));
    }

}
