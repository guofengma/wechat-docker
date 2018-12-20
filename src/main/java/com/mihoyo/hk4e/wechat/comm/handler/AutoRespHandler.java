package com.mihoyo.hk4e.wechat.comm.handler;

import com.mihoyo.hk4e.wechat.constants.MsgType;
import com.mihoyo.hk4e.wechat.dto.HandlerParam;
import com.mihoyo.hk4e.wechat.dto.HandlerResult;
import com.mihoyo.hk4e.wechat.dto.MessageSender;
import com.mihoyo.hk4e.wechat.service.DataGetter;
import com.mihoyo.hk4e.wechat.tools.BeanHelper;
import com.mihoyo.hk4e.wechat.tools.Log;
import org.w3c.dom.NodeList;

/**
 * 自动回复消息
 */
public class AutoRespHandler implements IServiceHandler {

    @Override
    public HandlerResult handle(HandlerParam param) {
        HandlerResult result = new HandlerResult();
        MsgType type = MsgType.getByMsgType(param.getMsgType());
        if(type == null || type != MsgType.TEXT){
            return result;
        }

        DataGetter dataGetter = BeanHelper.getDataGetter();

        NodeList nodeListContent = param.getRoot().getElementsByTagName("Content");
        String msg = nodeListContent.item(0).getTextContent();
        Log.requestLog.info("msg: " + msg);

        String backMsg = "哼，不想理你！";
        if(msg.equals("你好")) {
            backMsg = "一点都不好";
        }else if(msg.equals("求签")){
            backMsg = dataGetter.getPoemService().getLuck();
        }else if(msg.contains("滚")){
            backMsg = "呵，圆滚滚的小笨猪";
        }

        MessageSender ms = dataGetter.getMessageService().createOneMessageSender(type);
        ms.addUser(param.getUserName());
        ms.setContent(backMsg);
        dataGetter.getMessageService().sendMessage(ms);

        result.setState(HandlerResult.STATE_SUCCESS);
        result.setBackMsg(backMsg);
        return result;
    }
}
