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
        test2();
        return "1.2Winter, winter, beautiful winter!";
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

    private void test2(){
        List<Object> poem = new ArrayList<>();
        poem.add("桃花依旧笑春风");
        poem.add("相见时难别亦难");
        poem.add("守得云开见月明");
        poem.add("花开堪折直须折");
        poem.add("红粉相随南浦晚");
        poem.add("任是无情也动人");
        poem.add("红藕香残玉簟秋");
        poem.add("芒果布丁喵喵喵");

        List<Object> poemList = RandomUtils.randomGet(poem, 4);

        Map<Object, Integer> luck = new HashMap<>();
        luck.put("小吉", 40);
        luck.put("中吉", 20);
        luck.put("大吉", 5);
        luck.put("超级无敌吉", 1);

        String luckTag = RandomUtils.weightGet(luck).toString();

        StringBuilder sb = new StringBuilder();
        sb.append("今日运势：")
                .append(luckTag)
                .append("\n--------------\n");
        for(Object p: poemList){
            sb.append(p.toString()).append("\n");
        }

        MessageSender ms = messageService.createOneMessageSender(MsgType.TEXT);
        ms.addUser("xingyi.song");
        ms.setContent(sb.toString());
        messageService.sendMessage(ms);
    }
}