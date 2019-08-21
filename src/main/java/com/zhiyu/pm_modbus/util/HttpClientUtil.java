package com.zhiyu.pm_modbus.util;

import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
@Component
public class HttpClientUtil {
    private static Logger log = LogManager.getLogger(HttpClientUtil.class);

    private PoolingHttpClientConnectionManager pool;
    private RequestConfig config;

    public HttpClientUtil(PoolingHttpClientConnectionManager pool, RequestConfig config) {
        this.pool = pool;
        this.config = config;
    }

    /**
     * 使用httpclient请求远程接口数据
     * @param url 请求地址
     * @param params 参数
     * @param type 请求类型
     * @return 返回结果
     */
    public String doRequest(String url, List<NameValuePair> params,String type) {
        //获取httpclient
        CloseableHttpClient client = HttpClients.custom().setConnectionManager(pool).build();
        //可关闭的返回对象
        CloseableHttpResponse response = null;
        try {
            URIBuilder builder = new URIBuilder(url);
            if (null != params&&params.size() > 0) {
                builder.addParameters(params);
            }
            if ("get".equals(type) || "GET".equals(type)) {
                HttpGet get = new HttpGet(builder.build());
                //设置请求信息
                //get.addHeader("Authorization",appcode);
                get.setConfig(config);
                response = client.execute(get);

            }else if ("post".equals(type) || "POST".equals(type)) {
                HttpPost post = new HttpPost(builder.build());
                post.setConfig(config);
                response = client.execute(post);
            } else {
                return null;
            }

            if (response.getStatusLine().getStatusCode() == 200) {
                if (response.getEntity() != null) {
                    return EntityUtils.toString(response.getEntity(), "utf-8");
                }
            }
        } catch (Exception e) {
            log.error("请求接口失败: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (null != response) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
