package com.yinhai.developer.redis;

/**
 * @ClassName: Publisher
 * @Description: TODO 发布类
 * @Author: yinhai
 * @Date: 2018/11/29 11:45
 * @Version 1.0
 **/
public class Publisher {
    public static void main(String[]args){
        RedisService redisService = new RedisService();
        new Thread(()->{
            redisService.excute(jedis -> {
                for (int i =0;i<1000;i++){
                    jedis.publish("mychannel","yinhai"+i);
                }

            });
        }).start();
    }
}