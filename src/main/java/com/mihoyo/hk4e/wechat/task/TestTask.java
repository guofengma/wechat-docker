package com.mihoyo.hk4e.wechat.task;

import org.springframework.stereotype.Component;

/**
 * 一个定时任务的测试类
 * 注释取消即可生效
 */
@Component
public class TestTask {

    /**
     * 每隔3秒执行
     */
//    @Scheduled(fixedRate = 3000)
    public void test(){
        System.out.println("task");
    }

    /**
     *  每天早上10：15触发
     */
//    @Scheduled(cron = "0 15 10 ? * *")
    public void test2(){
        System.out.println("task2");
    }
    /**
     * cron属性
     这是一个时间表达式，可以通过简单的配置就能完成各种时间的配置，我们通过CRON表达式几乎可以完成任意的时间搭配，它包含了六或七个域：

     Seconds : 可出现", - * /"四个字符，有效范围为0-59的整数
     Minutes : 可出现", - * /"四个字符，有效范围为0-59的整数
     Hours : 可出现", - * /"四个字符，有效范围为0-23的整数
     DayofMonth : 可出现", - * / ? L W C"八个字符，有效范围为0-31的整数
     Month : 可出现", - * /"四个字符，有效范围为1-12的整数或JAN-DEc
     DayofWeek : 可出现", - * / ? L C #"四个字符，有效范围为1-7的整数或SUN-SAT两个范围。1表示星期天，2表示星期一， 依次类推
     Year : 可出现", - * /"四个字符，有效范围为1970-2099年
     */
}
