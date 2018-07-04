package com.liugeng.zhihudemo.async.handler;

import com.liugeng.zhihudemo.async.EventHandler;
import com.liugeng.zhihudemo.async.EventModel;
import com.liugeng.zhihudemo.async.EventType;
import com.liugeng.zhihudemo.pojo.User;
import com.liugeng.zhihudemo.service.CommentService;
import com.liugeng.zhihudemo.service.MessageService;
import com.liugeng.zhihudemo.service.UserService;
import com.liugeng.zhihudemo.utils.ZhihuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class FollowHandler implements EventHandler{
    @Autowired
    MessageService messageService;
    @Autowired
    UserService userService;

    @Override
    public void doHandle(EventModel event) {
        int fromId = ZhihuUtils.SYSTEM_USERID;
        int toId = event.getEntityOwnerId();
        User follower = userService.getUser(event.getActorId());
        String followMsg;
        if(event.getEntityType() == CommentService.ENTITY_USER){
            followMsg = "用户：" +follower.getName()+ " 关注了你。";
        }else {
            followMsg = "用户：" +follower.getName()+ " 关注了你的问题：http://127.0.0.1:8080/question/"+event.getEntityId();
        }
        messageService.addMessage(fromId, toId, followMsg);
    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.FOLLOW, EventType.UNFOLLOW);
    }
}
