package com.mihoyo.hk4e.wechat.config;

import com.mihoyo.hk4e.wechat.tools.Log;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStreamReader;

public class RequestHandlerInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        String param = null;
//        BufferedReader streamReader = new BufferedReader( new InputStreamReader(request.getInputStream(), "UTF-8"));
//        StringBuilder responseStrBuilder = new StringBuilder();
//        String inputStr;
//        while ((inputStr = streamReader.readLine()) != null)
//            responseStrBuilder.append(inputStr);
//
//        System.out.println(responseStrBuilder.toString());
//        JSONObject jsonObject = JSONObject.parseObject(responseStrBuilder.toString());
//        param= jsonObject.toJSONString();
//        System.out.println(param);
//
        StringBuilder sb = new StringBuilder();
        sb.append(request.getRemoteAddr())
                .append("#")
                .append(request.getRequestURL())
                .append("?")
                .append(request.getQueryString());
        Log.requestLog.info(sb.toString());

        InputStreamReader inputStreamReader = new InputStreamReader(request.getInputStream(), "UTF-8");
        int by = 0;
        while ((by = inputStreamReader.read()) != -1) {
            System.out.println((char) by);
        }
        inputStreamReader.close();
//        System.out.println(request.getInputStream().toString());
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
