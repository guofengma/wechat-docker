package com.mihoyo.hk4e.wechat.config;

import com.alibaba.fastjson.JSONObject;
import com.mihoyo.hk4e.wechat.tools.Log;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class RequestHandlerInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        StringBuilder sb = new StringBuilder("in#request#");
        sb.append(request.getMethod())
                .append("#")
                .append(request.getRemoteAddr())
                .append("#")
                .append(request.getRequestURL())
                .append("?")
                .append(request.getQueryString());
        Log.requestLog.info(sb.toString());

        return true;
    }

//    @Override
//    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        if(ex != null){
            Log.errorLog.error("Exception when {}", request.getMethod(), ex);
        }
    }
}
