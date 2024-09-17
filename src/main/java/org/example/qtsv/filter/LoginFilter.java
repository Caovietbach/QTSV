package org.example.qtsv.filter;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.qtsv.entity.UserEntity;
import org.example.qtsv.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class LoginFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(LoginFilter.class);

    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String requestURI = request.getRequestURI();
        String token = request.getHeader("Authorization");
        logger.info("token: {}",token);
        if (requestURI.equals("/api/students/login") || requestURI.equals("/api/students/addUser")) {
            chain.doFilter(request, response);
        } else if (token != null) {
            try {
                String jwtToken = token.substring(7);
                logger.info("JWT: {}",jwtToken);
                UserEntity user = userService.extractUser(jwtToken);
                if (user != null && user.getStatus() == 1) {
                    logger.info("user: {}",user.getUserName());
                    chain.doFilter(request, response);
                } else {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Please log in.");
                    logger.error("error: {}",1);
                }
            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error processing token.");
                logger.error("error: {}",e);
            }
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authorization token missing or invalid.");
            logger.error("error: {}",2);
        }
    }
}