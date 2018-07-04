package com.liugeng.zhihudemo.controller;


import com.liugeng.zhihudemo.dao.QuestionDao;
import com.liugeng.zhihudemo.pojo.HostHolder;
import com.liugeng.zhihudemo.pojo.Question;
import com.liugeng.zhihudemo.pojo.User;
import com.liugeng.zhihudemo.pojo.ViewObject;
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
            List<ViewObject> viewObjectList = questionService.getQuestionsAndUsers(uid,0,5, hostHolder.getUser().getId());

            User user = userService.getUser(uid);
            ViewObject vo = new ViewObject();
            vo.set("user", user);
            vo.set("followerCount", followService.getFollowerCount(CommentService.ENTITY_USER, uid));
            vo.set("followeeCount", followService.getFolloweeCount(uid, CommentService.ENTITY_USER));
            vo.set("commentCount", commentService.getUserCommentCount(uid));
            vo.set("followed", followService.isFollower(CommentService.ENTITY_USER, uid, hostHolder.getUser().getId()));
            vo.set("likeCount", likeService.likeCountByUser(uid, CommentService.ENTITY_COMMENT));

            model.addAttribute("vos", viewObjectList);
            model.addAttribute("profileUser", vo);
            return "userPage";
        } catch (Exception e) {
            logger.error("打开特定用户问题页出错："+e.getMessage());
        }
        return null;
    }
}
