package com.liugeng.zhihudemo.async;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.liugeng.zhihudemo.utils.JedisAdapter;
import com.liugeng.zhihudemo.utils.RedisKeyUtil;
import com.liugeng.zhihudemo.utils.ZhihuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventProducer {
    @Autowired
    JedisAdapter jedisAdapter;

    public boolean fireEvent(EventModel event){
        try {
            String eventJSON = JSONObject.toJSONString(event);
            System.out.println(eventJSON);
            String key = RedisKeyUtil.getEventQueueKey();
            jedisAdapter.lpush(key, eventJSON);
            System.out.println("救命啊！我不想卡在这里！！！");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
