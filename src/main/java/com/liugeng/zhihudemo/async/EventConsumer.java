package com.liugeng.zhihudemo.async;

import com.alibaba.fastjson.JSONObject;
import com.liugeng.zhihudemo.controller.MessageController;
import com.liugeng.zhihudemo.utils.ApplicationContextProvider;
import com.liugeng.zhihudemo.utils.JedisAdapter;
import com.liugeng.zhihudemo.utils.RedisKeyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class EventConsumer implements InitializingBean{
    @Autowired
    JedisAdapter jedisAdapter;
    @Autowired
    ApplicationContextProvider applicationContextProvider;

    private Map<EventType, List<EventHandler>> config = new HashMap<>();
    private ExecutorService executorService = Executors.newFixedThreadPool(10);
    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);

    @Override
    public void afterPropertiesSet() throws Exception {
        Map<String, EventHandler> handlers = applicationContextProvider.getApplicationContext().getBeansOfType(EventHandler.class);
        if(handlers != null){
            Set<Map.Entry<String, EventHandler>> entrySet = handlers.entrySet();
            for(Map.Entry<String, EventHandler> entry:entrySet){
                handlerRegister( entry.getValue());
            }
        }
        //开启异步线程，不停轮询redis的消息队列，获取待处理的事件
        Thread thread = new Thread(() -> {
            while (true){
                String eventQueueKey = RedisKeyUtil.getEventQueueKey();
                List<String> eventList= jedisAdapter.brpop(0 ,eventQueueKey);
                for(String event:eventList){
                    if(event.equals(eventQueueKey)){
                        continue;
                    }
                    EventModel eventModel = JSONObject.parseObject(event, EventModel.class);
                    if(!config.containsKey(eventModel.getType())){
                        logger.error("非法事件！");
                        continue;
                    }
                    List<EventHandler> handlerList = config.get(eventModel.getType());
                    //将事件分发给订阅这个事件的所有handler进行处理
                    for(EventHandler handler:handlerList){
                        executorService.submit(() -> handler.doHandle(eventModel));
                    }
                }
            }
        });
        thread.start();
    }

    //将处理器注册到config中，处理器与处理事件是多对多关系
    private void handlerRegister(EventHandler eventHandler){
        List<EventType> types = eventHandler.getSupportEventTypes();
        for(EventType type:types){
            List<EventHandler> handlers;
            if(!config.containsKey(type)){
                handlers = new ArrayList<>();
            }else {
                handlers = config.get(type);
            }
            handlers.add(eventHandler);
            config.put(type, handlers);
        }
    }



}
