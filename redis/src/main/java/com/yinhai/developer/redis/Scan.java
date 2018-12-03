package com.yinhai.developer.redis;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;

public class Scan {
    public static  void main(String[]args){
        RedisService redisService = new RedisService();

          ScanParams scanParams = new ScanParams();
          scanParams.match("key11*");
          redisService.excute(jedis -> {
              ScanResult<String> result = jedis.scan("0",scanParams );
              int i = result.getResult().size();
              while (!result.getStringCursor().equals("0")){
                  result = jedis.scan(result.getStringCursor(),scanParams);
                  for(String s : result.getResult()){
                      System.out.println(s);
                  }
                  i+= result.getResult().size();
              }

              System.out.println(i);
          });

          System.out.print("完成");
        }
}
