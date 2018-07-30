package com.liugeng.zhihudemo.async;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.liugeng.zhihudemo.utils.JedisAdapter;
import com.liugeng.zhihudemo.utils.RedisKeyUtil;
import com.liugeng.zhihudemo.utils.ZhihuUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventProducer {
    @Autowired
    JedisAdapter jedisAdapter;

    private static final Logger logger = LoggerFactory.getLogger(EventProducer.class);

    public boolean fireEvent(EventModel event){
        try {
            String eventJSON = JSONObject.toJSONString(event);
            System.out.println(eventJSON);
            String key = RedisKeyUtil.getEventQueueKey();
            long listSize = jedisAdapter.lpush(key, eventJSON);
            if(listSize > 0){
                logger.info("发布事件成功：" + event.getType() + "，目前消息队列中暂未处理事件有：" + listSize + "条。");
            }else {
                logger.info("发布事件失败：" + event.getType());
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
