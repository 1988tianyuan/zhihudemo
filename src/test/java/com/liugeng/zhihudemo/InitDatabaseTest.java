package com.liugeng.zhihudemo;

import com.liugeng.zhihudemo.dao.QuestionDao;
import com.liugeng.zhihudemo.dao.UserDao;
import com.liugeng.zhihudemo.pojo.Feed;
import com.liugeng.zhihudemo.pojo.Question;
import com.liugeng.zhihudemo.pojo.User;
import com.liugeng.zhihudemo.pojo.ViewObject;
import com.liugeng.zhihudemo.service.CommentService;
import com.liugeng.zhihudemo.service.FeedService;
import com.liugeng.zhihudemo.service.FollowService;
import com.liugeng.zhihudemo.service.UserService;
import com.liugeng.zhihudemo.utils.JedisAdapter;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

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
    @Autowired
    FeedService feedService;
    @Autowired
    JedisAdapter jedisAdapter;

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
        for(int i = 0; i<10000; i++){
            jedisAdapter.sadd("testSet", String.valueOf(i));
            jedisAdapter.srem("testSet", String.valueOf(i));
            List<Integer> followeeUsers = followService.getFollowees(CommentService.ENTITY_USER, 11, 0, Integer.MAX_VALUE);
            boolean result = followService.isFollower(CommentService.ENTITY_QUESTION, 17, 11);
            System.out.println(followeeUsers.size());
            System.out.println(result);
        }
    }

    @Test
    public void initFeed(){
        int localUid = 11;
        List<Integer> followeeUsers = followService.getFollowees(CommentService.ENTITY_USER, localUid, 0, Integer.MAX_VALUE);
        List<Feed> feedList = feedService.getFeedByUsers(Integer.MAX_VALUE, followeeUsers, 10);
        for(Feed feed:feedList){
            System.out.println(feed.getData());
        }
    }

    @Test
    public void redisTest(){

    }

    @Test
    public void redisTest2(){
        Date date = new Date();
        for(int i = 0; i<100; i++){
            Jedis jedis1 = jedisAdapter.getJedis();
            Transaction tx1 = jedisAdapter.redisMulti(jedis1);
            tx1.zadd("testlpush1", date.getTime(), String.valueOf(i));
            List<Object> result1 = jedisAdapter.redisExec(tx1, jedis1);
            System.out.println(result1);

            Jedis jedis2 = jedisAdapter.getJedis();
            Transaction tx2 = jedisAdapter.redisMulti(jedis2);
            tx2.zadd("testlpush2", date.getTime(), String.valueOf(i));
            List<Object> result2 = jedisAdapter.redisExec(tx2, jedis2);
            System.out.println(result2);
        }
    }

    @Test
    public void redisTest3(){
        Set<String> set = jedisAdapter.zrange("testlpush1", 0 ,100);
        for(String s:set){
            System.out.println(s + ",");
        }
    }


}
