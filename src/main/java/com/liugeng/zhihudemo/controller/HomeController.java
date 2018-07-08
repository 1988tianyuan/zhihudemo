package com.liugeng.zhihudemo.controller;


import com.liugeng.zhihudemo.dao.QuestionDao;
import com.liugeng.zhihudemo.pojo.*;
import com.liugeng.zhihudemo.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Controller
public class HomeController {
    @Autowired
    QuestionService questionService;
    @Autowired
    UserService userService;
    @Autowired
    HostHolder hostHolder;
    @Autowired
    FollowService followService;
    @Autowired
    CommentService commentService;
    @Autowired
    LikeService likeService;
    @Autowired
    FeedService feedService;

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @RequestMapping(value = {"/index","/"},method = RequestMethod.GET)
    public String index(Model model){
        try {
            User localUser = hostHolder.getUser();
            int localUid = 0;
            if(localUser != null){
                localUid = localUser.getId();
            }
            model.addAttribute("vos", questionService.getQuestionsAndUsers(0,0,5, localUid));
            return "index";
        } catch (Exception e) {
            logger.error("打开问答主页出错："+e.getMessage());
        }
        return null;
    }

    @RequestMapping("/user/{uid}")
    public String userPage(@PathVariable("uid")int uid, Model model){
        try {
            User localUser = hostHolder.getUser();
            int localUid = 0;
            if(localUser != null){
                localUid = localUser.getId();
            }
            //获取该用户提的所有问题
            List<ViewObject> viewObjectList = questionService.getQuestionsAndUsers(uid,0,5, localUid);

            //获取该用户的关注、回答数和点赞数等信息
            User user = userService.getUser(uid);
            ViewObject vo = new ViewObject();
            vo.set("user", user);
            vo.set("followerCount", followService.getFollowerCount(CommentService.ENTITY_USER, uid));
            vo.set("followeeCount", followService.getFolloweeCount(uid, CommentService.ENTITY_USER));
            vo.set("commentCount", commentService.getUserCommentCount(uid));
            vo.set("followed", followService.isFollower(CommentService.ENTITY_USER, uid, localUid));
            vo.set("likeCount", likeService.likeCountByUser(uid, CommentService.ENTITY_COMMENT));

            //提取共同关注对象
            List<Integer> sharedFolloweeIds = followService.getSharedFollowees(localUid, uid, CommentService.ENTITY_USER);
            List<User> shareFollowees = new ArrayList<>();
            for(Integer i:sharedFolloweeIds){
                User sharedFollowee = userService.getUser(i);
                shareFollowees.add(sharedFollowee);
            }

            //拉取这个用户的所有新鲜事
            List<Feed> feeds = feedService.getFeedByUsers(Integer.MAX_VALUE, Arrays.asList(uid), 10);

            model.addAttribute("vos", viewObjectList);
            model.addAttribute("profileUser", vo);
            model.addAttribute("shareFollowees", shareFollowees);
            model.addAttribute("feeds", feeds);
            return "userPage";
        } catch (Exception e) {
            logger.error("打开特定用户问题页出错："+e.getMessage());
        }
        return null;
    }
}
