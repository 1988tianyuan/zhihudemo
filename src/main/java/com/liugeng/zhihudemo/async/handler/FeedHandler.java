package com.liugeng.zhihudemo.async.handler;

import com.alibaba.fastjson.JSONObject;
import com.liugeng.zhihudemo.async.EventHandler;
import com.liugeng.zhihudemo.async.EventModel;
import com.liugeng.zhihudemo.async.EventType;
import com.liugeng.zhihudemo.pojo.Comment;
import com.liugeng.zhihudemo.pojo.Feed;
import com.liugeng.zhihudemo.pojo.Question;
import com.liugeng.zhihudemo.pojo.User;
import com.liugeng.zhihudemo.service.*;
import com.liugeng.zhihudemo.utils.JedisAdapter;
import com.liugeng.zhihudemo.utils.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sun.plugin2.ipc.Event;

import java.util.*;

@Component
public class FeedHandler implements EventHandler{
    @Autowired
    UserService userService;
    @Autowired
    QuestionService questionService;
    @Autowired
    FeedService feedService;
    @Autowired
    FollowService followService;
    @Autowired
    JedisAdapter jedisAdapter;
    @Autowired
    CommentService commentService;

    @Override
    public void doHandle(EventModel event) {
        Feed feed = new Feed();
        feed.setCreateDate(new Date());
        feed.setType(event.getType().getValue());
        feed.setUserId(event.getActorId());
        feed.setData(buildFeedData(event));
        if(feed.getData() == null){
            return;
        }
        feedService.addFeed(feed);//将这条新鲜事持久化到数据库中

        //给关注这个用户的人推送这个消息
        List<Integer> followers = followService.getFollowers(CommentService.ENTITY_USER, event.getActorId(), 0 , Integer.MAX_VALUE);
        for(Integer i:followers){
            String timelineKey = RedisKeyUtil.getTimelineKey(i);
            jedisAdapter.lpush(timelineKey, String.valueOf(feed.getId()));
        }
    }

    //订阅事件
    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.COMMENT, EventType.LIKE, EventType.FOLLOW);
    }

    //将需要推送的其他信息存为json文本
    private String buildFeedData(EventModel event){
        Map<String, String> map = new HashMap<>();
        User actorUser = userService.getUser(event.getActorId());
        if(actorUser == null){return null;}
        map.put("userName", actorUser.getName());
        map.put("userHead", actorUser.getHeadUrl());
        map.put("userId", String.valueOf(event.getActorId()));
        if(event.getType() == EventType.COMMENT || event.getType() == EventType.FOLLOW && event.getEntityType() == CommentService.ENTITY_QUESTION){
            Question question = questionService.getQuestionById(event.getEntityId());
            map.put("questionId", String.valueOf(question.getId()));
            map.put("questionTitle", question.getTitle());
            return JSONObject.toJSONString(map);
        }else if(event.getType() == EventType.LIKE){
            Comment comment = commentService.getCommentById(event.getEntityId());
            map.put("commentOwnerId", String.valueOf(event.getEntityOwnerId()));
            map.put("commentOwnerName", userService.getUser(event.getEntityOwnerId()).getName());
            map.put("commentContent", event.getExt("comment"));
            map.put("questionTitle", event.getExt("question"));
            map.put("questionId", String.valueOf(comment.getEntityId()));
            return JSONObject.toJSONString(map);
        }
        return null;
    }
}
