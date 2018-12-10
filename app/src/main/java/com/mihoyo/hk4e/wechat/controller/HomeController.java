package com.mihoyo.hk4e.wechat.controller;

import com.mihoyo.hk4e.wechat.constants.MsgType;
import com.mihoyo.hk4e.wechat.dto.FileUploader;
import com.mihoyo.hk4e.wechat.dto.MessageSender;
import com.mihoyo.hk4e.wechat.entity.Token;
import com.mihoyo.hk4e.wechat.repository.TokenRepository;
import com.mihoyo.hk4e.wechat.service.FileService;
import com.mihoyo.hk4e.wechat.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

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
    @RequestMapping("/test")
    public String index(){

        test1();
        return "Just for test, the wechat platform simulator >_<";
    }

    private void test1(){
        //        SourceService sourceService = new SourceService();
//        String content = sourceService.getSource();

//        FileUploader fileUploader = new FileUploader("file", "/share/file");
//        String media_id = fileService.uploadFile(fileUploader);

        MessageSender ms = messageService.createOneMessageSender(MsgType.TEXT);
        ms.addUser("xingyi.song");
//        ms.setContent(media_id);
        ms.setContent("hello");
        messageService.sendMessage(ms);
    }

    private void test2(){
        tokenRepository.updateToken("111", new Date(), Token.ID);

        Token token = tokenRepository.findById(Token.ID).get();
        System.out.println("rresult:" + token.getContent() + "-" + token.getExpireDate());
    }

}