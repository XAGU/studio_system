package com.xagu.studio.studiosystem.config;

import com.xagu.studio.studiosystem.Interceptor.LoginHandlerInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

/**
 * @author xagu
 * Created on 2020/9/8
 * Email:xagu_qc@foxmail.com
 * Describe: TODO
 */
@Configuration
public class LoginConfiguration implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        LoginHandlerInterceptor loginHandlerInterceptor = new LoginHandlerInterceptor();
        InterceptorRegistration interceptorRegistration = registry.addInterceptor(loginHandlerInterceptor);
        interceptorRegistration.addPathPatterns("/view/**");
        interceptorRegistration.addPathPatterns("/index.html");
        interceptorRegistration.addPathPatterns("/console.html");
        interceptorRegistration.addPathPatterns("/swagger-ui.html");
        interceptorRegistration.addPathPatterns("/reSetPassword");
        interceptorRegistration.excludePathPatterns("/view/error/**");
    }
}
