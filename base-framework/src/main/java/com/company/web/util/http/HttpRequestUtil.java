package com.company.web.util.http;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;

import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.json.JSONObject;

public class HttpRequestUtil {
    static Logger logger = LoggerFactory.getLogger(HttpRequestUtil.class);
    public static String doGet(String url, Map<String, String> param, Map<String, String> headers) {
        // 创建Httpclient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();
        //设置请求超时时间（各项超时参数具体含义链接）
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(10000)
                .setConnectionRequestTimeout(10000)
                .setSocketTimeout(10000)
                .build();

        String resultString = "";
        CloseableHttpResponse response = null;
        try {
            // 创建uri
            URIBuilder builder = new URIBuilder(url);
            if (param != null) {
                for (String key : param.keySet()) {
                    builder.addParameter(key, param.get(key));
                }
            }
            URI uri = builder.build();
            // 创建http GET请求
            HttpGet httpGet = new HttpGet(uri);
            //给这个请求设置请求配置
            httpGet.setConfig(requestConfig);
            httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; rv:6.0.2) Gecko/20100101 Firefox/6.0.2");
            if(headers != null){
                for(String headerKey:headers.keySet()){
                    httpGet.addHeader(headerKey, headers.get(headerKey));
                }
            }
            // 执行请求
            response = httpclient.execute(httpGet);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultString;
    }
    public static String doPost(String url, Map<String, String> param) {
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //设置请求超时时间
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(10000)
                .setConnectionRequestTimeout(10000)
                .setSocketTimeout(10000)
                .build();
        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            httpPost.setConfig(requestConfig);
            // 创建参数列表
            if (param != null) {
                List<NameValuePair> paramList = new ArrayList<NameValuePair>();
                for (String key : param.keySet()) {
                    paramList.add(new BasicNameValuePair(key, param.get(key)));
                    // System.out.format("param list:"+paramList+"\n");
                }
                // 模拟表单
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList);
                httpPost.setEntity(entity);
            }
            // 执行http请求
            response = httpClient.execute(httpPost);

            if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                resultString = EntityUtils.toString(response.getEntity(), "utf-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return resultString;
    }

    public static String doPostJson(String url, String json, Map<String, String> paramHeader) {
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();

        //设置请求超时时间
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(10000)
                .setConnectionRequestTimeout(10000)
                .setSocketTimeout(10000)
                .build();
        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            httpPost.setConfig(requestConfig);
            // 创建请求内容 ，发送json数据需要设置contentType
            if(json != null){
                // logger.info("param is not null");
                StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
                // System.out.format("entity:"+entity+"\n");
                httpPost.setEntity(entity);
            }
            if(paramHeader != null){
                Set<String> keySet = paramHeader.keySet();
                for(String key:keySet){
                    httpPost.addHeader(key, paramHeader.get(key));
                }
            }
            // httpPost.addHeader("Content-Type", "application/json");

            
            // 执行http请求
            response = httpClient.execute(httpPost);
            // logger.info("return code: {}, OK code:{}", response.getStatusLine().getStatusCode(),HttpStatus.SC_OK);
            resultString = EntityUtils.toString(response.getEntity(), "utf-8");
            // if(response.getStatusLine().getStatusCode() < 400){
            //     resultString = EntityUtils.toString(response.getEntity(), "utf-8");
            // }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return resultString;
    }

    public static String doPut(String url, String json, Map<String, String> paramHeader) {
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //设置请求超时时间
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(10000)
                .setConnectionRequestTimeout(10000)
                .setSocketTimeout(10000)
                .build();
        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            // 创建Http Post请求
            HttpPut httpPut = new HttpPut(url);
            httpPut.setConfig(requestConfig);
            // 创建请求内容 ，发送json数据需要设置contentType
            if(json != null){
                logger.info("param is not null");
                StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
                System.out.format("entity:"+entity+"\n");
                httpPut.setEntity(entity);
            }else{
                logger.info("param is null");
            }
            if(paramHeader != null){
                Set<String> keySet = paramHeader.keySet();
                for(String key:keySet){
                    httpPut.addHeader(key, paramHeader.get(key));
                }
            }
            // httpPost.addHeader("Content-Type", "application/json");
            // 执行http请求
            response = httpClient.execute(httpPut);
            
            if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                resultString = EntityUtils.toString(response.getEntity(), "utf-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return resultString;
    }

    public static String doDelete(String url, Map<String, String> param, Map<String, String> headers) {
        // 创建Httpclient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();
        //设置请求超时时间（各项超时参数具体含义链接）
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(10000)
                .setConnectionRequestTimeout(10000)
                .setSocketTimeout(10000)
                .build();
        String resultString = "";
        CloseableHttpResponse response = null;
        try {
            // 创建uri
            URIBuilder builder = new URIBuilder(url);
            if (param != null) {
                for (String key : param.keySet()) {
                    builder.addParameter(key, param.get(key));
                }
            }
            URI uri = builder.build();
            // 创建http PUT请求
            HttpDelete httpDelete = new HttpDelete(uri);
            //给这个请求设置请求配置
            httpDelete.setConfig(requestConfig);
            // httpPut.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; rv:6.0.2) Gecko/20100101 Firefox/6.0.2");
            if(headers != null){
                for(String headerKey:headers.keySet()){
                    httpDelete.addHeader(headerKey, headers.get(headerKey));
                }
            }
            // 执行请求
            response = httpclient.execute(httpDelete);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultString;
    }
}
