package ru.job4j.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String uri = req.getRequestURI();
        if (uri.endsWith("login.html") || uri.endsWith("reg.html")) {
            chain.doFilter(request, response);
            return;
        }
        if (req.getSession().getAttribute("user") == null) {
            resp.sendRedirect("login.html");
            return;
        }
        chain.doFilter(request, response);
    }
}
