package com.zhiyu.pm_modbus.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HttpClientConfig {
    @Bean("pool")
    public PoolingHttpClientConnectionManager pool() {
        PoolingHttpClientConnectionManager pool = new PoolingHttpClientConnectionManager();
        pool.setMaxTotal(10);
        pool.setDefaultMaxPerRoute(10);
        return pool;
    }
    @Bean("config")
    public RequestConfig config() {
        RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(10000)//创建连接最长时间
                .setConnectionRequestTimeout(500)//获取连接最长时间
                .setSocketTimeout(10000)//数据传输最长时间
                .build();
        return config;
    }
}
