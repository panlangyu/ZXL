package com.travelsky.ypb.business.task;

import com.travelsky.ypb.configuration.AppConfig;
import com.travelsky.ypb.domain.log.Log;
import com.travelsky.ypb.publics.HttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by huc on 2017/11/27.
 * [一级]缓存管理
 */
@Component
public class CacheManager{

    @Autowired
    protected AppConfig appConfig;

    @Autowired
    protected HttpClient httpClient;

    private static HashMap cacheMap = new HashMap();
    public  static long TOKEN_TIME_OUT = 900000; //TODO 缓存过期时间
    //单实例构造方法
    private CacheManager() {
        super();
    }

    /**
     * 获取布尔值的缓存
     * @param key key
     * @return boolean
     */
    public boolean getSimpleFlag(String key){
        try{
            return (Boolean) cacheMap.get(key);
        }catch(NullPointerException e){
            return false;
        }
    }

    /**
     * 获取long类型的缓存
     * @param key key
     * @return long
     */
    public long getServerStartdt(String key){
        try {
            return (Long)cacheMap.get(key);
        } catch (Exception ex) {
            return 0;
        }
    }

    /**
     * 设置布尔值的缓存
     * @param key key
     * @param flag boolean
     * @return boolean
     */
    public synchronized boolean setSimpleFlag(String key,boolean flag){
        if (flag && getSimpleFlag(key)) {//假如为真不允许被覆盖
            return false;
        }else{
            cacheMap.put(key, flag);
            return true;
        }
    }

    /**
     * 设置long类型的缓存
     * @param key key
     * @param serverbegrundt long
     * @return boolean
     */
    public synchronized boolean setSimpleFlag(String key,long serverbegrundt){
        if (cacheMap.get(key) == null) {
            cacheMap.put(key,serverbegrundt);
            return true;
        }else{
            return false;
        }
    }

    /**
     * 得到缓存。同步静态方法
     * @param key key
     * @return cache
     */
    private synchronized Cache getCache(String key) {

        return (Cache) cacheMap.get(key);
    }

    /**
     * 判断是否存在一个缓存
     * @param key key
     * @return boolean
     */
    private synchronized boolean hasCache(String key) {

        return cacheMap.containsKey(key);
    }

    /**
     * 清除所有缓存
     */
    public synchronized void clearAll() {
        cacheMap.clear();
    }

    /**
     * 清除某一类特定缓存,通过遍历HASHMAP下的所有对象，来判断它的KEY与传入的TYPE是否匹配
     * @param type key的前缀
     */
    public synchronized void clearAll(String type) {
        Iterator i = cacheMap.entrySet().iterator();
        String key;
        try {
            while (i.hasNext()) {
                java.util.Map.Entry entry = (java.util.Map.Entry) i.next();
                key = (String) entry.getKey();
                if (key.startsWith(type)) { //如果匹配则删除掉
                    clearOnly(key);
                }
            }
        } catch (Exception ex) {

        }
    }

    /**
     * 清除指定的缓存
     * @param key key
     */
    public synchronized void clearOnly(String key) {

        cacheMap.remove(key);
    }

    /**
     * 载入缓存
     * @param key key
     * @param obj cache
     */
    public synchronized void putCache(String key, Cache obj) {

        cacheMap.put(key, obj);
    }

    /**
     * 获取token缓存信息
     * @param key key
     * @return cache
     */
    public Cache getCacheToken(String key) {
        Cache cache = null;
        if (hasCache(key)){
            cache = tokenCache(key);
        }else {
            Log.i(CacheManager.class,"getCacheToken","缓存信息不存在");
            cache = tokenCache(key);
        }
        return cache;
    }

    /**
     * 获取标准缓存信息
     * @param key
     * @return cache
     */
    public Cache getCacheInfo(String key) {
        Cache cache = null;
        if (hasCache(key)){
            cache = getCache(key);
            if (cacheExpired(cache)) { //调用判断是否终止方法
                cache.setExpired(true);
                clearOnly(key);
                cache = null;
            }else {
                cache = getCache(key);
            }
        }else {
            Log.i(CacheManager.class,"getCacheInfo","缓存信息不存在");
        }
        return cache;
    }


    /**
     * token 缓存
     * @param key key
     * @return cache
     */
    public Cache tokenCache(String key){
        Cache cache = getCache(key);
        if (cache == null) {
            String token = httpClient.getToken(key);
            this.putCacheInfo(key, token, CacheManager.TOKEN_TIME_OUT, true);
            cache = getCache(key);
        }else {
            if (cacheExpired(cache)) {
                //调用判断是否终止方法
                String token = httpClient.getToken(key);
                cache.setExpired(true);
                this.putCacheInfo(key, token, CacheManager.TOKEN_TIME_OUT, true);
                cache = getCache(key);
            }
        }
        return cache;
    }


    /**
     * 载入缓存信息
     * @param key key
     * @param obj cache
     * @param dt dt
     * @param expired boolean
     */
    public void putCacheInfo(String key, Object obj, long dt,boolean expired) {
        Cache cache = new Cache();
        cache.setKey(key);
        cache.setTimeOut(dt + System.currentTimeMillis()); //设置多久后更新缓存
        cache.setValue(obj);
        cache.setExpired(expired); //缓存默认载入时，终止状态为FALSE
        cacheMap.put(key, cache);
    }

    /**
     * 重写载入缓存信息方法
     * @param key ky
     * @param obj cache
     * @param dt dt
     */
    public void putCacheInfo(String key,Object obj,long dt){
        Cache cache = new Cache();
        cache.setKey(key);
        cache.setTimeOut(dt+System.currentTimeMillis());
        cache.setValue(obj);
        cache.setExpired(false);
        cacheMap.put(key,cache);
    }


    /**
     * 判断缓存是否终止
     * @param cache cache
     * @return boolean
     */
    public boolean cacheExpired(Cache cache) {
        if (null == cache) { //传入的缓存不存在
            return false;
        }
        long nowDt = System.currentTimeMillis(); //系统当前的毫秒数
        long cacheDt = cache.getTimeOut(); //缓存内的过期毫秒数
        if (cacheDt <= 0||cacheDt > nowDt) { //过期时间小于等于零时,或者过期时间大于当前时间时，则为FALSE
            return false;
        } else { //大于过期时间 即过期
            Log.i(CacheManager.class,"cacheExpired","缓存过期");
            return true;
        }
    }

    /**
     * 获取缓存中的大小
     * @return int
     */
    public int getCacheSize() {

        return cacheMap.size();
    }

    /**
     * 获取指定的类型的大小
     * @param type type
     * @return int
     */
    public int getCacheSize(String type) {
        int k = 0;
        Iterator i = cacheMap.entrySet().iterator();
        String key;
        try {
            while (i.hasNext()) {
                java.util.Map.Entry entry = (java.util.Map.Entry) i.next();
                key = (String) entry.getKey();
                if (key.indexOf(type) != -1) { //如果匹配则删除掉
                    k++;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return k;
    }

    /**
     * 获取缓存对象中的所有键值名称
     * @return ArrayList
     */
    public ArrayList getCacheAllkey() {
        ArrayList a = new ArrayList();
        try {
            Iterator i = cacheMap.entrySet().iterator();
            while (i.hasNext()) {
                java.util.Map.Entry entry = (java.util.Map.Entry) i.next();
                a.add(entry.getKey());
            }
        } catch (Exception ex) {

        } finally {
            return a;
        }
    }

    /**
     * 获取缓存对象中指定类型的键值名称
     * @param type type
     * @return ArrayList
     */
    public ArrayList getCacheListkey(String type) {
        ArrayList a = new ArrayList();
        String key;
        try {
            Iterator i = cacheMap.entrySet().iterator();
            while (i.hasNext()) {
                java.util.Map.Entry entry = (java.util.Map.Entry) i.next();
                key = (String) entry.getKey();
                if (key.indexOf(type) != -1) {
                    a.add(key);
                }
            }
        } catch (Exception ex) {} finally {
            return a;
        }
    }




}
