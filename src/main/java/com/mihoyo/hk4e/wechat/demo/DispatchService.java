package com.mihoyo.hk4e.wechat.demo;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class DispatchService implements InitializingBean{

    private Map<Integer, IServiceHandler> handlerMap = new HashMap<>();


    /**
     * dispatch一般的意思是分派
     * 他负责找到对应的ServiceHandler 然后让他执行任务
     * @param type
     * @param input
     * @return
     */
    public String dispatchAndHandle(int type, String input){
        IServiceHandler serviceHandler = handlerMap.get(type);
        if(serviceHandler == null){
            return "没找到对应的处理器";
        }
        return serviceHandler.handle(input);
    }

    /**
     * afterPropertiesSet这个方法是实现Spring的InitializingBean
     * 这个方法会在DispatchService被String创造的时候执行 “一次”
     * 你可以理解为是初始化工作
     * 把你定义的n多的handler放进map
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        //这里假设数据库处理的handler的type是1
        handlerMap.put(1, new DBServiceHandler());
        /**
         * 这里算是注册所有的handler
         */

    }
}
