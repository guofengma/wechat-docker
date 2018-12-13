package com.mihoyo.hk4e.wechat.service;

import com.mihoyo.hk4e.wechat.entity.Token;
import com.mihoyo.hk4e.wechat.tools.qq.AesException;
import com.mihoyo.hk4e.wechat.tools.qq.WXBizMsgCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class WxCryptService {

//    @Autowired
//    private TokenService tokenService;
    @Value("${app.token}")
    private String token;
    @Value("${corp.id}")
    private String corpId;
    @Value("${encoding.aes.key}")
    private String encodingAesKey;


    public WXBizMsgCrypt genWxBizMsgCrypt() throws AesException {
//        Token token = tokenService.getToken();
//        if(token == null){
//            return null;
//        }
//        return new WXBizMsgCrypt(token.getContent(), encodingAesKey, corpId);
        return new WXBizMsgCrypt(token, encodingAesKey, corpId);
    }
}
