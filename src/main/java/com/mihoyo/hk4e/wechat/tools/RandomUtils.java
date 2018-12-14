package com.mihoyo.hk4e.wechat.tools;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class RandomUtils {
    private static Logger logger = LoggerFactory.getLogger("RandomUtils");

    public static Object weightGet(Map<Object, Integer> data) {
        TreeMap<Integer, Object> treeMap = new TreeMap<>();
        for(Map.Entry<Object, Integer> entry : data.entrySet()){
            int weight = entry.getValue();
            if(weight <= 0){ //权重非正数的 剔除
                continue;
            }
            int sum = treeMap.isEmpty() ? 0 : treeMap.lastKey();
            //treeMap的key是累计权重 value是你想要的项
            treeMap.put(sum + weight, entry.getKey());
        }
        //组装完毕treeMap 然后开始随机
        int dice = new Random().nextInt(treeMap.lastKey()) + 1; //从1到所有权重总和之间随机

        Map.Entry<Integer, Object> result = treeMap.ceilingEntry(dice); //第一个大于等于dice的项就是答案

        return result.getValue();
    }

    public static List<Object> randomGet(List<Object> list, Integer num){
        List<Object> result = new ArrayList<>();

        //从中随机取两个
        Collections.shuffle(list); //将list随机打乱 数据多的话可能耗费点性能
        for(int i = 0; i < num; i++){
            result.add(list.get(i));
        }

        return result;

//        Random random = new Random();
//        //或者随机下标
//        int dice = random.nextInt(list.size());
//        Object result1 = list.get(dice);
//        list.remove(dice); //将随机出来的移除 所以会改变list 注意！
//        dice = random.nextInt(list.size());
//        Object result2 = list.get(dice);
    }
}
