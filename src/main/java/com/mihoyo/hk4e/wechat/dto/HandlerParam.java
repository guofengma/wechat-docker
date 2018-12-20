package com.mihoyo.hk4e.wechat.dto;

import org.w3c.dom.Element;

public class HandlerParam {
    private String userName;
    private String msgType;
    private Element root;

    public HandlerParam(String userName, String msgType, Element root) {
        this.userName = userName;
        this.msgType = msgType;
        this.root = root;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public Element getRoot() {
        return root;
    }

    public void setRoot(Element root) {
        this.root = root;
    }
}
