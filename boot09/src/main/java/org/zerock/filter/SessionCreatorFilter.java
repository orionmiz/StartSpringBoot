package org.zerock.filter;

import lombok.extern.java.Log;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Log
@WebFilter
public class SessionCreatorFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("INININININININI");
    }

    // generate csrf forcibly
    // https://github.com/thymeleaf/thymeleaf-extras-springsecurity/issues/34
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;

        String requestURI = httpServletRequest.getRequestURI();

        log.info("#############");

        if (!requestURI.startsWith("/boards")){
            httpServletRequest.getSession(true);
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        log.info("DEDEDEDEDEDEDE");
    }
}
