package com.yinhai.developer.redis;

import redis.clients.jedis.Jedis;

/**
 * @ClassName: ThreadPush
 * @Description: TODO
 * @Author: yinhai
 * @Date: 2018/11/23 13:51
 * @Version 1.0
 **/
public class ThreadPush extends Thread{
    private int id;

    public ThreadPush(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        Jedis jedis = new Jedis();
        jedis.auth("yinhai");
        String result = jedis.rpop("testThread");
        System.out.println("线程"+id+"消费了:"+result);
        while (result!= null){
            result = jedis.rpop("testThread");
//            try {
//                Thread.sleep(1000);
//            }catch (Exception e){
//                e.printStackTrace();
//                break;
//            }

            System.out.println("线程"+id+"消费了:"+result);
        }

    }
}