package com.liugeng.zhihudemo.async.handler;

import com.liugeng.zhihudemo.async.EventHandler;
import com.liugeng.zhihudemo.async.EventModel;
import com.liugeng.zhihudemo.async.EventType;
import com.liugeng.zhihudemo.pojo.Question;
import com.liugeng.zhihudemo.pojo.User;
import com.liugeng.zhihudemo.service.MessageService;
import com.liugeng.zhihudemo.service.QuestionService;
import com.liugeng.zhihudemo.service.UserService;
import com.liugeng.zhihudemo.utils.ZhihuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class CommentHandler implements EventHandler{
    @Autowired
    UserService userService;
    @Autowired
    QuestionService questionService;
    @Autowired
    MessageService messageService;

    @Override
    public void doHandle(EventModel event) {
        int fromId = ZhihuUtils.SYSTEM_USERID;
        int commentorId = event.getActorId();
        int questionId = event.getEntityId();
        User commentor = userService.getUser(commentorId);
        Question question = questionService.getQuestionById(questionId);
        int toId = question.getUserId();
        String commentMsg = event.getExt("commentMsg");
        String msg = "用户："+commentor.getName()+" 回答了你的问题“"+ question.getTitle() + "”：" + commentMsg;
        messageService.addMessage(fromId, toId, msg.substring(0, 200));
    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.COMMENT);
    }
}
