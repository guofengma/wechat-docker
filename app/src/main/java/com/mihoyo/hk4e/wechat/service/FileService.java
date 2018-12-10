package com.mihoyo.hk4e.wechat.service;

import com.alibaba.fastjson.JSONObject;
import com.mihoyo.hk4e.wechat.constants.Constants;
import com.mihoyo.hk4e.wechat.dto.FileUploader;
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
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class FileService {
    @Autowired
    private TokenService tokenService;

    private Logger logger = LoggerFactory.getLogger("FileService");

    public String uploadFile(FileUploader fileUploader){
//        Result<Object> result = new Result<>();

        Token token = tokenService.getToken();
        if(token == null){
            logger.info("upload token is null");
//            result.setCode(Constants.RESULT_STATE_FAIL);
            return null;
        }
        logger.info("upload start");
        Map<String, String> params = new HashMap<>();
        params.put("access_token", token.getContent());
        params.put("type", fileUploader.getType());
        try{
            String filePath = fileUploader.getFilePath();
            String fileName = fileUploader.getFileName();
            HttpResponse response = HttpsUtils.doMultipartPost("https://qyapi.weixin.qq.com/cgi-bin/media/upload", params, filePath, fileName);
            if(response != null){
                JSONObject respJson = JSONObject.parseObject(EntityUtils.toString(response.getEntity()));
                int errcode = respJson.getIntValue("errcode");
                String errmsg = respJson.getString("errmsg");
                String type = respJson.getString("type");
                String media_id = respJson.getString("media_id");
                String created_at = respJson.getString("created_at");

                logger.info("uploadFile success errcode:"+errcode
                        +" errmsg:"+errmsg
                        +" type:"+type
                        +" media_id:"+media_id
                        +" created_at:"+created_at);
//                result.setCode(Constants.RESULT_STATE_SUCCESS);
                return media_id;
            }
        }catch(Exception e){
            logger.error("", e);
        }
//        result.setCode(Constants.RESULT_STATE_FAIL);
        return null;
    }
}
