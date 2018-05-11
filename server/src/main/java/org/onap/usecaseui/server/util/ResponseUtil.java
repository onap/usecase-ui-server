/**
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
