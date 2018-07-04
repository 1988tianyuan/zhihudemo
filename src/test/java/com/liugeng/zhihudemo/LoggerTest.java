package com.liugeng.zhihudemo;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class LoggerTest {
    private final Logger logger = LoggerFactory.getLogger(LoggerTest.class);

    @Test
    public void Test1(){
        int a = 1;
        int b = 1;
        int resul = a + b;
        Assert.assertEquals(2, resul);
    }

    @Test
    public void Test2(){
        int a = 2;
        int b = 2;
        int resul = a + b;
        Assert.assertEquals(4, resul);
    }

    @Test
    public void Test3(){
        int a = 3;
        int b = 3;
        int resul = a + b;
        Assert.assertEquals(6, resul);
    }
}
