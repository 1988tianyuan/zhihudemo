package com.liugeng.zhihudemo;

import com.liugeng.zhihudemo.dao.QuestionDao;
import com.liugeng.zhihudemo.dao.UserDao;
import com.liugeng.zhihudemo.pojo.Question;
import com.liugeng.zhihudemo.pojo.User;
import com.liugeng.zhihudemo.pojo.ViewObject;
import com.liugeng.zhihudemo.service.CommentService;
import com.liugeng.zhihudemo.service.FollowService;
import com.liugeng.zhihudemo.service.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class InitDatabaseTest {
    @Autowired
    UserDao userDao;
    @Autowired
    QuestionDao questionDao;
    @Autowired
    FollowService followService;
    @Autowired
    UserService userService;
    @Autowired
    CommentService commentService;

    @Test
    public void initDatabase(){
        for(int i = 2; i<11; i++){
            User user = new User();
            user.setName("刘耕"+i);
            user.setPassword("111111"+i);
            user.setHeadUrl("");
            user.setSalt("");
            userDao.addUser(user);
        }
    }

    @Test
    public void initFollow(){
        boolean ret = followService.unFollow(11, CommentService.ENTITY_USER, 15);
        Assert.assertTrue(ret);
    }



}
