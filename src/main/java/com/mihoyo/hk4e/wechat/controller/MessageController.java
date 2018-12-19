package com.mihoyo.hk4e.wechat.controller;

import com.alibaba.fastjson.JSONObject;
import com.mihoyo.hk4e.wechat.constants.Constants;
import com.mihoyo.hk4e.wechat.constants.MsgType;
import com.mihoyo.hk4e.wechat.dto.FileUploader;
import com.mihoyo.hk4e.wechat.dto.MessageSender;
import com.mihoyo.hk4e.wechat.dto.Result;
import com.mihoyo.hk4e.wechat.service.FileService;
import com.mihoyo.hk4e.wechat.service.MessageService;
import com.mihoyo.hk4e.wechat.tools.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileOutputStream;

@RestController
@RequestMapping("/message")
public class MessageController {
    @Value("${agent.id}")
    private int agentId;

    @Autowired
    private MessageService messageService;

    @Autowired
    private FileService fileService;

    @RequestMapping(value = "send", method = RequestMethod.POST)
    public Result<Object> send(@RequestBody String body){
        Result<Object> result = new Result<>();

        JSONObject json = JSONObject.parseObject(body);
        json.put("agentid", agentId);

        String toUser = json.getString("touser");

        JSONObject text = json.getJSONObject("text");
        String content = text.getString("content");

        if(content.length() > 500) {
            Long timestamp = System.currentTimeMillis();
            String fileName = "tmpFile/" + timestamp.toString();

            try {
                File file = new File(fileName);
                File parentFile = file.getParentFile();
                if (!parentFile.exists()){
                    parentFile.mkdirs();
                }
                file.createNewFile();

                FileOutputStream out = new FileOutputStream(file, true);
                out.write(content.getBytes("utf-8"));
                out.close();

                FileUploader fileUploader = new FileUploader("file", fileName);
                String media_id = fileService.uploadFile(fileUploader);

                MessageSender ms = messageService.createOneMessageSender(MsgType.FILE);
                ms.addUser(toUser);
                ms.setContent(media_id);
                messageService.sendMessage(ms);

                text.put("content", content.substring(0, 20) + "...\n详见附件");
                return messageService.sendMessageDirect(json);
            } catch (Exception e) {
                //验证URL失败，错误原因请查看异常
                Log.errorLog.error("error: ", e);
                result.setCode(Constants.RESULT_STATE_FAIL);
                return result;
            }
        }
        else{
            return messageService.sendMessageDirect(json);
        }
    }

//    @RequestMapping(value = "upload", method = RequestMethod.POST)
//    public Result<Object> upload(@RequestBody String body){
//        JSONObject json = JSONObject.parseObject(body);
//        json.put("agentid", agentId);
//
//        String filePath = json.getString("filepath");
//        FileUploader fileUploader = new FileUploader("file", "/Users/xingyi.song/Downloads/51207cd727232.jpg");
//        String media_id = fileService.uploadFile(fileUploader);
//
//
//        return messageService.sendMessageDirect(json);
//    }
}
