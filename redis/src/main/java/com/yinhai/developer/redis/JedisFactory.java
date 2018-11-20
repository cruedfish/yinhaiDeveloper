package com.yinhai.developer.redis;

import org.apache.log4j.Logger;
import redis.clients.jedis.Jedis;

public class JedisFactory {
    private static final Logger logger = Logger.getLogger(JedisFactory.class);
    /**
     * jedis
     */
    private static Jedis jedis = null;;

    /**
     * redis-host
     */
    private final static String REDIS_HOST = "39.107.90.7";

    /**
     * redis-port
     */
    private final static int REDIS_PORT = 6379;

    /**
     * redis-timeout
     */
    private final static int REDIS_TIMEOUT = 5000;

    static {
        try {
            jedis = new Jedis(REDIS_HOST, REDIS_PORT, REDIS_TIMEOUT);
            jedis.auth("yinhai");
        } catch (Exception e) {
            logger.error(" JedisFactory 获取redis连接出错 ： " , e);
        }
    }
    public static Jedis getJedis() {
        return jedis;
    }

    public static void main(String[] args) {
        System.out.println(JedisFactory.getJedis().info());
    }
    public void closeJedis(){
        jedis.close();
    }
}
