package com.liugeng.zhihudemo.async.handler;

import com.liugeng.zhihudemo.async.EventHandler;
import com.liugeng.zhihudemo.async.EventModel;
import com.liugeng.zhihudemo.async.EventType;
import com.liugeng.zhihudemo.pojo.Message;
import com.liugeng.zhihudemo.pojo.User;
import com.liugeng.zhihudemo.service.MessageService;
import com.liugeng.zhihudemo.service.UserService;
import com.liugeng.zhihudemo.utils.ZhihuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class LikeHandler implements EventHandler{
    @Autowired
    MessageService messageService;
    @Autowired
    UserService userService;

    @Override
    public void doHandle(EventModel event) {
        int fromId = ZhihuUtils.SYSTEM_USERID;
        int toId = event.getEntityOwnerId();
        User user = userService.getUser(event.getActorId());
        String content = "用户"+user.getName()+"赞了问题“" + event.getExt("question") + "”中你的回答："+event.getExt("comment");
        messageService.addMessage(fromId, toId, content);
    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.LIKE);
    }
}
