package com.mihoyo.hk4e.wechat.service;

import com.mihoyo.hk4e.wechat.comm.handler.AutoRespHandler;
import com.mihoyo.hk4e.wechat.comm.handler.IServiceHandler;
import com.mihoyo.hk4e.wechat.dto.HandlerParam;
import com.mihoyo.hk4e.wechat.dto.HandlerResult;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 责任链模式
 * 把所有的handler放在一个链表里
 *
 * 请求数据   ==> handler1 -> handler2 ->  ....  -> handler3 => 返回数据
 *
 * 这里简单处理：只要有一个handler成功处理请求 就直接返回
 *
 */
@Service
public class ChainService implements InitializingBean {

    private List<IServiceHandler>  chains = new ArrayList<>();

    public HandlerResult handle(HandlerParam param){
        for(IServiceHandler handler : chains){
            HandlerResult result;
            try{
                result = handler.handle(param);
            }catch(Exception e){
                result = new HandlerResult();
                result.setState(HandlerResult.STATE_EXCEPTION);
                result.setBackMsg("exception occurs");
                return result;
            }

            if(result.getState() == HandlerResult.STATE_SUCCESS){
                return result;
            }
        }
        return null;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        chains.add(new AutoRespHandler());
    }
}
