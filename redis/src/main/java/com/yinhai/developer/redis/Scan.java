package com.yinhai.developer.redis;

import redis.clients.jedis.JedisPoolConfig;

public class Scan {
    public static  void main(String[]args){
        RedisService redisService = new RedisService();

          redisService.excute(jedis -> {
              for (int i =1 ;i<10000;i++) {
                  jedis.set("key" + i, i+"");
              }
          });
          System.out.print("完成");
        }
}
