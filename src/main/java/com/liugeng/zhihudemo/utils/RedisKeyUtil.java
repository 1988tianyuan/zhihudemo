package com.liugeng.zhihudemo.utils;

import org.omg.CORBA.PUBLIC_MEMBER;

public class RedisKeyUtil {
    private static final String SPLIT = ":";
    private static final String BIZ_lIKE = "LIKE";
    private static final String BIZ_DISLIKE = "DISLIKE";
    private static final String BIZ_EVENTQUEUE = "EVENT_QUEUE";
    private static final String BIZ_FOLLOWER = "FOLLOWER";
    private static final String BIZ_FOLLOWEE = "FOLLOWEE";

    public static String getLikeKey(int entityId, int entityType){
         return BIZ_lIKE + SPLIT + entityId + SPLIT + entityType;
    }

    public static String getDisLikeKey(int entityId, int entityType){
        return BIZ_DISLIKE + SPLIT + entityId + SPLIT + entityType;
    }

    public static String getEventQueueKey(){
        return BIZ_EVENTQUEUE;
    }

    public static String getFollowerKey(int entityType, int entityId){
        return BIZ_FOLLOWER + SPLIT + String.valueOf(entityType) + SPLIT + String.valueOf(entityId);
    }

    public static String getFolloweeKey(int userId, int entityType){
        return BIZ_FOLLOWEE + SPLIT + String.valueOf(userId) + SPLIT + String.valueOf(entityType);
    }
}
