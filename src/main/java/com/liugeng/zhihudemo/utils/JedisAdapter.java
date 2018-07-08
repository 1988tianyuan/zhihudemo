package com.liugeng.zhihudemo.utils;

import com.liugeng.zhihudemo.controller.MessageController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import redis.clients.jedis.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class JedisAdapter implements InitializingBean{

    private JedisPool pool;
    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);

    public static void print(int index, Object o){
        System.out.println(String.format("%d：%s", index, o.toString()));
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        pool = new JedisPool("redis://localhost:6379/10");
    }

    public Jedis getJedis(){
        return pool.getResource();
    }

    public Transaction redisMulti(Jedis jedis){
        try {
            return jedis.multi();
        } catch (Exception e) {
            logger.error("开启redis事务时发生异常：" + e.getMessage());
        }
        return null;
    }

    public List<Object> redisExec(Transaction tx, Jedis jedis){
        try {
            return tx.exec();
        } catch (Exception e) {
            logger.error("执行redis事务时发生异常：" + e.getMessage());
        } finally {
          if(tx != null){
              try {
                  tx.close();
              } catch (IOException e) {
                  e.printStackTrace();
              }
          }
          if(jedis != null){
              jedis.close();//一定记得把这个jedis连接关掉
          }
        }
        return null;
    }

    public long sadd(String setName, String value){
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.sadd(setName, value);
        } catch (Exception e) {
            logger.error("添加redis的set元素时出错："+e.getMessage());
        } finally {
            if(jedis != null){
                jedis.close();
            }
        }
        return 0;
    }

    public long srem(String setName, String value){
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.srem(setName, value);
        } catch (Exception e) {
            logger.error("删除redis的set元素时出错："+e.getMessage());
        } finally {
            if(jedis != null){
                jedis.close();
            }
        }
        return 0;
    }

    public long scard(String setName){
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.scard(setName);
        } catch (Exception e) {
            logger.error("返回set元素个数时出错："+e.getMessage());
        } finally {
            if(jedis != null){
                jedis.close();
            }
        }
        return 0;
    }

    public boolean sismember(String setName, String value){
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.sismember(setName, value);
        } catch (Exception e) {
            logger.error("判断set元素时出错："+e.getMessage());
        } finally {
            if(jedis != null){
                jedis.close();
            }
        }
        return false;
    }

    public long lpush(String key, String value){
        Jedis jedis = null;
        try{
            jedis = pool.getResource();
            return jedis.lpush(key, value);
        } catch (Exception e){
            logger.error("向队列 "+ key +" 中加入元素时发生错误："+e.getMessage());
        } finally {
            if(jedis != null){
                jedis.close();
            }
        }
        return 0;
    }

    public List<String> brpop(int timeOut, String key){
        Jedis jedis = null;
        try{
            jedis = pool.getResource();
            return jedis.brpop(0 ,key);
        } catch (Exception e){
            logger.error("阻塞获取异步消息队列的过程发生错误："+e.getMessage());
        } finally {
            if(jedis != null){
                jedis.close();
            }
        }
        return null;
    }

    public List<String> lrange(String key, int start, int end){
        Jedis jedis = pool.getResource();
        try {
            return jedis.lrange(key, start ,end);
        } catch (Exception e) {
            logger.error("发生异常：" + e.getMessage());
        } finally {
            if(jedis != null){
                jedis.close();
            }
        }
        return null;
    }

    public long zadd(String key, String value, double score){
        Jedis jedis = null;
        try {
            jedis = getJedis();
            jedis.zadd(key, score, value);
        } catch (Exception e) {
            logger.error("添加zset时发生异常：" + e.getMessage());
        } finally {
            if(jedis != null){
                jedis.close();
            }
        }
        return 0;
    }

    public Set<String> zrange(String key, int start, int end){
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.zrange(key, start, end);
        } catch (Exception e) {
            logger.error("查找zset时发生异常：" + e.getMessage());
        } finally {
            if(jedis != null){
                jedis.close();
            }
        }
        return null;
    }

    public Set<String> zrevrange(String key, int start, int end){
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.zrevrange(key, start, end);
        } catch (Exception e) {
            logger.error("查找zset时发生异常：" + e.getMessage());
        } finally {
            if(jedis != null){
                jedis.close();
            }
        }
        return null;
    }

    public long zcard(String key){
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.zcard(key);
        } catch (Exception e) {
            logger.error("查找zset元素个数时发生异常：" + e.getMessage());
        } finally {
            if(jedis != null){
                jedis.close();
            }
        }
        return 0;
    }

    public double zscore(String key, String member){
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.zscore(key, member);
        } catch (Exception e) {
            logger.error("查找zset分值时发生异常：" + e.getMessage());
        } finally {
            if(jedis != null){
                jedis.close();
            }
        }
        return 0;
    }

    public Set<String> zinterStore(String setName1, String setName2){
        Jedis jedis = null;
        try {
            jedis = getJedis();
            String dstKey = "sharedZset";
            jedis.zinterstore(dstKey, setName1, setName2);
            return jedis.zrangeByLex(dstKey, "-", "+");
        } catch (Exception e) {
            logger.error("查找set交集时发生异常：" + e.getMessage());
        } finally {
            if(jedis != null){
                jedis.close();
            }
        }
        return null;
    }
}
