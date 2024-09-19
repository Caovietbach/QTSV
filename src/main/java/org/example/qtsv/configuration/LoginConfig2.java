
package org.example.qtsv.configuration;

import org.example.qtsv.filter.LoginFilter2;
import org.example.qtsv.filter.UserFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoginConfig2 {

    @Bean
    public FilterRegistrationBean<LoginFilter2> loginFilterRegistration(LoginFilter2 loginFilter2) {
        FilterRegistrationBean<LoginFilter2> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(loginFilter2);
        return registrationBean;
    }
}


