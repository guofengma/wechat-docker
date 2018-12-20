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


    /**
     * 根据msgType返回对应的MsgType
     * @param msgType
     * @return
     */
    public static MsgType getByMsgType(String msgType){
        for(MsgType mt : MsgType.values()){
            if(mt.getMsgType().equals(msgType)){
                return mt;
            }
        }
        return null;
        /**
         * 其实可以直接通过"TEXT"找到对应的MsgType.TEXT对象
         * 这里的"TEXT"是TEXT这个属性的 而不是("text", "content")的text
         * 因为外面传过来的是text 而且一般枚举类型的属性命名是大写的 所以很尴尬有了上面的方法
         *
         * 如果可以的 更简单的方法是
         * MsgType result = MsgType.valueOf("TEXT");
         */
    }
}
