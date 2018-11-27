package com.yinhai.developer.redis;

import redis.clients.jedis.GeoRadiusResponse;
import redis.clients.jedis.GeoUnit;
import redis.clients.jedis.params.geo.GeoRadiusParam;

import java.util.List;

public class GeoHash {
    public static void main(String[]args){
         RedisService redisService = new RedisService();
        Holder<Double> a = new Holder<>();
        Holder<List<GeoRadiusResponse>> resList = new Holder<>();
                redisService.excute(jedis->{
             jedis.geoadd("company",116.562108, 39.787602 ,"jd");
             jedis.geoadd("company",116.334255 , 40.027400  ,"xiaomi");
             jedis.geoadd("company",116.489033  , 40.007669   ,"meituan");
             jedis.geoadd("company",116.514203   , 39.905409    ,"ireader");
             jedis.geoadd("company",116.48105    , 39.996794     ,"juejin");
             //计算两个节点距离
             Double b = jedis.geodist("company","juejin","ireader", GeoUnit.KM);
             List<GeoRadiusResponse> res = jedis.georadiusByMember("company","juejin",20,GeoUnit.KM, GeoRadiusParam.geoRadiusParam().sortAscending().count(3));
             a.value(b);
             resList.value(res);
         });
        System.out.print(a.value());
        for(GeoRadiusResponse geoRadiusResponse:resList.value()){
            System.out.print(new String(geoRadiusResponse.getMember()));
        }
    }
}
