package com.mihoyo.hk4e.wechat.demo;

/**
 * 这是处理数据库的
 * 其他类型的 可以创建对应的handler
 */
public class DBServiceHandler implements IServiceHandler {
    @Override
    public String handle(String input) {
        /**
         * 各种数据库处理
         */
        return "success";
    }
}
