package com.ando.web.filter;

import javax.servlet.*;
import java.io.IOException;

public class AndoFilterRegistration implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("AndoFilter process...");
        chain.doFilter(request,response);
    }

    @Override
    public void destroy() {

    }
}
