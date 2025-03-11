/**
 * Copyright 2016-2017 CMCC Corporation.
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

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import jakarta.servlet.http.HttpServletRequest;

import com.google.common.base.Throwables;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UuiCommonUtil {
	
	
	private static final Logger logger = LoggerFactory.getLogger(UuiCommonUtil.class);
	/**
	 * 
	  * getUUID
	  * 
	  * @Auther YYY
	  * @Date   2018/4/26
	  * @Title: getUUID
	  * @Description: 
	  * @return
	 */
	public static String getUUID() {
		UUID uuid = UUID.randomUUID();
		String str = uuid.toString();
		return str.substring(0, 8) + str.substring(9, 13)
				+ str.substring(14, 18) + str.substring(19, 23)
				+ str.substring(24);
	}
	
	/**
	 * 
	  * isNotNullOrEmpty
	  * 
	  * @Auther YYY
	  * @Date   2018/5/4
	  * @Title: isNotNullOrEmpty
	  * @Description: 
	  * @param obj
	  * @return
	 */
	public static boolean isNotNullOrEmpty(Object obj) {
		if(null == obj) {
			return false;
		}
		if(obj instanceof Collection collection) {
			if (collection.size() == 0) {
				return false;
			}
		} else if (obj instanceof String string) {
			if (string.trim().equals("")) {
				return false;
			}
		} else if (obj instanceof Map map) {
			if (map.size() == 0) {
				return false;
			}
		} else if (obj.getClass().isArray()) {
			if(Array.getLength(obj) == 0) {
				return false;
			}
		} else if (obj instanceof StringBuffer buffer) {
			if (buffer.length() == 0) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 
	  * OutRequestBody
	  * 
	  * @Auther YYY
	  * @Date   2018/5/9
	  * @Title: OutRequestBody
	  * @Description: 
	  * @param request
	  * @return
	 */
	public static String OutRequestBody(HttpServletRequest request){
		BufferedReader br;
		String str, wholeStr = "";
		try {
			br = request.getReader();
			while((str = br.readLine()) != null){
			wholeStr += str;
			}
		} catch (IOException e) {
			logger.error("exception occurred while performing UuiCommonUtil OutRequestBody.Details:"+ e.getMessage());
			logger.error("exception occurred while performing UuiCommonUtil OutRequestBody.Details:"+ Throwables.getStackTraceAsString(e));
		}
		return wholeStr;
	}
	
	public static boolean checkNumber(String num,String format){
		boolean result = false;
		if(isNotNullOrEmpty(num)){
			if(num.matches(format)){
				result = true;
			}
		}
		return result;
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List getPageList(List list,int currentPage,int pageSize){
        
		List listPages = new ArrayList();
        int currIdx = (currentPage > 1 ? (currentPage -1) * pageSize : 0);
        for (int i = 0; i < pageSize && i < list.size() - currIdx; i++) {
            Object  listPage= list.get(currIdx + i);
            listPages.add(listPage);
        }
		return listPages;
	}
	
    public static boolean isExistFile(String path) {
    	 
        if (null == path || "".equals(path.trim())) {
            return false;
        }
 
        File targetFile = new File(path);
        return targetFile.exists();
    }
    
    public static String readJsonFile(String path) throws IOException{

		File file = new File(path);
		String content= FileUtils.readFileToString(file,"UTF-8");
		return content;
	}
}
