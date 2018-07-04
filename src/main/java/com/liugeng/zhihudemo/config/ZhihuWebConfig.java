package com.liugeng.zhihudemo.config;

import com.liugeng.zhihudemo.interceptor.LoginCheckInterceptor;
import com.liugeng.zhihudemo.interceptor.LoginRequiredInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Component
public class ZhihuWebConfig extends WebMvcConfigurationSupport{
    @Autowired
    LoginCheckInterceptor loginCheckInterceptor;
    @Autowired
    LoginRequiredInterceptor loginRequiredInterceptor;

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginCheckInterceptor).excludePathPatterns("/static/**", "/reglogin");
        registry.addInterceptor(loginRequiredInterceptor).addPathPatterns("/user/*").excludePathPatterns("/static/**", "/reglogin");
    }

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }
}
