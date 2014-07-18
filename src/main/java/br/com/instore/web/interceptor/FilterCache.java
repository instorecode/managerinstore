package br.com.instore.web.interceptor;

import java.io.IOException;
import java.util.Calendar;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter(urlPatterns = "/*")
public class FilterCache implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if(((HttpServletRequest) request).getRequestURI().contains("/resources/")) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MONTH, 6);
            ((HttpServletResponse)response).setDateHeader("Expires", calendar.getTimeInMillis());
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        
    }
    
}
