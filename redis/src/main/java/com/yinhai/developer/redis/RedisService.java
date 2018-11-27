package com.yinhai.developer.redis;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Service
public class RedisService {
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

    JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();

    JedisPool jedisPool =  new JedisPool(jedisPoolConfig,REDIS_HOST,REDIS_PORT);
    public RedisService(){
        //连接超时时是否阻塞，false时报异常,ture阻塞直到超时, 默认true
        jedisPoolConfig.setBlockWhenExhausted(true);
        //逐出策略（默认DefaultEvictionPolicy(当连接超过最大空闲时间,或连接数超过最大空闲连接数)）
        jedisPoolConfig.setEvictionPolicyClassName("org.apache.commons.pool2.impl.DefaultEvictionPolicy");

        //最大空闲连接数, 默认8个
        jedisPoolConfig.setMaxIdle(8);

        //最大连接数, 默认8个
        jedisPoolConfig.setMaxTotal(8);

        //获取连接时的最大等待毫秒数(如果设置为阻塞时BlockWhenExhausted),如果超时就抛异常, 小于零:阻塞不确定的时间,  默认-1
        jedisPoolConfig.setMaxWaitMillis(-1);

        //逐出连接的最小空闲时间 默认1800000毫秒(30分钟)
        jedisPoolConfig.setMinEvictableIdleTimeMillis(1800000);

        //最小空闲连接数, 默认0
        jedisPoolConfig.setMinIdle(0);

        //每次逐出检查时 逐出的最大数目 如果为负数就是 : 1/abs(n), 默认3
        jedisPoolConfig.setNumTestsPerEvictionRun(3);

        //对象空闲多久后逐出, 当空闲时间>该值 且 空闲连接>最大空闲数 时直接逐出,不再根据MinEvictableIdleTimeMillis判断  (默认逐出策略)
        jedisPoolConfig.setSoftMinEvictableIdleTimeMillis(1800000);

        // 获取连接时的最大等待毫秒数(如果设置为阻塞时BlockWhenExhausted),如果超时就抛异常, 小于零:阻塞不确定的时间,
        // 默认-1
        jedisPoolConfig.setMaxWaitMillis(100);

        //对拿到的connection进行validateObject校验
        jedisPoolConfig.setTestOnBorrow(true);

        //在进行returnObject对返回的connection进行validateObject校验
        jedisPoolConfig.setTestOnReturn(true);

        //定时对线程池中空闲的链接进行validateObject校验
        jedisPoolConfig.setTestWhileIdle(true);

    }

    public void excute(CallWithJedis callWithJedis){
        try(Jedis jedis = jedisPool.getResource()){
            jedis.auth("yinhai");
            try {
                callWithJedis.call(jedis);
            }catch (Exception e){
                callWithJedis.call(jedis);
            }

        }
    }

}
