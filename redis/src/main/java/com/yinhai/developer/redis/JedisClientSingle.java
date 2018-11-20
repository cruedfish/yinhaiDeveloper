package com.yinhai.developer.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.GeoRadiusResponse;
import redis.clients.jedis.GeoUnit;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.geo.GeoRadiusParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class JedisClientSingle implements  JedisClient {
    @Autowired
    JedisFactory jedisFactory;

    private   Jedis jedis=JedisFactory.getJedis();
    private Logger logger = LoggerFactory.getLogger(JedisClientSingle.class);
    private static ProtostuffSerializer protostuffSerializer = new ProtostuffSerializer();
    public boolean exists(String key) {
        boolean flag = false;
        try {
            flag = jedis.exists(protostuffSerializer.serialize(key));
        } catch (Exception ex) {
            logger.error("JedisService.exists 出错[key=" + key + "]", ex);
        } finally {
            closeResource(jedis);
        }
        return flag;
    }

    public String set(String key, String value, int seconds) {
        String responseResult = null;
        String result = "";
        Jedis jedis = null;
        try {
            responseResult = jedis.set(protostuffSerializer.serialize(key),
                    protostuffSerializer.serialize(value));
            if (seconds != 0) {
                jedis.expire(protostuffSerializer.serialize(key), seconds);
            }
            result = responseResult;
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("JedisService.set 出错[key=" + key + ",value=" + value + "]", ex);
        } finally {
            closeResource(jedis);
        }
        return result;
    }
    
    public String get(String key) {
        // TODO Auto-generated method stub
        String resultData = "";
        Jedis jedis = null;
        try {
            byte[] result = jedis.get(protostuffSerializer.serialize(key));
            if (result == null) {
                return "";
            }
            resultData = protostuffSerializer.deserialize(result);
        } catch (Exception ex) {
            logger.error("JedisService.get 出错[key=" + key + "]", ex);
        } finally {
            closeResource(jedis);
        }
        return resultData;
    }

    
    public String getSet(String key, String value, int seconds) {
        // TODO Auto-generated method stub
        String resultData = "";
        Jedis jedis = null;
        try {
            byte[] result = jedis.getSet(protostuffSerializer.serialize(key),
                    protostuffSerializer.serialize(value));
            if (result == null) {
                result = protostuffSerializer.serialize(value);
            }
            jedis.expire(protostuffSerializer.serialize(key), seconds);
            return protostuffSerializer.deserialize(result);
        } catch (Exception ex) {
            logger.error("JedisService.getSet 出错[key=" + key + ",value=" + value + "]", ex);
        } finally {
            closeResource(jedis);
        }
        return resultData;
    }

    
    public void delKey(String key) {
        // TODO Auto-generated method stub
        try {
            jedis.del(protostuffSerializer.serialize(key));
        } catch (Exception ex) {
            logger.error("JedisService.delKey 出错[key=" + key, ex);
        }
    }

    
    public void delNativeKey(String key) {
        try {
            jedis.del(key);
        } catch (Exception ex) {
            logger.error("del native key err: " + key, ex);
        }
    }

    
    public Long geoadd(String key, double longitude, double latitude, byte[] obj) {
        // TODO Auto-generated method stub
        Jedis jedis = null;
        try {
            return jedis.geoadd(protostuffSerializer.serialize(key), longitude, latitude, obj);
        } catch (Exception ex) {
            logger.error("JedisService.geoadd 出错[key=" + key + "]", ex);
        } finally {
            closeResource(jedis);
        }
        return null;
    }

    
    public List<GeoRadiusResponse> georadius(String key, double longitude, double latitude) {
        // TODO Auto-generated method stub
        GeoUnit geoUnit = GeoUnit.KM;
        GeoRadiusParam param = GeoRadiusParam.geoRadiusParam();
        param.withDist();
        param.sortAscending();
        param.count(10);
        param.withCoord();
        double SEARCH_DISTANCE = 10000.0;// 查询距离10000km
        Jedis jedis = null;
        List<GeoRadiusResponse> geoRadiusResponses = null;
        try {
            // jedis = jedisSentinelPool.getResource();
            geoRadiusResponses = jedis.georadius(protostuffSerializer.serialize(key), longitude, latitude,
                    SEARCH_DISTANCE, geoUnit, param);
        } catch (Exception ex) {
            logger.error("JedisService.geoadd 出错[key=" + key + "]", ex);
        } finally {
            closeResource(jedis);
        }
        return geoRadiusResponses;
    }

    @SuppressWarnings("unchecked")
    
    public Map<String, Object> getMapData(String key) {
        // TODO Auto-generated method stub
        Map<String, Object> resultData = new HashMap<String,Object>();
        Jedis jedis = null;
        try {
            byte[] result = jedis.get(protostuffSerializer.serialize(key));
            if (result == null) {
                return null;
            }
            String resultDataStr = protostuffSerializer.deserialize(result);
            resultData = JsonUtil.json2Map(resultDataStr);
        } catch (Exception ex) {
            logger.error("JedisService.get 出错[key=" + key + "]", ex);
        } finally {
            closeResource(jedis);
        }
        return resultData;
    }

    /**
     * 回收释放资源
     *
     * @param jedis
     */
    private void closeResource(Jedis jedis) {
        try {
            if (jedis != null) {
                jedis.close();
            }
            // if(jedis!=null){
            // jedis.close();
            // }
        } catch (Exception e) {
            logger.error("JedisServicecloseResource error.", e);
        }
    }

    
    public String getLockValue(String key) {
        return jedis.get(key);
    }

    /**
     * 方法名称：lock <br>
     * 描述： 如为第一次，则加上锁,每次调用值会自动加一(第一次返回false，后面有锁返回true)<br>
     * 作者：100196 <br>
     * 修改日期：2017年7月31日下午3:56:18
     *
     * @see
     *      int)
     * @param key
     * @param seconds
     * @return
     */
    
    public boolean lock(String key, int seconds) {
        try {
            // 如为第一次，则加上锁，返回false,10秒
            if (jedis.incr(key) == 1) {
                jedis.expire(key, seconds);
                return false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return true;
    }

    
    public void unlock(String key) {
        try {
            jedis.del(key);
        } catch (Exception e) {
            logger.error("JedisServicecloseResource error.", e);
        }
    }

    
    public List<GeoRadiusResponse> georadius(String key, double longitude, double latitude, double radius, int size) {
        GeoUnit geoUnit = GeoUnit.KM;
        GeoRadiusParam param = GeoRadiusParam.geoRadiusParam();
        param.withDist();
        param.sortAscending();
        param.count(size);
        param.withCoord();
        double SEARCH_DISTANCE =radius;
        Jedis jedis = null;
        List<GeoRadiusResponse> geoRadiusResponses = null;
        try {
            geoRadiusResponses = jedis.georadius(protostuffSerializer.serialize(key), longitude, latitude,
                    SEARCH_DISTANCE, geoUnit, param);
        } catch (Exception ex) {
            logger.error("JedisService.geoadd 出错[key=" + key + "]", ex);
        } finally {
            closeResource(jedis);
        }
        return geoRadiusResponses;
    }

    
    public Long setNx(String key, String value) {
        Long result = 0L;
        try {
            result = jedis.setnx(key, value);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    
    public String getSetNx(String key, String value) {
        String result = "";
        try {
            result = jedis.getSet(key, value);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }
    @Override
    public Long lpush(String key,String value){
        Long result = jedis.lpush(protostuffSerializer.serialize(key),protostuffSerializer.serialize(value));
        return result;
    }
    @Override
    public String rpop(String key){
        String resultData = "";
        try {
            byte[] result = jedis.rpop(protostuffSerializer.serialize(key));
            if (result == null) {
                return "";
            }
            resultData = protostuffSerializer.deserialize(result);
        } catch (Exception ex) {
            logger.error("JedisService.get 出错[key=" + key + "]", ex);
        } finally {
            closeResource(jedis);
        }
        return resultData;
    }
    @Override
    public List<String> rbpop(String key){
        String resultData = "";
        List<String> resultList = new ArrayList();
        try {
            List<byte[]> result = jedis.brpop(5,protostuffSerializer.serialize(key));
            if (result == null) {
                return null;
            }
            for(byte[]a:result){
                resultData = protostuffSerializer.deserialize(a);
                resultList.add(resultData);
            }

        } catch (Exception ex) {
            logger.error("JedisService.get 出错[key=" + key + "]", ex);
        } finally {
            closeResource(jedis);
        }
        return resultList;
    }
}
