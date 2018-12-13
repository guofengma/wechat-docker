package com.mihoyo.hk4e.wechat.controller;

import com.mihoyo.hk4e.wechat.constants.Tips;
import com.mihoyo.hk4e.wechat.service.WxCryptService;
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
public class ServiceController {

    @Autowired
    private WxCryptService wxCryptService;

    @RequestMapping(value = "/service", method = RequestMethod.GET)
    public String verify(@RequestParam("msg_signature") String msgSignature,
                         @RequestParam("timestamp") String timestamp,
                         @RequestParam("nonce") String nonce,
                         @RequestParam("echostr") String echostr){

        try {
            WXBizMsgCrypt wxcpt = wxCryptService.genWxBizMsgCrypt();
            if(wxcpt == null){
                return Tips.TRY_IT_LATER;
            }
            String sEchoStr = wxcpt.VerifyURL(msgSignature, timestamp,
                    nonce, echostr);
            System.out.println("verifyurl echostr: " + sEchoStr);
            // 验证URL成功，将sEchoStr返回
//             HttpUtils.SetResponse(sEchoStr);
            return sEchoStr;
        } catch (Exception e) {
            //验证URL失败，错误原因请查看异常
            e.printStackTrace();
            return e.getMessage();
        }
    }


    @RequestMapping(value = "/service", method = RequestMethod.POST)
    public String service(@RequestParam("msg_signature") String msgSignature,
                          @RequestParam("timestamp") String timestamp,
                          @RequestParam("nonce") String nonce,
                          @RequestBody String body){
        try {
            WXBizMsgCrypt wxcpt = wxCryptService.genWxBizMsgCrypt();
            if(wxcpt == null){
                return Tips.TRY_IT_LATER;
            }
            String sMsg = wxcpt.DecryptMsg(msgSignature, timestamp, nonce, body);
            System.out.println("after decrypt msg: " + sMsg);
            // TODO: 解析出明文xml标签的内容进行处理
            // For example:
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            StringReader sr = new StringReader(sMsg);
            InputSource is = new InputSource(sr);
            Document document = db.parse(is);

            Element root = document.getDocumentElement();
            NodeList nodelist1 = root.getElementsByTagName("Content");
            String msg = nodelist1.item(0).getTextContent();
            System.out.println("msg：" + msg);
            String encryptMsg = wxcpt.EncryptMsg(msg, timestamp, nonce);
            System.out.println("加密后:" + encryptMsg);
            return encryptMsg;
        } catch (Exception e) {
            // TODO
            // 解密失败，失败原因请查看异常
            e.printStackTrace();
            return e.getMessage();
        }
    }


}
