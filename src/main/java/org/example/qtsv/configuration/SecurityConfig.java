
package org.example.qtsv.configuration;

import org.example.qtsv.filter.LoginFilter;
import org.example.qtsv.filter.UserFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private final LoginFilter loginFilter;

    @Autowired
    private final UserFilter userFilter;

    @Autowired
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Autowired
    private final CustomAccessDeniedHandler customAccessDeniedHandler;

    public SecurityConfig(LoginFilter loginFilter, UserFilter userFilter,
                          CustomAuthenticationEntryPoint customAuthenticationEntryPoint,
                          CustomAccessDeniedHandler customAccessDeniedHandler) {
        this.loginFilter = loginFilter;
        this.userFilter = userFilter;
        this.customAuthenticationEntryPoint = customAuthenticationEntryPoint;
        this.customAccessDeniedHandler = customAccessDeniedHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint(customAuthenticationEntryPoint)
                        .accessDeniedHandler(customAccessDeniedHandler)
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/login", "/api/auth/logout").permitAll()
                        .requestMatchers("/api/users").hasRole("ADMIN")
                        .requestMatchers("/api/students/").hasAnyRole("USER","ADMIN")
                        .requestMatchers("/api/students/thesis").hasAnyRole("USER","ADMIN")
                        .requestMatchers("/api/students/{id}/thesis").hasRole("USER")
                        .requestMatchers("/api/students", "/api/students/{id}").hasAuthority("ADMIN")
                        .anyRequest().authenticated()
                )
                .addFilterBefore(loginFilter,UsernamePasswordAuthenticationFilter.class)
                ;


        return http.build();
    }
}



