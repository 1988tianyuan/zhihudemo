package com.liugeng.zhihudemo.service;

import com.liugeng.zhihudemo.pojo.ViewObject;
import com.liugeng.zhihudemo.utils.JedisAdapter;
import com.liugeng.zhihudemo.utils.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class FollowService {
    @Autowired
    JedisAdapter jedisAdapter;

    public boolean follow(int userId, int entityType, int entityId){
        String followerKey = RedisKeyUtil.getFollowerKey(entityType, entityId);
        String followeeKey = RedisKeyUtil.getFolloweeKey(userId, entityType);
        Date date = new Date();
        Jedis jedis = jedisAdapter.getJedis();
        Transaction tx = jedisAdapter.redisMulti(jedis);
        tx.zadd(followerKey, date.getTime(), String.valueOf(userId));
        tx.zadd(followeeKey, date.getTime(), String.valueOf(entityId));
        List<Object> result = jedisAdapter.redisExec(tx, jedis);   //返回事务中每个操作的执行结果
        return result.size() == 2 && (long)result.get(0)>0 && (long)result.get(1)>0;
    }

    public boolean unFollow(int userId, int entityType, int entityId){
        String followerKey = RedisKeyUtil.getFollowerKey(entityType, entityId);
        String followeeKey = RedisKeyUtil.getFolloweeKey(userId, entityType);
        Jedis jedis = jedisAdapter.getJedis();
        Transaction tx = jedisAdapter.redisMulti(jedis);
        tx.zrem(followerKey, String.valueOf(userId));
        tx.zrem(followeeKey, String.valueOf(entityId));
        List<Object> result = jedisAdapter.redisExec(tx, jedis);   //返回事务中每个操作的执行结果
        return result.size() == 2 && (long)result.get(0)>0 && (long)result.get(1)>0;
    }

    public List<Integer> getFollowers(int entityType, int entityId, int offset, int count){
        String followerKey = RedisKeyUtil.getFollowerKey(entityType, entityId);
        Set<String> followers = jedisAdapter.zrevrange(followerKey, offset, count);
        List<Integer> followerList = new ArrayList<>();
        for(String s : followers){
            followerList.add(Integer.valueOf(s));
        }
        return followerList;
    }

    public List<Integer> getFollowees(int entityType, int userId, int offset, int count){
        String followeeKey = RedisKeyUtil.getFolloweeKey(userId, entityType);
        Set<String> followers = jedisAdapter.zrevrange(followeeKey, offset, count);
        List<Integer> followerList = new ArrayList<>();
        for(String s : followers){
            followerList.add(Integer.valueOf(s));
        }
        return followerList;
    }

    public long getFollowerCount(int entityType, int entityId){
        String followerKey = RedisKeyUtil.getFollowerKey(entityType, entityId);
        return jedisAdapter.zcard(followerKey);
    }

    public long getFolloweeCount(int userId, int entityType){
        String followeeKey = RedisKeyUtil.getFolloweeKey(userId, entityType);
        return jedisAdapter.zcard(followeeKey);
    }

    public boolean isFollower(int entityType, int entityId, int userId){
        String followerKey = RedisKeyUtil.getFollowerKey(entityType, entityId);
        return jedisAdapter.zscore(followerKey, String.valueOf(userId)) > 0;
    }

    public List<Integer> getSharedFollowees(int uid1, int uid2, int entityType){
        String followeeKey1 = RedisKeyUtil.getFolloweeKey(uid1, entityType);
        String followeeKey2 = RedisKeyUtil.getFolloweeKey(uid2, entityType);
        Set<String> sharedFollowees = jedisAdapter.zinterStore(followeeKey1, followeeKey2);
        List<Integer> sharedFolloweeList = new ArrayList<>();
        if(!sharedFollowees.isEmpty()){
            for(String s : sharedFollowees){
                sharedFolloweeList.add(Integer.valueOf(s));
            }
        }
        return sharedFolloweeList;
    }

}
