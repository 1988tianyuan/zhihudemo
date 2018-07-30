package com.liugeng.zhihudemo.async.handler;

import com.liugeng.zhihudemo.async.EventHandler;
import com.liugeng.zhihudemo.async.EventModel;
import com.liugeng.zhihudemo.async.EventProducer;
import com.liugeng.zhihudemo.async.EventType;
import com.liugeng.zhihudemo.service.SearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;


@Component
public class AddQuestionHandler implements EventHandler {
    @Autowired
    SearchService searchService;

    private static final Logger logger = LoggerFactory.getLogger(AddQuestionHandler.class);

    @Override
    public void doHandle(EventModel event) {
        String title = event.getExt("question_title");
        String content = event.getExt("question_content");
        int id = event.getEntityId();
        searchService.newQuestionIndex(id, title, content);
    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.ADD_QUESTION);
    }
}
