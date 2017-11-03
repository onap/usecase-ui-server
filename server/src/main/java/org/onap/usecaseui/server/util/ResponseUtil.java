package org.onap.usecaseui.server.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ResponseUtil {

    private static Logger logger = LoggerFactory.getLogger(ResponseUtil.class);

    public static boolean responseDownload(String filePath, HttpServletResponse response){
        if (null != response){
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/csv");
            response.setHeader("Content-Disposition","attachment;filename="+filePath);
            try(InputStream is = new FileInputStream(filePath);
                OutputStream os = response.getOutputStream()){
                byte[] b = new byte[2048];
                int length;
                while ((length = is.read(b)) > 0) {
                    os.write(b, 0, length);
                }
                return true;
            }catch (IOException e){
                logger.error("download csv File error :"+e.getMessage());
                return false;
            }
        }else{
            logger.error("csvFile generate success,but response is null,don't download to local");
            return false;
        }

    }

}
