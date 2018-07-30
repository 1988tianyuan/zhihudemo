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

    //存储entity的点赞列表，以实体id作为key，将用户信息存入到相应的set容器中
    public long like(int userId, int entityId, int entityType){
        String user = String.valueOf(userId);
        String likeKey = RedisKeyUtil.getLikeKey(entityId, entityType);
        String dislikeKey = RedisKeyUtil.getDisLikeKey(entityId, entityType);
        jedisAdapter.sadd(likeKey, user);
        jedisAdapter.srem(dislikeKey, user);
        this.userLikeSet(userId, entityId, entityType);
        return jedisAdapter.scard(likeKey);
    }

    //存储entity的点踩列表，以实体id作为key，将用户信息存入到相应的set容器中
    public long dislike(int userId, int entityId, int entityType){
        String user = String.valueOf(userId);
        String dislikeKey = RedisKeyUtil.getDisLikeKey(entityId, entityType);
        String likeKey = RedisKeyUtil.getLikeKey(entityId, entityType);
        jedisAdapter.sadd(dislikeKey, user);
        jedisAdapter.srem(likeKey, user);
        this.userLikeSetRemove(userId, entityId, entityType);
        return jedisAdapter.scard(dislikeKey);
    }



    //针对某个entity判断该用户是否点赞
    public int likeStatus(int userId, int entityId, int entityType){
        String user = String.valueOf(userId);
        String dislikeKey = RedisKeyUtil.getDisLikeKey(entityId, entityType);
        String likeKey = RedisKeyUtil.getLikeKey(entityId, entityType);
        if(jedisAdapter.sismember(likeKey, user)){
            return 1;
        }
        return jedisAdapter.sismember(dislikeKey, user) ? -1 : 0;
    }

    //获取某个entity被点赞的数目
    public long likeNum(int entityId, int entityType){
        String likeKey = RedisKeyUtil.getLikeKey(entityId, entityType);
        return jedisAdapter.scard(likeKey);
    }
    //获取某个entity被点踩的数目
    public long dislikeNum(int entityId, int entityType){
        String disLikeKey = RedisKeyUtil.getDisLikeKey(entityId, entityType);
        return jedisAdapter.scard(disLikeKey);
    }

    //存储user的点赞列表，以用户id作为key，将entity信息存入到相应的set容器中
    public long userLikeSet(int userId, int entityId, int entityType){
        String likeKeyByUser = RedisKeyUtil.getLikeKeyByUser(userId);
        String entity = String.valueOf(entityId)+":"+String.valueOf(entityType);
        jedisAdapter.sadd(likeKeyByUser, entity);
        return jedisAdapter.scard(likeKeyByUser);
    }

    //存储user的点赞列表，以用户id作为key，将entity信息存入到相应的set容器中
    public long userLikeSetRemove(int userId, int entityId, int entityType){
        String likeKeyByUser = RedisKeyUtil.getLikeKeyByUser(userId);
        String entity = String.valueOf(entityId)+":"+String.valueOf(entityType);
        jedisAdapter.srem(likeKeyByUser, entity);
        return jedisAdapter.scard(likeKeyByUser);
    }

    //返回某个user的点赞总数
    public long likeCountByUser(int uid){
        String likeKeyByUser = RedisKeyUtil.getLikeKeyByUser(uid);
        return jedisAdapter.scard(likeKeyByUser);
    }

}
