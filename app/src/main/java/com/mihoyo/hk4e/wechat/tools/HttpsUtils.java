package com.mihoyo.hk4e.wechat.tools;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.util.Map;

public class HttpsUtils {

    private static Logger logger = LoggerFactory.getLogger("HttpUtils");

    private static HttpClient getHttpsClient() throws Exception {

        SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, (certificate, authType) -> true).build();

        CloseableHttpClient client = HttpClients.custom()
                .setSSLContext(sslContext)
                .setSSLHostnameVerifier(new NoopHostnameVerifier())
                .build();
        return client;

    }

    /**
     * 发送https get请求
     * @param url
     * @param params
     * @return
     */
    public static HttpResponse doHttpsGet(String url, Map<String, String> params){
        try{
            HttpGet httpGet = new HttpGet(buildUrlWithParams(url, params));
//        httpGet.setHeader("Accept", "application/xml");
            HttpClient client = getHttpsClient();
            HttpResponse response = client.execute(httpGet);
            return response;
        }catch(Exception e){
            logger.error("", e);
        }
        return null;
    }

    public static HttpResponse doHttpsPost(String url, Map<String, String> params, HttpEntity entity){
        try{
            HttpPost httpPost = new HttpPost(buildUrlWithParams(url, params));
            httpPost.setEntity(entity);
            HttpClient client = getHttpsClient();
            HttpResponse response = client.execute(httpPost);
            return response;
        }catch(Exception e){
            logger.error("", e);
        }
        return null;
    }

    public static HttpResponse doMultipartPost(String url, Map<String, String> params, String filePath, String fileName){
        try{
            HttpPost httpPost = new HttpPost(buildUrlWithParams(url, params));

            File file = new File(filePath);
            Long fileLength = file.length();

            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.addBinaryBody("name=\"media\"; filename=\""+fileName+"\"; filelength="+fileLength.toString(), file);
            httpPost.setEntity(builder.build());
            HttpClient client = getHttpsClient();
            HttpResponse response = client.execute(httpPost);
            return response;
        }catch(Exception e){
            logger.error("", e);
        }
        return null;
    }

    /**
     * 将url和参数拼接成一个完整的url
     * @param url
     * @param params
     * @return
     */
    private static String buildUrlWithParams(String url, Map<String, String> params){
        if(params == null || params.isEmpty()){
            return url;
        }
        StringBuilder stringBuilder = new StringBuilder(url);
        stringBuilder.append("?");
        for(Map.Entry<String, String> entry : params.entrySet()){
            if(stringBuilder.length() > 0){
                stringBuilder.append("&");
            }
            stringBuilder.append(entry.getKey()).append("=").append(entry.getValue());
        }
        return stringBuilder.toString();
    }

}
