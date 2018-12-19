package com.mihoyo.hk4e.wechat.service;

import com.mihoyo.hk4e.wechat.tools.RandomUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PoemService {
    public String getLuck(){
        List<Object> poem = new ArrayList<>();
        poem.add("桃花依旧笑春风");
        poem.add("相见时难别亦难");
        poem.add("守得云开见月明");
        poem.add("花开堪折直须折");
        poem.add("红粉相随南浦晚");
        poem.add("任是无情也动人");
        poem.add("红藕香残玉簟秋");
        poem.add("芒果布丁喵喵喵");

        List<Object> poemList = RandomUtils.randomGet(poem, 4);

        Map<Object, Integer> luck = new HashMap<>();
        luck.put("小吉", 40);
        luck.put("中吉", 20);
        luck.put("大吉", 5);
        luck.put("超级无敌吉", 1);

        String luckTag = RandomUtils.weightGet(luck).toString();

        StringBuilder sb = new StringBuilder();
        sb.append("今日运势：")
                .append(luckTag)
                .append("\n--------------\n");
        for(Object p: poemList){
            sb.append(p.toString()).append("\n");
        }

        return sb.toString();
    }
}
