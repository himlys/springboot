package com.spring.filter;

import javax.servlet.*;
import java.io.IOException;
import java.io.InputStream;

public class UserServiceFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//        String url = request.getLocalAddr();
//        InputStream is = request.getInputStream();
//        byte [] bs = new byte[1024];
//        StringBuffer buffer = new StringBuffer();
//        while(is.read(bs) > 0){
//            buffer.append(new String(bs));
//        }
//        String r = buffer.toString();
//        System.out.println("bs = " + r);
//        System.out.println("request url = " + url);
        chain.doFilter(request, response);
    }
}
