package com.yinhai.developer.redis;

import redis.clients.jedis.Jedis;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @ClassName: LuaScriptTest
 * @Description: TODO
 * @Author: yinhai
 * @Date: 2018/11/22 16:36
 * @Version 1.0
 **/
public class LuaScriptTest {
    public static void main(String[]args){

//        Jedis jedis = new Jedis();
//        jedis.auth("yinhai");
//        String [] param = new String[]{"123","456","789","10112","12345","1","3","31","4","13","4"};
//
//        jedis.lpush("testThread",param);
//        Thread t1 = new ThreadPush(1);
//        Thread t2 = new ThreadPush(2);
//        t2.start();
//        t1.start();

        Jedis jedis1 = new Jedis("39.107.90.7",6379);
        jedis1.auth("yinhai");
        System.out.println(jedis1.ping());
        String str1 = "return redis.pcall('set',KEYS[1],'bar')";//设置键k1的值为bar
        Object eval1 = jedis1.eval(str1, 1,"k1");
        System.out.println(eval1);
        System.out.println(jedis1.eval("return redis.call('get','k1')"));//查看执行情况

        //实现分布式限流
        String str2 = "local myTable = redis.call('cl.throttle',KEYS[1],ARGV[1],ARGV[2],ARGV[3],ARGV[4]); return myTable[1]";
        String[] param1 =  new String[]{"testThrottle","10","5","60","3"};
        Object eval2 = jedis1.eval(str2,1,param1);

        //实现分布式锁
        String str3 = "local a = redis.call('setnx',KEYS[1],ARGV[1]); if( a==1) then return 0 else return 1 end ";

        String[] param2 =  new String[]{"nxtest2","3"};

        Object eval3 = jedis1.eval(str3,1,param2);

//        System.out.println(eval2);

        System.out.println(eval3);


    }
}