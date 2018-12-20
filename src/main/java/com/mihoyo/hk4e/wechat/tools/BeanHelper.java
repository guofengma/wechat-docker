package com.mihoyo.hk4e.wechat.tools;

import com.mihoyo.hk4e.wechat.service.DataGetter;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class BeanHelper implements ApplicationContextAware {

    private static DataGetter dataGetter;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        //从Spring中获得DataGetter独享
        dataGetter = (DataGetter) applicationContext.getBean("dataGetter");
    }

    /**
     * 这个方法存在意义是
     * 我需要在静态域中获得String管理的对象
     * 但这些对象不能通过@Autowired注入
     * @return
     */
    public static DataGetter getDataGetter(){
        return dataGetter;
    }

}
