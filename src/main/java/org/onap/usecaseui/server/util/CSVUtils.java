package org.onap.usecaseui.server.util;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
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
