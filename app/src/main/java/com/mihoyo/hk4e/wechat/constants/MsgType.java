package com.mihoyo.hk4e.wechat.constants;

import com.alibaba.fastjson.JSONObject;

public enum MsgType {

    TEXT("text", "content"),
    FILE("file", "media_id");


    private String msgType;
    private String tag;

    MsgType(String msgType, String tag){
        this.msgType = msgType;
        this.tag = tag;
    }

    public String getMsgType() {
        return msgType;
    }

    public JSONObject genJson(String tagValue){
        JSONObject json = new JSONObject();
        json.put(this.tag, tagValue);
        return json;
    }
}
