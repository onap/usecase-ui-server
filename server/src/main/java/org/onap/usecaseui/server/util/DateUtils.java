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
import java.util.*;

public class DateUtils {


    /**
     * 获取两个日期之间的日期
     * @param start 开始日期
     * @param end 结束日期
     * @return 日期集合
     */
    public static List<Date> getBetweenDates(Date start, Date end) {
        List<Date> result = new ArrayList<Date>();
        Calendar tempStart = Calendar.getInstance();
        tempStart.setTime(start);
        tempStart.add(Calendar.DAY_OF_YEAR, 1);

        Calendar tempEnd = Calendar.getInstance();
        tempEnd.setTime(end);
        while (tempStart.before(tempEnd)) {
            result.add(tempStart.getTime());
            tempStart.add(Calendar.DAY_OF_YEAR, 1);
        }
        return result;
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

    public String getYearMonthDayHourMinuteSecond(long timeMillis) {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
        calendar.setTimeInMillis(timeMillis);
        int year=calendar.get(Calendar.YEAR);

        int month=calendar.get(Calendar.MONTH) + 1;
        String mToMonth=null;
        if (String.valueOf(month).length()==1) {
            mToMonth="0"+month;
        } else {
            mToMonth=String.valueOf(month);
        }

        int day=calendar.get(Calendar.DAY_OF_MONTH);
        String dToDay=null;
        if (String.valueOf(day).length()==1) {
            dToDay="0"+day;
        } else {
            dToDay=String.valueOf(day);
        }

        int hour=calendar.get(Calendar.HOUR_OF_DAY);
        String hToHour=null;
        if (String.valueOf(hour).length()==1) {
            hToHour="0"+hour;
        } else {
            hToHour=String.valueOf(hour);
        }

        int minute=calendar.get(Calendar.MINUTE);
        String mToMinute=null;
        if (String.valueOf(minute).length()==1) {
            mToMinute="0"+minute;
        } else {
            mToMinute=String.valueOf(minute);
        }

        int second=calendar.get(Calendar.SECOND);
        String sToSecond=null;
        if (String.valueOf(second).length()==1) {
            sToSecond="0"+second;
        } else {
            sToSecond=String.valueOf(second);
        }
        return  year+ "-" +mToMonth+ "-" +dToDay+ " "+hToHour+ ":" +mToMinute+ ":" +sToSecond;
    }



    public static void main(String[] args) {
        // System.out.println(new DateUtils().getYearMonthDayHourMinuteSecond(System.currentTimeMillis()));
        String startime_s = "2017-10-31";
        String endtime_s = "2017-12-24";
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date startime = null;
        Date endtime =null;
        try {
            startime = formatter.parse(startime_s);
            endtime = formatter.parse(endtime_s);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        List<Date> result =getBetweenDates(startime,endtime);

        System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaa===="+result);

    }

}
