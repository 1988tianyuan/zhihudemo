package com.liugeng.zhihudemo.service;

import com.liugeng.zhihudemo.dao.MessageDao;
import com.liugeng.zhihudemo.pojo.Message;
import com.liugeng.zhihudemo.pojo.User;
import com.liugeng.zhihudemo.pojo.ViewObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MessageService {
    @Autowired
    MessageDao messageDao;
    @Autowired
    SensitiveWordsService sensitiveWordsService;
    @Autowired
    UserService userService;

    public int addMessage(int fromId, int toId, String content){
        Message message = new Message();
        message.setContent(content);
        message.setFromId(fromId);
        message.setToId(toId);
        if(fromId<toId){
            message.setConversationId(String.format("%d_%d", fromId, toId));
        }else {
            message.setConversationId(String.format("%d_%d", toId, fromId));
        }
        message.setCreateDate(new Date());
        message.setHasRead(0);
        message.setContent(sensitiveWordsService.filter(message.getContent()));
        return messageDao.addMessage(message)>0 ? message.getId() : 0;
    }

    public List<ViewObject> getConversationDetail(String conversationId, int offset, int limit){
        List<Message> messageList = messageDao.getConversationDetail(conversationId, offset, limit);
        List<ViewObject> messages = new ArrayList<>();
        for(Message m:messageList){
            ViewObject v = new ViewObject();
            v.set("message", m);
            User fromUser = userService.getUser(m.getFromId());
            if(fromUser == null){
                continue;
            }
            v.set("fromUser", fromUser);
            messages.add(v);
        }
        return messages;
    }

    public List<ViewObject> getConversationList(int localId){
        List<Message> conversationList = messageDao.getConversationList(localId);
        List<ViewObject> conversations = new ArrayList<>();
        for(Message c:conversationList){
            ViewObject v = new ViewObject();
            v.set("conversation", c);
            v.set("user", userService.getUser(c.getFromId()));
            v.set("unreadNum", unreadCount(localId, c.getConversationId()));
            conversations.add(v);
        }
        return conversations;
    }

    public int unreadCount(int toId, String conversationId){
        return messageDao.unreadCount(toId, conversationId);
    }

    public void updateUnread(String conversationId){
        messageDao.updateUnread(conversationId);
    }

    public void deleteConversation(String conversationId, int targetId){
        messageDao.deleteFromConversation(conversationId, targetId);
        messageDao.deleteToConversation(conversationId, targetId);
    }
}
