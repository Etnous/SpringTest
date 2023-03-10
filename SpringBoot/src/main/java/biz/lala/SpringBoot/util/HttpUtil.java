package biz.lala.SpringBoot.util;

import biz.lala.SpringBoot.entity.ProxyEntity;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author et
 */
public class HttpUtil {

    public static String httpGetRequest(String url, Map<String, String> paramsMap, ProxyEntity proxy) throws Exception {
        CloseableHttpClient httpClient = HttpClients.custom().evictIdleConnections(1, TimeUnit.SECONDS).build();
        if (!Objects.isNull(paramsMap)) {
            List<BasicNameValuePair> list = new ArrayList<>();
            paramsMap.forEach( (key, value) -> list.add(new BasicNameValuePair(key, value)));
            String param = EntityUtils.toString(new UrlEncodedFormEntity(list, "UTF-8"));
            url += "?" + param;
        }

        HttpGet httpGet = new HttpGet(url);
        RequestConfig config;
        if (!Objects.isNull(proxy)) {
            HttpHost httpHost = new HttpHost(proxy.getProxyIp(), proxy.getProxyPort(), proxy.getProxyType());
            config = RequestConfig.custom()
                    .setConnectTimeout(20000)
                    .setConnectionRequestTimeout(20000)
                    .setSocketTimeout(20000)
                    .setProxy(httpHost).build();
        } else {
            config = RequestConfig.custom()
                    .setConnectTimeout(20000)
                    .setConnectionRequestTimeout(20000)
                    .setSocketTimeout(20000)
                    .build();
        }
        httpGet.setConfig(config);
        CloseableHttpResponse response = httpClient.execute(httpGet);
        HttpEntity entity = response.getEntity();
        String content = EntityUtils.toString(entity);
        return content;

    }
}
