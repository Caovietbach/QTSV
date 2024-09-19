/*
package org.example.qtsv.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.qtsv.entity.UserEntity;
import org.example.qtsv.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);

    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        String token = request.getHeader("Authorization");
        logger.info("token: {}", token);

        if (requestURI.equals("/api/students/login") || requestURI.equals("/api/students/addUser")) {
            return true;
        } else if (token != null && token.startsWith("Bearer ")) {
            try {
                String jwtToken = token.substring(7);
                logger.info("JWT: {}", jwtToken);
                UserEntity user = userService.extractUser(jwtToken);
                if (user != null && user.getStatus() == 1) {
                    logger.info("user: {}", user.getUserName());
                    return true;
                } else {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Please log in.");
                    logger.error("User status is not authorized.");
                    return false;
                }
            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error processing token.");
                logger.error("Error processing token: {}", e.getMessage());
                return false;
            }
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authorization token missing or invalid.");
            logger.error("Authorization token missing or invalid.");
            return false;
        }
    }
}

 */