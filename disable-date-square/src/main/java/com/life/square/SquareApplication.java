package com.life.square;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableTransactionManagement    //保证开启事务（其实springboot默认就是开启事务的）
public class SquareApplication {
    public static void main(String[] args) {
        SpringApplication.run(SquareApplication.class, args);
    }

    @Bean
//    public RestHighLevelClient client(@Value("${es.url}") String esUrl) {
    public RestHighLevelClient client() {
        return new RestHighLevelClient(RestClient.builder(
                // 我的腾讯云服务器
//                HttpHost.create("http://175.178.177.105:9200")
                // 政府的腾讯云服务器
//                HttpHost.create("http://222.177.66.230:9200")
                // 我的阿里云服务器
                HttpHost.create("http://8.134.164.93:9200")
//                HttpHost.create(esUrl)
        ));
    }

    /**
     * 创建RestTemplate 并注入Spring容器
     *
     * @return
     */
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

//    @Bean
//    public IRule randomRule() {
//        return new RandomRule();
//    }

}


