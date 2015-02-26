package com.expenses.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Andres on 15/11/2014.
 */
public class ApiOriginFilter implements javax.servlet.Filter{

    private static final String DEFAULT_ALLOWED_ORIGIN = "*";

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response,
                         final FilterChain chain) throws IOException, ServletException {
        HttpServletResponse res = (HttpServletResponse) response;
        String allowedOrigin = DEFAULT_ALLOWED_ORIGIN;
        if (request instanceof HttpServletRequest) {
            allowedOrigin = ((HttpServletRequest) request).getHeader("Origin");
        }
        res.addHeader("Access-Control-Allow-Origin", allowedOrigin);
        res.addHeader("Access-Control-Allow-Methods", "OPTIONS, GET, POST, DELETE, PUT, HEAD");
        res.addHeader("Access-Control-Allow-Credentials", "true");
        res.addHeader("Access-Control-Allow-Headers", "Accept,Accept-Encoding,Accept-Language,Access-Control-Request-Method,Access-Control-Request-Headers,Authorization,Connection,Content-Type,Host,Origin,Referer,Token-Id,User-Agent, X-Requested-With");
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }

    @Override
    public void init(final FilterConfig filterConfig) throws ServletException {
    }
}
