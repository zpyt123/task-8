package com.jnshu.util;

import com.whalin.MemCached.MemCachedClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

public final class MemcachedUtil {
    private static Logger logger = LoggerFactory.getLogger(MemcachedUtil.class);
     private static String MEMCACHE_POOL_NAME = "memCachedPool";
    //     private static String MEMCACHE_POOL_NAME = "default";
    /*
    *Memcached测试单位
     */
    private static MemCachedClient memCachedClient = new MemCachedClient();

    //创建连接池
    static {
        //获取实际的连接池的实例
        if (memCachedClient == null) {
            memCachedClient = new MemCachedClient("MEMCACHE_POOL_NAME");
        }
    }
    /*
    *构造函数
    */
    private MemcachedUtil() {
    }

    /**
     * 向缓存添加新的键值对。如果键已经存在，则之前的值将被替换.
     * @param key 键
     * @param value 值
     * @return boolean
     */
    public static boolean set(String key, Object value) {
        return setExp(key, value,null);
    }

    /**
     * 向缓存添加新的键值对。如果键已经存在，则之前的值将被替换.
     * @param key 键
     * @param value 值
     * @param expire 过期时间 New Date(1000*10)：十秒后过期
     * @return boolean
     */
    public static boolean set(String key, Object value, Date expire) {
        return setExp(key, value, expire);
    }

    /**
     * 向缓存添加新的键值对。如果键已经存在，则之前的值将被替换.
     * @param key 键
     * @param value 值
     * @param expire 过期时间 New Date(1000*10)：十秒后过期
     * @return boolean
     */
    private static boolean setExp(String key, Object value, Date expire) {
        boolean flag = false;
        try {
            flag = memCachedClient.set(key, value, expire);
        } catch (Exception e) {
            logger.error("Memcached set方法报错，key值：" + key, "\r\n" + exceptionWrite(e));
        }
        return flag;
    }

    /**
     * 仅当缓存中不存在键时，add 命令才会向缓存中添加一个键值对.
     *
     * @param key
     *            键
     * @param value
     *            值
     * @return boolean
     */
    public static boolean add(String key, Object value) {
        return addExp(key, value, null);
    }

    /**
     * 仅当缓存中不存在键时，add 命令才会向缓存中添加一个键值对.
     *
     * @param key
     *            键
     * @param value
     *            值
     * @param expire
     *            过期时间 New Date(1000*10)：十秒后过期
     * @return boolean
     */
    public static boolean add(String key, Object value, Date expire) {
        return addExp(key, value, expire);
    }

    /**
     * 仅当缓存中不存在键时，add 命令才会向缓存中添加一个键值对.
     *
     * @param key
     *            键
     * @param value
     *            值
     * @param expire
     *            过期时间 New Date(1000*10)：十秒后过期
     * @return boolean
     */
    private static boolean addExp(String key, Object value, Date expire) {
        boolean flag = false;
        try {
            flag = memCachedClient.add(key, value, expire);
        } catch (Exception e) {
            logger.error("Memcached set方法报错，key值：" + key, "\r\n" + exceptionWrite(e));
        }
        return flag;
    }

/**
 * 仅当键已经存在时，replace 命令才会替换缓存中的键.
 *
 * @param key
 *            键
 * @param value
 *            值
 * @return boolean
 */
    public static  boolean replace(String key, Object value){
    return replaceExp(key,value,null);
    }


    /**
     * 仅当键已经存在时，replace 命令才会替换缓存中的键.
     *
     * @param key
     *            键
     * @param value
     *            值
     * @param expire
     *            过期时间 New Date(1000*10)：十秒后过期
     * @return boolean
     */
    private   static  boolean replaceExp(String key,Object value, Date expire){
        boolean flag = false;
        try {
            flag = memCachedClient.replace(key, value, expire);
        }catch (Exception e){
            logger.error("Memcached replace方法报错，key值：" + key + "\r\n" + exceptionWrite(e));
        }
        return flag;
    }
    /**
     * get 命令用于检索与之前添加的键值对相关的值.
     *
     * @param key
     *            键
     * @return boolean
     */
    public  static  Object get(String key){
        Object o = null;
        try{
            o = memCachedClient.get(key);
        }catch (Exception e){
            logger.error("Memcached get方法报错，key值："+"\r\n" +exceptionWrite(e));
        }
        return  o;
    }
    /**
     * 删除 memcached 中的任何现有值.
     *
     * @param key
     *            键
     * @return boolean
     */
    public  static  boolean delete(String key){
        return memCachedClient.delete(key);
    }
    /**
     * 清理缓存中的所有键/值对.
     *
     * @return boolean
     */
    public  static  boolean  flashAll(){
        boolean flag =false;
        try {
            flag = memCachedClient.flushAll();
        }catch (Exception e){
            logger.error("Memcached flushAll方法报错\r\n:"+exceptionWrite(e));
        }
        return flag;
    }
    /**
     * 返回异常栈信息，String类型.
     *
     * @param e
     *            Exception
     * @return boolean
     */
    public  static String exceptionWrite(Exception e){
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            pw.flush();
        return sw.toString();
    }
}


