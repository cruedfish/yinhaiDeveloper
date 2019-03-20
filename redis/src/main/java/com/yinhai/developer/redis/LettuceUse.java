package com.yinhai.developer.redis;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisFuture;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;
import io.lettuce.core.api.sync.RedisCommands;

import java.util.function.Consumer;

public class LettuceUse {
  public static  void  main(String[]args){
      RedisClient redisClient = RedisClient.create("redis://yinhai@39.107.90.7:6379/0");
      StatefulRedisConnection<String,String> statefulRedisConnection = redisClient.connect();
//      RedisCommands redisCommand = statefulRedisConnection.sync();
//      redisCommand.set("key","HelloRedis");
//      String result = (String)redisCommand.get("key");
//        System.out.println(result);
      RedisAsyncCommands redisCommands1 = statefulRedisConnection.async();
      RedisFuture<String> future = redisCommands1.get("key");
      future.thenAccept(new Consumer<String>() {
          @Override
          public void accept(String o) {
              System.out.println((String)o);
          }
      });

      statefulRedisConnection.close();
      redisClient.shutdown();
  }
}
