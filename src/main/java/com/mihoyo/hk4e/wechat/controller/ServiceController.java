package com.mihoyo.hk4e.wechat.controller;

import com.mihoyo.hk4e.wechat.constants.MsgType;
import com.mihoyo.hk4e.wechat.constants.Tips;
import com.mihoyo.hk4e.wechat.dto.HandlerParam;
import com.mihoyo.hk4e.wechat.dto.HandlerResult;
import com.mihoyo.hk4e.wechat.dto.MessageSender;
import com.mihoyo.hk4e.wechat.service.ChainService;
import com.mihoyo.hk4e.wechat.service.MessageService;
import com.mihoyo.hk4e.wechat.service.PoemService;
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
import java.util.ArrayList;
import java.util.List;

@RestController
public class ServiceController {

    @Autowired
    private WxCryptService wxCryptService;

    @Autowired
    private ChainService chainService;

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
            String sEchoStr = wxcpt.VerifyURL(msgSignature, timestamp, nonce, echostr);
            Log.requestLog.info("in#response#verify echostr: " + sEchoStr);
            return sEchoStr;
        } catch (Exception e) {
            //验证URL失败，错误原因请查看异常
            Log.errorLog.error("error: ", e);
            return e.getMessage();
        }
    }


    @RequestMapping(value = "/service", method = RequestMethod.POST)
    public String service(@RequestParam("msg_signature") String msgSignature,
                          @RequestParam("timestamp") String timestamp,
                          @RequestParam("nonce") String nonce,
                          @RequestBody String body){
        try {
            Log.requestLog.info("body: " + body);
            WXBizMsgCrypt wxcpt = wxCryptService.genWxBizMsgCrypt();
            if(wxcpt == null){
                return Tips.TRY_IT_LATER;
            }
            String sMsg = wxcpt.DecryptMsg(msgSignature, timestamp, nonce, body);
            Log.requestLog.info("after decrypt msg: " + sMsg);

            // TODO: 解析出明文xml标签的内容进行处理
            // For example:
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document document = db.parse(new InputSource(new StringReader(sMsg)));

            Element root = document.getDocumentElement();
            NodeList nodeListMsgType = root.getElementsByTagName("MsgType");
            String msgType = nodeListMsgType.item(0).getTextContent();
            Log.requestLog.info("msgType: " + msgType);

            NodeList nodeListName = root.getElementsByTagName("FromUserName");
            String userName = nodeListName.item(0).getTextContent();
            Log.requestLog.info("user: " + userName);

            HandlerParam param = new HandlerParam(userName, msgType, root);
            HandlerResult result = chainService.handle(param);
            String backMsg = result != null ? result.getBackMsg() : "handler not found";
            if(result == null){
                Log.errorLog.error("handler not found when msg:{}", sMsg);
            }
            String encryptMsg = wxcpt.EncryptMsg(backMsg, timestamp, nonce);
            Log.requestLog.info("加密后: " + encryptMsg);
            return encryptMsg;
        } catch (Exception e) {
            // TODO
            // 解密失败，失败原因请查看异常
            Log.errorLog.error("error: ", e);
            return e.getMessage();
        }
    }
}
