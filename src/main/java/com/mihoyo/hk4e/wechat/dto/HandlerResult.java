package com.mihoyo.hk4e.wechat.dto;

public class HandlerResult {
    public static final int STATE_IGNORE = 0; //忽略不处理
    public static final int STATE_SUCCESS = 1; //处理
    public static final int STATE_EXCEPTION = 2; //异常


    private int state = STATE_IGNORE; //默认忽略
    private String backMsg; //返回内容

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getBackMsg() {
        return backMsg;
    }

    public void setBackMsg(String backMsg) {
        this.backMsg = backMsg;
    }
}
