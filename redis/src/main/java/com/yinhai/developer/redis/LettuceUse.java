package com.yinhai.developer.redis;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.reactive.RedisReactiveCommands;
import io.lettuce.core.api.reactive.RedisStringReactiveCommands;
import io.lettuce.core.api.sync.RedisCommands;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;
import java.util.function.Consumer;

public class LettuceUse {
  public static  void  main(String[]args) throws Exception{
//      RedisCommands redisCommand = statefulRedisConnection.sync();
//      redisCommand.set("key","HelloRedis");
//      String result = (String)redisCommand.get("key");
//        System.out.println(result);
//      RedisAsyncCommands redisCommands1 = statefulRedisConnection.async();
//      RedisFuture<String> future = redisCommands1.get("key");
//
//      future.handle((String s,Throwable t)->{
//          System.out.println(t);
//          System.out.println("handle方法输出结果为:"+s);
//          return s;
//      }).thenAccept(a->{
//          System.out.println("accept方法输出结果为:"+a);
//          throw new RuntimeException("123");
//          //exceptionally 在异常发生的时候应该做的操作
//      }).exceptionally(a->{
//          System.out.println(a.toString());
//          return null;
//      });


//      future.thenAccept(new Consumer<String>() {
//          @Override
//          public void accept(String o) {
//              try {
//                  Thread.sleep(1000);
//              }catch (Exception e){
//                  e.printStackTrace();
//              }
//              System.out.println((String)o);
//          }
//      });
//      System.out.println("测试主线程阻塞");
//      statefulRedisConnection.close();
//      redisClient.shutdown();
//
//
//      RedisClient client = RedisClient.create("redis://localhost");
//      RedisStringReactiveCommands<String, String> commands = client.connect().reactive();
//
//
//      Flux.just("Ben", "Michael", "Mark")
//              .flatMap(commands::get)
//              .flatMap(value -> ((RedisReactiveCommands<String, String>) commands).rpush("result", value))
//              .subscribe();
//      RedisClient redisClient = RedisClient.create("redis://yinhai@39.107.90.7:6379/0");
////      StatefulRedisConnection<String,String> statefulRedisConnection = redisClient.connect();
////      RedisCommands<String,String> redisCommands = statefulRedisConnection.sync();
////      System.out.println(redisCommands.get("key"));
////      RedisStringReactiveCommands<String,String> commands = statefulRedisConnection.reactive();
////      Mono<String> mono = commands.get("key");
////      int a = 1;
      RedisURI redisURI = RedisURI.Builder.redis("39.107.90.7").withPort(6379).withPassword("yinhai").withDatabase(0).build();
      RedisClient client = RedisClient.create(redisURI);
      RedisStringReactiveCommands<String, String> commands = client.connect().reactive();
      commands.get("key").subscribe(new Consumer<String>() {
          @Override
          public void accept(String s) {
              System.out.println(s);
          }
      });
//      commands
//              .get("key")
//              .subscribe(new Subscriber<String>() {
//                  @Override
//                  public void onSubscribe(Subscription s) {
//                      System.out.println("订阅"+s.toString());
//                  }
//
//                  @Override
//                  public void onNext(String s) {
//                      System.out.println("next"+s);
//                  }
//
//                  @Override
//                  public void onError(Throwable t) {
//
//                  }
//
//                  @Override
//                  public void onComplete() {
//                      System.out.println("完成");
//                  }
//              });

      Flux.just("Ben", "key", "Mark").flatMap(key -> commands.get(key)).subscribe(value -> System.out.println("Got value: " + value));


//      Flux.just("Ben", "Michael", "Mark").doOnNext(new Consumer<String>() {
//                                                       @Override
//                                                       public void accept(String s) {
//                                                           System.out.println("测试"+s);
//
//                                                       }
//                                                   }).subscribe();


//      Flux.just("Ben", "Michael", "Mark")
//              .doOnNext(((RedisReactiveCommands<String,String>) commands)::scard)
//              .reduce((sum, current) -> sum + current)
//              .subscribe(result -> System.out.println("Number of elements in sets: " +
//                      result));


//      Flux.just("Ben", "Michael", "Mark")
//              .flatMap(((RedisReactiveCommands<String, String>) commands)::scard)
//              .reduce(new BiFunction<Long, Long, Long>() {
//                  @Override
//                  public Long apply(Long aLong, Long aLong2) {
//                      return aLong+aLong2;
//                  }
//              })
//              .subscribe(new Consumer<Long>() {
//                  @Override
//                  public void accept(Long aLong) {
//                      System.out.println("Number of elements in sets: " +
//                              aLong);
//                  }
//              } );

  }
}
