package com.liugeng.zhihudemo.async.handler;

import com.liugeng.zhihudemo.async.EventHandler;
import com.liugeng.zhihudemo.async.EventModel;
import com.liugeng.zhihudemo.async.EventType;
import com.liugeng.zhihudemo.utils.MailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class LoginExceptionHandler implements EventHandler{
    @Autowired
    MailSender mailSender;

    @Override
    public void doHandle(EventModel event) {
        String userName = event.getExt("userName");
        String email = event.getExt("email");
        String title = "Zhihu问答系统发现你登录IP存在异常";
        String temp = "loginExceptionTemp.ftl";
        Map<String, Object> model = new HashMap<>();
        model.put("userName", userName);
        mailSender.sendWithTemplate(email, title, temp, model);
    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.LOGIN);
    }
}
