/*
 * Copyright (C) 2018 CMCC, Inc. and others. All rights reserved.
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.onap.usecaseui.server.bean.HttpRequestHeader;
import org.onap.usecaseui.server.bean.HttpResponseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.onap.usecaseui.server.constant.CommonConstant.BLANK;
import static org.onap.usecaseui.server.constant.CommonConstant.ENCODING_UTF8;
import static org.onap.usecaseui.server.constant.HttpConstant.*;

public class HttpUtil {
    private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    public static HttpResponseResult sendPostByMap(
            String url,
            HttpRequestHeader httpRequestHeader,
            Map<String, String> requestBodyMap) {
        HttpResponseResult reponseResult = new HttpResponseResult(HttpStatus.SC_NOT_FOUND, BLANK);
        logger.info("[" + url + "]" + " API POST calling is starting......");

        try {
            // set request url and header for API calling
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(url);
            setHttpPostHeader(httpPost, httpRequestHeader);

            // set request body for API calling
            List<NameValuePair> nvp = new ArrayList<NameValuePair>();
            if (requestBodyMap != null) {
                for (Map.Entry<String, String> entry : requestBodyMap.entrySet()) {
                    nvp.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
                }
            }
            httpPost.setEntity(new UrlEncodedFormEntity(nvp, ENCODING_UTF8));

            // execute API calling and return response
            CloseableHttpResponse response = httpClient.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                reponseResult.setResultContent(EntityUtils.toString(response.getEntity(), ENCODING_UTF8));
            }
            reponseResult.setResultCode(response.getStatusLine().getStatusCode());
            response.close();
        } catch (ClientProtocolException cpe) {
            logger.error(cpe.toString());
            cpe.printStackTrace();
        } catch (IOException ioe) {
            logger.error(ioe.toString());
            ioe.printStackTrace();
        }

        logger.info("[" + url + "]" + " API POST calling has finished!");
        return reponseResult;
    }

    public static HttpResponseResult sendPostByJson(
            String url,
            HttpRequestHeader httpRequestHeader,
            String requestBodyJson) {
        HttpResponseResult reponseResult = new HttpResponseResult(HttpStatus.SC_NOT_FOUND, BLANK);
        logger.info("[" + url + "]" + " API POST calling is starting......");

        try {
            // set request url and header for API calling
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(url);
            setHttpPostHeader(httpPost, httpRequestHeader);

            // set request body for API calling
            StringEntity se = new StringEntity(requestBodyJson, ContentType.APPLICATION_JSON);
            se.setContentEncoding(ENCODING_UTF8);
            httpPost.setEntity(se);

            // execute API calling and return response
            CloseableHttpResponse response = httpClient.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                reponseResult.setResultContent(EntityUtils.toString(response.getEntity(), ENCODING_UTF8));
            }
            response.close();
        } catch (ClientProtocolException cpe) {
            logger.error(cpe.toString());
            cpe.printStackTrace();
        } catch (IOException ioe) {
            logger.error(ioe.toString());
            ioe.printStackTrace();
        }

        logger.info("[" + url + "]" + " API POST calling has finished!");
        return reponseResult;
    }

    private static void setHttpPostHeader(HttpPost httpPost, HttpRequestHeader httpRequestHeader) {
        httpPost.setHeader(HTTP_AUTHORIZATION, httpRequestHeader.getStrAuthorization());
        httpPost.setHeader(HTTP_X_FROMAPP_ID, httpRequestHeader.getStrFromAppId());
        httpPost.setHeader(HTTP_X_TRANSACTION_ID, httpRequestHeader.getStrTransactionId());
        httpPost.setHeader(HTTP_CONTENT_TYPE, httpRequestHeader.getStrContentType());
        httpPost.setHeader(HTTP_ACCEPT, httpRequestHeader.getStrAccept());
    }
}
