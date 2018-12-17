package com.mihoyo.hk4e.wechat.demo;

public interface IServiceHandler {

    /**
     * 根据输入的input做具体操作
     * 然后返回东西
     * 这里的输入和返回类型 只是举例子
     * @param input
     * @return
     */
    String handle(String input);

}
