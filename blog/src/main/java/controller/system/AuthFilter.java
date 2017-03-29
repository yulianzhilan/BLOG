package controller.system;


import entity.system.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by scott on 2017/3/24.
 */
public class AuthFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse)response;
        HttpSession session = httpServletRequest.getSession();
        String path = httpServletRequest.getRequestURI();
        User user =  (User)session.getAttribute("_token");
//        if(user != null){
//            chain.doFilter(httpServletRequest,httpServletResponse);
//        } else if(path.contains("/blog/article")||path.contains("/blog/file")||path.contains("/blog/photo")){
//            ((HttpServletResponse) response).sendRedirect("/blog/login");
//        } else{
            chain.doFilter(httpServletRequest, httpServletResponse);
//        }
    }

    @Override
    public void destroy() {

    }
}
