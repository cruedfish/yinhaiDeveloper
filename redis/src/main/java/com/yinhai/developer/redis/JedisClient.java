package com.yinhai.developer.redis;

import redis.clients.jedis.GeoRadiusResponse;

import java.util.List;
import java.util.Map;

public interface JedisClient {

    /**
     * 是否存在
     * @param key
     * @return
     * @throws Exception
     */
    boolean exists(String key);

    /**
     * 缓存set值
     * @param key
     * @param value
     * @param seconds 缓存时间(不设设置则为0)
     * @return
     * @throws Exception
     */
    String set(String key, String value, int seconds);

    /**
     * 重新缓存getSet值
     * @param key
     * @param value
     * @param seconds 缓存时间(不设设置则为0) 应重新赋值会删除原有的过期时间，故这里需重新设置
     * @return
     * @throws Exception
     */
    String getSet(String key, String value, int seconds);

    /**
     * 获取set值
     * @param key
     * @return
     * @throws Exception
     */
    String get(String key);

    /**
     * 添加地理位置
     * @param key
     * @param longitude
     * @param latitude
     * @param obj
     * @return
     */
    Long geoadd(String key, double longitude, double latitude, byte[] obj);

    /**
     *  地理位置查询
     * @param key
     * @param longitude
     * @param latitude
     * @return
     */
    List<GeoRadiusResponse> georadius(String key, double longitude, double latitude);

    /**
     * 删除key
     * @param key
     */
    void delKey(String key);

    /**
     * 删除 native key
     * @param key
     */
    void delNativeKey(String key);

    /**方法名称: getMapData<br>
     * 描述：获取Map格式数据
     * 作者: 100196
     * 修改日期：2017年7月20日下午3:25:16
     * @param key
     * @return
     */
    Map<String,Object> getMapData(String key);

    /**方法名称: lock<br>
     * 描述：避免重复提交，枷锁
     * 作者: 100196
     * 修改日期：2017年7月18日下午1:58:17
     * @param key,seconds
     * @return
     */
    boolean lock(String key, int seconds);

    /**方法名称: unlock<br>
     * 描述：redis解锁
     * 作者: 100196
     * 修改日期：2017年7月18日下午2:48:05
     * @param key
     */
    void unlock(String key);

    /**方法名称: getLockValue<br>
     * 描述：统计锁定次数
     * 作者: 100196
     * 修改日期：2017年7月18日下午3:25:26
     * @param key
     * @return
     */
    String getLockValue(String key);

    /**
     *  地理位置查询
     * @param key
     * @param longitude
     * @param latitude
     * @param size
     * @param radius
     * @return
     */
    List<GeoRadiusResponse> georadius(String key, double longitude, double latitude, double radius, int size);

    Long setNx(String key, String value);

    String getSetNx(String key, String value);

    Long lpush(String key, String value);

    String rpop(String key);

    public List<String> rbpop(String key);

    public void zadd(String key,Integer timeout,String value);


}
