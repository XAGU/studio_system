package com.xagu.studio.studiosystem.config;

import jdk.nashorn.internal.runtime.logging.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

/**
 * @author xagu
 * Created on 2020/4/14
 * Email:xagu_qc@foxmail.com
 * Describe: TODO
 */
@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate(ClientHttpRequestFactory factory) {
        RestTemplate restTemplate = new RestTemplate(factory);
        restTemplate.getInterceptors().add(new FakeIpInterceptor());
        return restTemplate;
    }

    @Bean
    public ClientHttpRequestFactory simpleClientHttpRequestFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setReadTimeout(60000);
        factory.setConnectTimeout(60000);
        return factory;
    }

    private class FakeIpInterceptor implements org.springframework.http.client.ClientHttpRequestInterceptor {
        @Override
        public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
            traceRequest(request, body);
            return execution.execute(request, body);
        }


        private void traceRequest(HttpRequest request, byte[] body) throws IOException {
            System.out.println("===========================request begin================================================");
            System.out.println("URI         : {}" + request.getURI());
            System.out.println("Method      : {}" + request.getMethod());
            System.out.println("Headers     : {}" + request.getHeaders());
            System.out.println("Request body: {}" + new String(body, "UTF-8"));
            System.out.println("==========================request end================================================");
        }

    }
}