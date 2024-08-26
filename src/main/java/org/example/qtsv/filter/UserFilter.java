package org.example.qtsv.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class UserFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(UserFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String user = httpRequest.getHeader("Username");
        String api = httpRequest.getServletPath();
        logger.info("user: {} use {} api", user, api);
        chain.doFilter(request, response);
    }

}
