package com.liang.filter;

import com.liang.domain.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException { }
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String path = request.getServletPath();
//        不应该被拦截的请求，自动放行
        if ("/login.jsp".equals(path) || "/login.do".equals(path)||"/code.do".equals(path)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
//            其他请求
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");

            if (user != null) {
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                response.sendRedirect(request.getContextPath() + "/login.jsp");
            }
        }
    }

    @Override
    public void destroy() { }
}
