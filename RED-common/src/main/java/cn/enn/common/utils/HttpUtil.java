package cn.enn.common.utils;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class HttpUtil {


    public static String doPost(String url, String jsonStr) {

        String result = "";

        CloseableHttpClient httpclient = HttpClientBuilder.create().build();


        HttpPost post = new HttpPost(url);
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(5000).setConnectionRequestTimeout(1000).setSocketTimeout(5000).build();
        post.setConfig(requestConfig);
        try {
            StringEntity s = new StringEntity(jsonStr);
            s.setContentEncoding("UTF-8");
            s.setContentType("application/json");//发送json数据需要设置contentType
            post.setEntity(s);
            CloseableHttpResponse res = httpclient.execute(post);
            try {
                if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    result = EntityUtils.toString(res.getEntity());
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                res.close();
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }


    public static String doPost(String url, String jsonStr, String appSecret) {

        String result = "";

        CloseableHttpClient httpclient = HttpClientBuilder.create().build();

        HttpPost post = new HttpPost(url);
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(5000).setConnectionRequestTimeout(1000).setSocketTimeout(5000).build();
        post.setConfig(requestConfig);
        try {
            StringEntity s = new StringEntity(jsonStr);
            s.setContentEncoding("UTF-8");
            s.setContentType("application/json");//发送json数据需要设置contentType
            post.setEntity(s);
            post.setHeader("appSecret", appSecret);

            CloseableHttpResponse res = httpclient.execute(post);
            try {
                if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    result = EntityUtils.toString(res.getEntity());
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                res.close();
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }


    public static String doGet(String url) {

        String result = "";

        CloseableHttpClient httpclient = HttpClientBuilder.create().build();
        HttpGet get = new HttpGet(url);
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(5000).setConnectionRequestTimeout(1000).setSocketTimeout(5000).build();
        get.setConfig(requestConfig);
        try {
            get.setHeader("content-type", "application/json");
            HttpResponse res = httpclient.execute(get);
            if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                result = EntityUtils.toString(res.getEntity());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public static String doGet(String url, String appSecret) {

        String result = "";

        CloseableHttpClient httpclient = HttpClientBuilder.create().build();
        HttpGet get = new HttpGet(url);
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(5000).setConnectionRequestTimeout(1000).setSocketTimeout(5000).build();
        get.setConfig(requestConfig);
        try {
            get.setHeader("content-type", "application/json");
            get.setHeader("appSecret", appSecret);
            CloseableHttpResponse res = httpclient.execute(get);
            try {
                if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    result = EntityUtils.toString(res.getEntity());
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                res.close();
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
