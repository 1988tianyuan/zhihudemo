package com.liugeng.zhihudemo;

import com.liugeng.zhihudemo.service.CommentService;
import com.liugeng.zhihudemo.service.LikeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class RedisTest {
    @Autowired
    LikeService likeService;
    @Autowired
    CommentService commentService;

    @Test
    public void userLikeKeyTest(){
        List<Integer> commentList = commentService.cidList();
        for(int i = 1; i<=13; i++){
//            for(Integer cid:commentList){
//                if(1 == likeService.likeStatus(i, cid, CommentService.ENTITY_COMMENT)){
//                    likeService.userLikeSet(i, cid, CommentService.ENTITY_COMMENT);
//                }
//            }
            System.out.println("用户"+i+"的总点赞数是："+likeService.likeCountByUser(i));
        }

    }
}
