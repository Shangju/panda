package com.graduation.panda.config;

import com.graduation.panda.filter.SysInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 拦截器配置
 *
 */
@Configuration
public class FilterConfig extends WebMvcConfigurerAdapter{

    @Bean
    public SysInterceptor getSysInterceptor(){
        return new SysInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(getSysInterceptor()).addPathPatterns("/cart/**");
    }

}
