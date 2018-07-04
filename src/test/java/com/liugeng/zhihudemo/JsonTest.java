package com.liugeng.zhihudemo;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.liugeng.zhihudemo.async.EventModel;
import com.liugeng.zhihudemo.async.EventType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class JsonTest {

    @Test
    public void jsonTest(){
        EventModel eventModel = new EventModel();
        eventModel.setExt("ext", "ext");
        System.out.println(JSONObject.toJSONString(eventModel,SerializerFeature.WRITE_MAP_NULL_FEATURES));
    }

}
