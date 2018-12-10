package com.mihoyo.hk4e.wechat.controller;

import com.fasterxml.jackson.annotation.OptBoolean;
import com.mihoyo.hk4e.wechat.constants.MsgType;
import com.mihoyo.hk4e.wechat.dto.MessageSender;
import com.mihoyo.hk4e.wechat.entity.Token;
import com.mihoyo.hk4e.wechat.repository.TokenRepository;
import com.mihoyo.hk4e.wechat.service.FileService;
import com.mihoyo.hk4e.wechat.service.MessageService;
import com.mihoyo.hk4e.wechat.tools.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Optional;

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
        test2();
        Log.errorLog.error("这是error");

        Log.errorLog.info("这是info");
        Log.errorLog.warn("这是warn");
        Log.errorLog.debug("这是debug");

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
        Optional<Token> optional = tokenRepository.findById(Token.ID);
        if(!optional.isPresent()){
            tokenRepository.save(Token.createOne("111", new Date()));
        }else{
            tokenRepository.updateToken("111", new Date(), Token.ID);
        }

        Token token = tokenRepository.findById(Token.ID).get();
        System.out.println("result:" + token.getContent() + "-" + token.getExpireDate());
    }

}