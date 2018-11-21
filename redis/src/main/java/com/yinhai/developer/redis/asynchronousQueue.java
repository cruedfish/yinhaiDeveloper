package com.yinhai.developer.redis;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: asynchronousQueue
 * @Description: TODO
 * @Author: yinhai
 * @Date: 2018/11/20 19:46
 * @Version 1.0
 **/
public class asynchronousQueue {
    public static void main(String[]args){
        //测试rpop
        JedisClient jedisClient = new JedisClientSingle();
        jedisClient.lpush("testqueue","123");
        jedisClient.lpush("testqueue","456");
        jedisClient.lpush("testqueue","789");
       String result0 = jedisClient.rpop("testqueue");
       String result1 =jedisClient.rpop("testqueue");
               String result2 = jedisClient.rpop("testqueue");
               List<String> result = new ArrayList<String>();
               result.add(result0);
        result.add(result1);
        result.add(result2);
        result.stream().forEach(a->System.out.println(a));
        jedisClient.lpush("testqueue","123");
        jedisClient.lpush("testqueue","456");
        jedisClient.lpush("testqueue","123");
        result = jedisClient.rbpop("testqueue");
        int n =1;
        result.stream().forEach(a->System.out.println(a));


    }
}