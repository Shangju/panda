package com.graduation.panda.config;

import com.graduation.panda.filter.SysInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class FilterConfig extends WebMvcConfigurerAdapter{

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(new SysInterceptor()).addPathPatterns("/cart/**");
    }

}
