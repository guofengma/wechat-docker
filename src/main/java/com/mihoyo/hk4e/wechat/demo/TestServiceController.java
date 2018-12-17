package com.mihoyo.hk4e.wechat.demo;

import com.mihoyo.hk4e.wechat.constants.MsgType;
import com.mihoyo.hk4e.wechat.constants.Tips;
import com.mihoyo.hk4e.wechat.dto.MessageSender;
import com.mihoyo.hk4e.wechat.service.MessageService;
import com.mihoyo.hk4e.wechat.service.WxCryptService;
import com.mihoyo.hk4e.wechat.tools.Log;
import com.mihoyo.hk4e.wechat.tools.qq.WXBizMsgCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;

@RestController
public class TestServiceController {


    @Autowired
    private DispatchService dispatchService;


    @RequestMapping(value = "/testService", method = RequestMethod.POST)
    public String service(@RequestParam("msg_signature") String msgSignature,
                          @RequestParam("timestamp") String timestamp,
                          @RequestParam("nonce") String nonce,
                          @RequestBody String body){
        try {
            //这里模拟/service的请求
            //假设接收到了数据 从中解析出你要的关键字
            //这里假设关键字是type
            int type = 1;
            String input = "更新数据库中某某信息";
            //然后调用我们的方法
            String result = dispatchService.dispatchAndHandle(type, input);

            return result;
        } catch (Exception e) {
            // TODO
            // 解密失败，失败原因请查看异常
            Log.errorLog.error("error: ", e);
            return e.getMessage();
        }
    }
}
