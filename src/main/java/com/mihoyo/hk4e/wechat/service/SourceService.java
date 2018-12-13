package com.mihoyo.hk4e.wechat.service;

import com.alibaba.fastjson.JSONObject;
import com.mihoyo.hk4e.wechat.constants.Constants;
import com.mihoyo.hk4e.wechat.dto.MessageSender;
import com.mihoyo.hk4e.wechat.dto.Result;
import com.mihoyo.hk4e.wechat.entity.Token;
import com.mihoyo.hk4e.wechat.tools.HttpsUtils;
import org.apache.http.HttpResponse;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

@Service
public class SourceService {
    private Logger logger = LoggerFactory.getLogger("SourceService");

    public String getSource(){
        Result<Object> result = new Result<>();

        Map<String, String> params = new HashMap<>();
        params.put("workspace_id", "22963631");
        params.put("id", "1122963631001006185");
        try{
            HttpResponse response = HttpsUtils.doHttpsGet("http://oa.mihoyo.com:7778/stories", params);
            if(response != null){
                JSONObject respJson = JSONObject.parseObject(EntityUtils.toString(response.getEntity()));
                String status = respJson.getString("status");
                String data = respJson.getString("data");

                logger.info("getSource success status:"+status
                        +" data:"+data);
                return data;
            }
        }catch(Exception e){
            logger.error("", e);
        }
        return "wrong";
    }
}
