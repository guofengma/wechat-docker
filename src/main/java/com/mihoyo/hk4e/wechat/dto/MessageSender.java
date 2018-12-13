package com.mihoyo.hk4e.wechat.dto;

import com.alibaba.fastjson.JSONObject;
import com.mihoyo.hk4e.wechat.constants.MsgType;
import com.mihoyo.hk4e.wechat.constants.SymbolConstants;
import java.util.HashSet;
import java.util.Set;

/**
 * 一次消息推送
 * 暂时仅支持text
 * @See https://work.weixin.qq.com/api/doc#90000/90135/90236
 */
public class MessageSender {
    private Set<String> tousers = new HashSet<>();
    private Set<String> topartys = new HashSet<>();
    private Set<String> totags = new HashSet<>();
    private final MsgType type;
    private final int agentId;
    private int safe;
    private String content; //text内容

    public MessageSender(MsgType type, int agentId) {
        this.type = type;
        this.agentId = agentId;
    }

    public void addUser(String user){
        tousers.add(user);
    }
    public void addParty(String party){
        tousers.add(party);
    }
    public void addTag(String tag){
        tousers.add(tag);
    }


    public JSONObject genJson(){
        JSONObject json = new JSONObject();
        json.put("touser", concat(tousers));
        json.put("toparty", concat(topartys));
        json.put("totag", concat(totags));
        json.put("msgtype", type.getMsgType());
        json.put("agentid", agentId);
        json.put(type.getMsgType(), type.genJson(content));
        json.put("safe", safe);
        return json;
    }


    private String concat(Set<String> set){
        StringBuilder sb = new StringBuilder();
        for(String str : set){
            if(sb.length() > 0){
                sb.append(SymbolConstants.VERTICAL_LINE);
            }
            sb.append(str);
        }
        return sb.toString();
    }


    public MsgType getType() {
        return type;
    }

    public int getAgentId() {
        return agentId;
    }

    public int getSafe() {
        return safe;
    }

    public void setSafe(int safe) {
        this.safe = safe;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
