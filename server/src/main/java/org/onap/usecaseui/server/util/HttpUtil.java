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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.onap.usecaseui.server.bean.HttpResponseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

import static org.onap.usecaseui.server.constant.CommonConstant.BLANK;
import static org.onap.usecaseui.server.constant.CommonConstant.ENCODING_UTF8;

public class HttpUtil {
    private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    /**
     * common POST method for REST API calling by using map request body
     *
     * @param url
     * @param headerMap
     * @param requestBodyMap
     * @return HttpResponseResult
     */
    public static HttpResponseResult sendPostRequestByMap(
            String url,
            Map<String, String> headerMap,
            Map<String, String> requestBodyMap) {
        logger.info("[" + url + "]" + " API POST calling is starting......");
        HttpResponseResult responseResult = new HttpResponseResult(HttpStatus.SC_NOT_FOUND, BLANK);
        CloseableHttpClient httpClient = HttpClients.createDefault();

        try {
            // set request url and header for API calling
            HttpPost httpPost = new HttpPost(url);
            setHeader(httpPost, headerMap);

            // set request body for API calling
            httpPost.setEntity(setBodyByMap(requestBodyMap));

            // execute API calling and set response
            CloseableHttpResponse response = httpClient.execute(httpPost);
            setResponse(response, responseResult);
        } catch (ClientProtocolException cpe) {
            logger.error(cpe.toString());
            cpe.printStackTrace();
        } catch (IOException ioe) {
            logger.error(ioe.toString());
            ioe.printStackTrace();
        } catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
            } catch (Exception e) {
                logger.error(e.toString());
                e.printStackTrace();
            }
        }

        logger.info("[" + url + "]" + " API POST calling has finished!");
        return responseResult;
    }

    /**
     * common POST method for REST API calling by using json request body
     *
     * @param url
     * @param headerMap
     * @param requestBodyJson
     * @return HttpResponseResult
     */
    public static HttpResponseResult sendPostRequestByJson(
            String url,
            Map<String, String> headerMap,
            String requestBodyJson) {
        logger.info("[" + url + "]" + " API POST calling is starting......");
        HttpResponseResult responseResult = new HttpResponseResult(HttpStatus.SC_NOT_FOUND, BLANK);
        CloseableHttpClient httpClient = HttpClients.createDefault();

        try {
            // set request url and header for API calling
            HttpPost httpPost = new HttpPost(url);
            setHeader(httpPost, headerMap);

            // set request body for API calling
            httpPost.setEntity(setBodyByJson(requestBodyJson));

            // execute API calling and return response
            CloseableHttpResponse response = httpClient.execute(httpPost);
            setResponse(response, responseResult);
        } catch (ClientProtocolException cpe) {
            logger.error(cpe.toString());
            cpe.printStackTrace();
        } catch (IOException ioe) {
            logger.error(ioe.toString());
            ioe.printStackTrace();
        } catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
            } catch (Exception e) {
                logger.error(e.toString());
                e.printStackTrace();
            }
        }

        logger.info("[" + url + "]" + " API POST calling has finished!");
        return responseResult;
    }

    /**
     * common GET method for REST API calling
     *
     * @param url
     * @param headerMap
     * @return HttpResponseResult
     */
    public HttpResponseResult sendGetRequest(
            String url,
            Map<String, String> headerMap) {
        logger.info("[" + url + "]" + " API GET calling is starting......");
        HttpResponseResult responseResult = new HttpResponseResult(HttpStatus.SC_NOT_FOUND, BLANK);
        CloseableHttpClient httpClient = HttpClients.createDefault();

        try {
            // set request url and header for API calling
            HttpGet httpGet = new HttpGet(url);
            setHeader(httpGet, headerMap);

            // execute API calling and return response
            CloseableHttpResponse response = httpClient.execute(httpGet);
            setResponse(response, responseResult);
        } catch (ClientProtocolException cpe) {
            logger.error(cpe.toString());
            cpe.printStackTrace();
        } catch (IOException ioe) {
            logger.error(ioe.toString());
            ioe.printStackTrace();
        } catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
            } catch (Exception e) {
                logger.error(e.toString());
                e.printStackTrace();
            }
        }

        logger.info("[" + url + "]" + " API GET calling has finished!");
        return responseResult;
    }

    /**
     * common PUT method for REST API calling by using map request body
     *
     * @param url            AAAAA
     * @param headerMap      AAAAA
     * @param requestBodyMap AAAAA
     * @return HttpResponseResult
     */
    public static HttpResponseResult sendPutRequestByMap(
            String url,
            Map<String, String> headerMap,
            Map<String, String> requestBodyMap) {
        logger.info("[" + url + "]" + " API PUT calling is starting......");
        HttpResponseResult responseResult = new HttpResponseResult(HttpStatus.SC_NOT_FOUND, BLANK);
        CloseableHttpClient httpClient = HttpClients.createDefault();

        try {
            // set request url and header for API calling
            HttpPut httpPut = new HttpPut(url);
            setHeader(httpPut, headerMap);

            // set request body for API calling
            httpPut.setEntity(setBodyByMap(requestBodyMap));

            // execute API calling and set response
            CloseableHttpResponse response = httpClient.execute(httpPut);
            setResponse(response, responseResult);
        } catch (ClientProtocolException cpe) {
            logger.error(cpe.toString());
            cpe.printStackTrace();
        } catch (IOException ioe) {
            logger.error(ioe.toString());
            ioe.printStackTrace();
        } catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
            } catch (Exception e) {
                logger.error(e.toString());
                e.printStackTrace();
            }
        }

        logger.info("[" + url + "]" + " API PUT calling has finished!");
        return responseResult;
    }

    /**
     * common PUT method for REST API calling by using json request body
     *
     * @param url
     * @param headerMap
     * @param requestBodyJson
     * @return HttpResponseResult
     */
    public static HttpResponseResult sendPutRequestByJson(
            String url,
            Map<String, String> headerMap,
            String requestBodyJson) {
        logger.info("[" + url + "]" + " API PUT calling is starting......");
        HttpResponseResult responseResult = new HttpResponseResult(HttpStatus.SC_NOT_FOUND, BLANK);
        CloseableHttpClient httpClient = HttpClients.createDefault();

        try {
            // set request url and header for API calling
            HttpPut httpPut = new HttpPut(url);
            setHeader(httpPut, headerMap);

            // set request body for API calling
            httpPut.setEntity(setBodyByJson(requestBodyJson));

            // execute API calling and return response
            CloseableHttpResponse response = httpClient.execute(httpPut);
            setResponse(response, responseResult);
        } catch (ClientProtocolException cpe) {
            logger.error(cpe.toString());
            cpe.printStackTrace();
        } catch (IOException ioe) {
            logger.error(ioe.toString());
            ioe.printStackTrace();
        } catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
            } catch (Exception e) {
                logger.error(e.toString());
                e.printStackTrace();
            }
        }

        logger.info("[" + url + "]" + " API PUT calling has finished!");
        return responseResult;
    }

    /**
     * common DELETE method for REST API calling
     *
     * @param url
     * @param headerMap
     * @return HttpResponseResult
     */
    public HttpResponseResult sendDeleteRequest(
            String url,
            Map<String, String> headerMap) {
        logger.info("[" + url + "]" + " API DELETE calling is starting......");
        HttpResponseResult responseResult = new HttpResponseResult(HttpStatus.SC_NOT_FOUND, BLANK);
        CloseableHttpClient httpClient = HttpClients.createDefault();

        try {
            // set request url and header for API calling
            HttpDelete httpDelete = new HttpDelete(url);
            setHeader(httpDelete, headerMap);

            // execute API calling and return response
            CloseableHttpResponse response = httpClient.execute(httpDelete);
            setResponse(response, responseResult);
        } catch (ClientProtocolException cpe) {
            logger.error(cpe.toString());
            cpe.printStackTrace();
        } catch (IOException ioe) {
            logger.error(ioe.toString());
            ioe.printStackTrace();
        } catch (Exception e) {
            logger.error(e.toString());
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
            } catch (Exception e) {
                logger.error(e.toString());
                e.printStackTrace();
            }
        }

        logger.info("[" + url + "]" + " API DELETE calling has finished!");
        return responseResult;
    }

    /**
     * get string content from request body
     *
     * @param request
     * @return String
     */
    public static String ReadAsChars(HttpServletRequest request) {
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder(BLANK);

        try {
            br = request.getReader();
            String tempString;
            while ((tempString = br.readLine()) != null) {
                sb.append(tempString);
            }
            br.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            if (null != br) {
                try {
                    br.close();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        }

        return sb.toString();
    }

    private static void setHeader(HttpRequestBase request, Map<String, String> headerMap) {
        if (headerMap != null) {
            Set<String> keySet = headerMap.keySet();
            for (String key : keySet) {
                request.addHeader(key, headerMap.get(key));
            }
        }
    }

    private static UrlEncodedFormEntity setBodyByMap(Map<String, String> requestBodyMap) throws UnsupportedEncodingException {
        List<NameValuePair> nvp = new ArrayList<>();
        if (requestBodyMap != null) {
            for (Map.Entry<String, String> entry : requestBodyMap.entrySet()) {
                nvp.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
        }
        return new UrlEncodedFormEntity(nvp, ENCODING_UTF8);
    }

    private static StringEntity setBodyByJson(String requestBodyJson) {
        StringEntity se = new StringEntity(requestBodyJson, ContentType.APPLICATION_JSON);
        se.setContentEncoding(ENCODING_UTF8);
        return se;
    }

    private static void setResponse(CloseableHttpResponse response, HttpResponseResult responseResult) throws IOException {
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            responseResult.setResultContent(EntityUtils.toString(response.getEntity(), ENCODING_UTF8));
        }
        responseResult.setResultCode(response.getStatusLine().getStatusCode());
        response.close();
    }
}
