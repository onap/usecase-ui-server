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




    public static String initDate(Date d,int year,int month,int day,int hour,int minute,int second) throws ParseException {
        Instant instant = d.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime ldt = LocalDateTime.ofInstant(instant,zone);
        if(year >= 0){
            if(year == 0)
                ldt = ldt.withYear(1);
        } else
            ldt = ldt.withYear(ldt.getYear()-1);
        if(month >= 0){
            if(month == 0)
                ldt = ldt.withMonth(1);
        } else
            ldt = ldt.withMonth(ldt.getMonthValue()-1);
        if(day >= 0){
            if(day == 0)
                ldt = ldt.withDayOfMonth(1);
        } else
            ldt = ldt.withDayOfMonth(ldt.getDayOfMonth()-1<1?ldt.getDayOfMonth():ldt.getDayOfMonth()+-1);
        if(hour >= 0){
            if(hour == 0)
                ldt = ldt.withHour(0);
        } else
            ldt = ldt.withHour(ldt.getHour()-1);
        if(minute >= 0){
            if(minute == 0)
                ldt = ldt.withMinute(0);
        } else
            ldt = ldt.withMinute(ldt.getMinute()-1);
        if(second >= 0){
            if(second == 0)
                ldt = ldt.withSecond(0);
        } else
            ldt = ldt.withMinute(ldt.getSecond()-1);
        return ldt.format(DateTimeFormatter.ofPattern(Constant.DATE_FORMAT));
    }




        /**
         * param except data ,if int equal 0 then cleared zero,
         * if equal -1 then minus 1 ,else unchanging
         * @param d
         * @param year
         * @param month
         * @param hour
         * @param minute
         * @param second
         * @return
         * @throws ParseException
         */
    public static Date initProcessDate(Date d,int year,int month,int day,int hour,int minute,int second) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(Constant.DATE_FORMAT);
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        if(year >= 0){
            if(year == 0)
                c.set(Calendar.YEAR,0);
        } else
            c.set(Calendar.YEAR,c.get(Calendar.YEAR)-1);
        if(month >= 0){
            if(month == 0)
                c.set(Calendar.MONTH,0);
        } else
            c.set(Calendar.MONTH,c.get(Calendar.MONTH)-1);
        if(day >= 0){
            if(day == 0)
                c.set(Calendar.DAY_OF_MONTH,0);
        } else
            c.set(Calendar.DAY_OF_MONTH,c.get(Calendar.DAY_OF_MONTH)-1);
        if(hour >= 0){
            if(hour == 0)
                c.set(Calendar.HOUR_OF_DAY,0);
        } else
            c.set(Calendar.HOUR_OF_DAY,c.get(Calendar.HOUR_OF_DAY)-1);
        if(minute >= 0){
            if(minute == 0)
                c.set(Calendar.MINUTE,0);
        } else
            c.set(Calendar.MINUTE,c.get(Calendar.MINUTE)-1);
        if(second >= 0){
            if(second == 0)
                c.set(Calendar.SECOND,0);
        } else
            c.set(Calendar.SECOND,c.get(Calendar.SECOND)-1);
        return sdf.parse(sdf.format(c.getTime()));
    }

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
