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

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.aspectj.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.List;


public class CSVUtils {
    //CSV文件分隔符
    private final static String NEW_LINE_SEPARATOR="\n";
    private static Logger logger = LoggerFactory.getLogger(CSVUtils.class);


    /**写入csv文件
     * @param headers 列头
     * @param data 数据内容
     * @param filePath 创建的csv文件路径
     * **/
    public static void writeCsv(String[] headers,List<String[]> data,String filePath) {
        try{
            CSVFormat formator = CSVFormat.DEFAULT.withHeader(headers);
            String dir = filePath.substring(0,filePath.lastIndexOf('/'));
            File file = new File(dir);
            if (!file.exists())
                 file.mkdirs();
            try(Writer writer = new FileWriter(filePath);
                CSVPrinter printer = new CSVPrinter(writer,formator)){
                for(String[] str : data){
                    printer.printRecord(str);
                }
            }

            logger.info("CSV File Generate Success,FilePath:"+filePath);
        }catch (IOException e){
            logger.error("CSV File Generate Failure:"+e.getMessage());
        }
    }

    /**读取csv文件
     * @param filePath 文件路径
     * @param headers csv列头
     * @return CSVRecord 列表
     * @throws IOException **/
    public static List<CSVRecord> readCSV(String filePath, String[] headers) throws IOException{
        //创建CSVFormat
        CSVFormat formator = CSVFormat.DEFAULT.withHeader(headers);
        FileReader fileReader=new FileReader(filePath);
        //创建CSVParser对象
        CSVParser parser=new CSVParser(fileReader,formator);
        List<CSVRecord> records=parser.getRecords();
        parser.close();
        fileReader.close();
        return records;
    }

}
