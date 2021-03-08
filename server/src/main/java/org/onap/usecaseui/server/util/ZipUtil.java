package org.onap.usecaseui.server.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import org.apache.commons.io.IOUtils;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;
/**
 * Zip压缩工具
 * @author Master.Xia@zhenzhiguz.com
 * @version 1.0 Date:2016年8月26日11:01:39
 * @version 2.0 Date:2017年2月13日22:43:11
 */
public class ZipUtil {
    /**
     * 压缩文件
     * @param src 要压缩的文件或目录的绝对路径
     * @param zip 输出的压缩文件的绝对路径
     * @throws IOException
     */
    public static void zip(String src, String zip) throws IOException {
        zip(new File(src), new File(zip));
    }
    /**
     * 压缩文件
     * @param src 要压缩的文件或目录的绝对路径
     * @param zip 输出的压缩文件
     * @throws IOException
     */
    public static void zip(String src, File zip) throws IOException {
        zip(new File(src), zip);
    }
    /**
     * 压缩文件
     * @param src 要压缩的文件或目录
     * @param zip 输出的压缩文件的绝对路径
     * @throws IOException
     */
    public static void zip(File src, String zip) throws IOException {
        zip(src, new File(zip));
    }
    /**
     * 压缩文件
     * @param src 要压缩的文件或目录
     * @param zip 输出的压缩文件
     * @throws IOException
     */
    public static void zip(File src, File zip) throws IOException {
        List<ZipEntry> list = foreach(src);
        ZipOutputStream out = new ZipOutputStream(zip);
        for (ZipEntry en : list) {
            File fo = new File(src.getParent(), en.getName());
            out.putNextEntry(en);
            FileInputStream in = new FileInputStream(fo);
            byte[] buffer = new byte[1024*8];
            for(int len=0;(len=in.read(buffer))!=-1;){
                out.write(buffer, 0, len);
            }
            in.close();
            out.flush();
        }
        out.close();
    }
    /**
     * 解压缩
     * @param zip 需要解压的压缩文件
     * @param out 解压到的目录
     * @throws Exception
     */
    public static void unzip(String zip,String out) throws Exception {
        unzip(new File(zip), new File(out));
    }
    /**
     * 解压缩
     * @param zip 需要解压的压缩文件
     * @param out 解压到的目录
     * @throws Exception
     */
    public static void unzip(String zip,File out) throws Exception {
        unzip(new File(zip), out);
    }
    /**
     * 解压缩
     * @param zip 需要解压的压缩文件
     * @param out 解压到的目录
     * @throws Exception
     */
    public static void unzip(File zip,String out) throws Exception {
        unzip(zip, new File(out));
    }
    /**
     * 解压缩
     * @param zip 需要解压的压缩文件
     * @param out 解压到的目录
     * @throws Exception
     */
    public static void unzip(File zip,File out) throws Exception {
        ZipFile zipFile = new ZipFile(zip,"GB18030");
        for (Enumeration<ZipEntry> entries = zipFile.getEntries(); entries.hasMoreElements();) {
            ZipEntry entry = entries.nextElement();
            File file = new File(out,entry.getName());
            if (entry.isDirectory()) {
                file.mkdirs();
            } else {
                File parent = file.getParentFile();
                if (!parent.exists()) {
                    parent.mkdirs();
                }
                IOUtils.copy(zipFile.getInputStream(entry), new FileOutputStream(file));
            }
        }
        zipFile.close();
    }
    private static List<ZipEntry> foreach(File file) {
        return foreach(file, "");
    }
    private static List<ZipEntry> foreach(File file, String path) {
        List<ZipEntry> list = new ArrayList<ZipEntry>();
        if (file.isDirectory()) {
            path += file.getName() + File.separator;
            for (File fo : file.listFiles()) {
                list.addAll(foreach(fo, path));
            }
        } else if (file.isFile()) {
            list.add(new ZipEntry(path + file.getName()));
        }
        return list;
    }
}
