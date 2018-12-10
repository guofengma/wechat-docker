package com.mihoyo.hk4e.wechat.service;

import com.alibaba.fastjson.JSONObject;
import com.mihoyo.hk4e.wechat.constants.Constants;
import com.mihoyo.hk4e.wechat.constants.MsgType;
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
public class MessageService {
    private Charset charset = Charset.forName("utf8");

    @Autowired
    private TokenService tokenService;

    @Value("${wx.url.message.send}")
    private String wxUrlMessageSend;

    @Value("${agent.id}")
    private int agentId;

    private Logger logger = LoggerFactory.getLogger("MessageService");

    public Result<Object> sendMessage(MessageSender messageSender){
        Result<Object> result = new Result<>();

        Token token = tokenService.getToken();
        if(token == null){
            result.setCode(Constants.RESULT_STATE_FAIL);
            return result;
        }
        JSONObject json = messageSender.genJson();
        Map<String, String> params = new HashMap<>();
        params.put("access_token", token.getContent());
        StringEntity entity = new StringEntity(json.toString(), charset);
        try{
            HttpResponse response = HttpsUtils.doHttpsPost(wxUrlMessageSend, params, entity);
            if(response != null){
                JSONObject respJson = JSONObject.parseObject(EntityUtils.toString(response.getEntity()));
                int errcode = respJson.getIntValue("errcode");
                String errmsg = respJson.getString("errmsg");
                String invaliduser = respJson.getString("invaliduser");
                String invalidparty = respJson.getString("invalidparty");
                String invalidtag = respJson.getString("invalidtag");

                logger.info("sendMessage success errcode:"+errcode
                        +" errmsg:"+errmsg
                        +" invaliduser:"+invaliduser
                        +" invalidparty:"+invalidparty
                        +" invalidtag:"+invalidtag);
                result.setCode(Constants.RESULT_STATE_SUCCESS);
                return result;
            }
        }catch(Exception e){
            logger.error("", e);
        }
        result.setCode(Constants.RESULT_STATE_FAIL);
        return result;
    }

    public MessageSender createOneMessageSender(MsgType msgType){
        MessageSender ms = new MessageSender(msgType, agentId);
        return ms;
    }
}
