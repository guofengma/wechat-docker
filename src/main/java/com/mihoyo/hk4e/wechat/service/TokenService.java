package com.mihoyo.hk4e.wechat.service;

import com.alibaba.fastjson.JSONObject;
import com.mihoyo.hk4e.wechat.entity.Token;
import com.mihoyo.hk4e.wechat.repository.TokenRepository;
import com.mihoyo.hk4e.wechat.tools.HttpsUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * token管理器
 */
@Service
public class TokenService {
    @Autowired
    private TokenRepository tokenRepository;

    @Value("${corp.id}")
    private String corpId;
    @Value("${app.secret}")
    private String corpSecret;
    @Value("${wx.url.access.token}")
    private String wxUrlAccessToken;

    private Logger logger = LoggerFactory.getLogger("TokenService");

    //如果发现token失效了 就去从微信服务器取token 并标记flag 以免同时多人发请求
    private AtomicBoolean flag = new AtomicBoolean(false);


    public Token getToken(){
        Optional<Token> optional = tokenRepository.findById(Token.ID);
        if(optional.isPresent() && optional.get().valid()){
            return optional.get();
        }
        if(flag.compareAndSet(false, true)){
            //同时只有一个请求去取token 其他人都fail-fast
            fetchToken();
            optional = tokenRepository.findById(Token.ID);
            logger.info("use token:" + optional.get().getContent() + " expireDate:" + optional.get().getExpireDate().toString());
            return optional.isPresent() && optional.get().valid() ? optional.get() : null;
        }
        return null;
    }

    public void clearFlag(){
        this.flag.getAndSet(false);
    }


    private void fetchToken(){
        long start = System.currentTimeMillis();
        logger.info("fetchToken start");
        try{
            //去微信取token
            Map<String, String> params = new HashMap<>(2, 1f);
            params.put("corpid", corpId);
            params.put("corpsecret", corpSecret);
            HttpResponse response = HttpsUtils.doHttpsGet(wxUrlAccessToken, params);
            if(response == null){
                logger.error("fetchToken result:response is null");
                return;
            }
            String result = EntityUtils.toString(response.getEntity());
            JSONObject jsonObject = JSONObject.parseObject(result);
            int errcode = jsonObject.getIntValue("errcode");
            if(errcode != 0){ //失败
                logger.error("fetchToken result:"+jsonObject.getString("errmsg"));
                return;
            }
            String accessToken = jsonObject.getString("access_token");
            int expiresIn = jsonObject.getIntValue("expires_in");
            logger.info("fetchToken result: token="+accessToken +" expiresIn="+expiresIn);
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.SECOND, expiresIn);
            Optional<Token> optional = tokenRepository.findById(Token.ID);
            if(!optional.isPresent()){
                tokenRepository.save(Token.createOne(accessToken, cal.getTime()));
            }else{
                tokenRepository.updateToken(accessToken, cal.getTime(), Token.ID);
            }
        }catch(Exception e){
            logger.error("", e);
        }finally {
            clearFlag();
        }
        logger.info("fetchToken end within " + (System.currentTimeMillis() - start) + "ms");
    }
}
