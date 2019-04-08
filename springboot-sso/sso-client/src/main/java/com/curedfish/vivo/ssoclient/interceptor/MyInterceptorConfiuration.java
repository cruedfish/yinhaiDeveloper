package com.curedfish.vivo.ssoclient.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyInterceptorConfiuration implements WebMvcConfigurer {
    @Autowired
    CookieInterceptor cookieInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
         registry.addInterceptor(cookieInterceptor);
    }
}
