package com.yinhai.developer.redis;

import org.apache.logging.log4j.core.pattern.AbstractStyleNameConverter;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisPubSub;

/**
 * @ClassName: Pubsub
 * @Description: TODO redis 发布订阅
 * @Author: yinhai
 * @Date: 2018/11/29 10:38
 * @Version 1.0
 **/
public class Pubsub {
    public static void main(String[]args){
        RedisService redisService = new RedisService();


       new Thread(()->{
           redisService.excute(jedis -> {
               jedis.subscribe(new JedisPubSub() {
                   @Override
                   public void onMessage(String channel, String message) {
                       System.out.println(message);
                   }
                   @Override
                   public void onSubscribe(String channel, int subscribedChannels) {
                       System.out.println(String.format("subscribe redis channel success, channel %s, subscribedChannels %d",
                               channel, subscribedChannels));
                   }
                   @Override
                   public void onUnsubscribe(String channel, int subscribedChannels) {
                       System.out.println(String.format("unsubscribe redis channel, channel %s, subscribedChannels %d",
                               channel, subscribedChannels));

                   }
               },"mychannel");
           });
       }).start();

    }
}