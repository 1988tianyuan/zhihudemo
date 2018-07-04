package com.liugeng.zhihudemo.service;

import com.liugeng.zhihudemo.utils.JedisAdapter;
import com.liugeng.zhihudemo.utils.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LikeService {
    @Autowired
    JedisAdapter jedisAdapter;
    @Autowired
    CommentService commentService;

    public long like(int userId, int entityId, int entityType){
        String user = String.valueOf(userId);
        String likeKey = RedisKeyUtil.getLikeKey(entityId, entityType);
        String dislikeKey = RedisKeyUtil.getDisLikeKey(entityId, entityType);
        jedisAdapter.sadd(likeKey, user);
        jedisAdapter.srem(dislikeKey, user);
        return jedisAdapter.scard(likeKey);
    }

    public long dislike(int userId, int entityId, int entityType){
        String user = String.valueOf(userId);
        String dislikeKey = RedisKeyUtil.getDisLikeKey(entityId, entityType);
        String likeKey = RedisKeyUtil.getLikeKey(entityId, entityType);
        jedisAdapter.sadd(dislikeKey, user);
        jedisAdapter.srem(likeKey, user);
        return jedisAdapter.scard(dislikeKey);
    }

    public int likeStatus(int userId, int entityId, int entityType){
        String user = String.valueOf(userId);
        String dislikeKey = RedisKeyUtil.getDisLikeKey(entityId, entityType);
        String likeKey = RedisKeyUtil.getLikeKey(entityId, entityType);
        if(jedisAdapter.sismember(likeKey, user)){
            return 1;
        }
        return jedisAdapter.sismember(dislikeKey, user) ? -1 : 0;
    }

    public long likeNum(int entityId, int entityType){
        String likeKey = RedisKeyUtil.getLikeKey(entityId, entityType);
        return jedisAdapter.scard(likeKey);
    }

    public long dislikeNum(int entityId, int entityType){
        String disLikeKey = RedisKeyUtil.getDisLikeKey(entityId, entityType);
        return jedisAdapter.scard(disLikeKey);
    }

    public long likeCountByUser(int uid, int entityType){
        long likeCount = 0;
        List<Integer> cidList = commentService.cidList();
        for(Integer cid:cidList){
            if(1 == likeStatus(uid, cid, entityType)){
                likeCount++;
            }
        }
        return likeCount;
    }

}
