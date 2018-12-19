package com.mihoyo.hk4e.wechat.controller;

import com.mihoyo.hk4e.wechat.constants.MsgType;
import com.mihoyo.hk4e.wechat.dto.FileUploader;
import com.mihoyo.hk4e.wechat.dto.MessageSender;
import com.mihoyo.hk4e.wechat.entity.Token;
import com.mihoyo.hk4e.wechat.repository.TokenRepository;
import com.mihoyo.hk4e.wechat.service.FileService;
import com.mihoyo.hk4e.wechat.service.MessageService;
import com.mihoyo.hk4e.wechat.tools.Log;
import com.mihoyo.hk4e.wechat.tools.RandomUtils;
import org.apache.logging.log4j.util.StringBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

//import com.mihoyo.hk4e.wechat.service.SourceService;

@RestController
public class HomeController {
    @Autowired
    private MessageService messageService;

    @Autowired
    private FileService fileService;

    @Autowired
    private TokenRepository tokenRepository;

    /**
     * just test if the service ok
     * @return
     */
    @RequestMapping("/live")
    public String index(){
        return "Just for test, the wechat callback server >_<";
    }

    @RequestMapping("/winter")
    public String secret(){
        return "1.5Winter, winter, beautiful winter!";
    }

    private void test1(){
        //        SourceService sourceService = new SourceService();
//        String content = sourceService.getSource();

//        FileUploader fileUploader = new FileUploader("file", "/Users/xingyi.song/Downloads/51207cd727232.jpg");
//        String media_id = fileService.uploadFile(fileUploader);

        MessageSender ms = messageService.createOneMessageSender(MsgType.TEXT);
        ms.addUser("xingyi.song");
//        ms.setContent(media_id);
        ms.setContent("hello");
        messageService.sendMessage(ms);
    }
}