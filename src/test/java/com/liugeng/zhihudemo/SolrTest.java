package com.liugeng.zhihudemo;

import com.liugeng.zhihudemo.service.SearchService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class SolrTest {
    @Autowired
    SearchService searchService;

    @Test
    public void test(){
        boolean result = searchService.newQuestionIndex(2201, "我是真的真的真的爱中华人民共和国", "我是真的真的真的爱中华人民共和国");
        Assert.assertTrue(result);
    }
}
