
package org.example.qtsv.configuration;

import jakarta.servlet.Filter;
import org.example.qtsv.filter.UserFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class AppConfig {
    @Bean
    public FilterRegistrationBean<Filter> AppFilter() {
        FilterRegistrationBean<Filter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new UserFilter());
        return registrationBean;
    }
}

