package com.payment.demo.Security;

import com.payment.demo.Interceptors.interceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Component
public class paymentInterceptorConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private interceptor interceptor;

//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        super.addInterceptors(registry);
//        registry.addInterceptor(interceptor);
//    }
}
