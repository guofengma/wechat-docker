package com.mihoyo.hk4e.wechat.config;

import com.mihoyo.hk4e.wechat.tools.Log;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class RequestHandlerInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append(request.getRemoteAddr())
                .append("#")
                .append(request.getMethod())
                .append("#")
                .append(request.getRequestURL())
                .append("?")
                .append(request.getQueryString()).append("#");

        BufferedReader streamReader = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
        String inputStr;
        while ((inputStr = streamReader.readLine()) != null)
            sb.append(inputStr);

        Log.requestLog.info(sb.toString());

        return true;
    }

//    @Override
//    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//    }

//    @Override
//    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//        if(ex != null){
//            Log.errorLog.error("Exception when {}", request.getMethod(), ex);
//        }
//    }
}
