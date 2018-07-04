package com.liugeng.zhihudemo.controller;

import com.liugeng.zhihudemo.pojo.*;
import com.liugeng.zhihudemo.service.CommentService;
import com.liugeng.zhihudemo.service.FollowService;
import com.liugeng.zhihudemo.service.QuestionService;
import com.liugeng.zhihudemo.service.UserService;
import com.liugeng.zhihudemo.utils.ZhihuUtils;
import org.apache.catalina.Host;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/question")
public class QuestionController {
    @Autowired
    QuestionService questionService;
    @Autowired
    HostHolder hostHolder;
    @Autowired
    CommentService commentService;
    @Autowired
    FollowService followService;
    @Autowired
    UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @RequestMapping(value = "/add", method = {RequestMethod.POST})
    @ResponseBody
    public String addQuestion(Question question){
        try{
            if(null == hostHolder.getUser()){
                return ZhihuUtils.getJSONString(999);
            }
            if(questionService.addQuestion(question, hostHolder.getUser().getId())>0){
                return ZhihuUtils.getJSONString(0);
            }
        }catch (Exception e){
            logger.error("添加问题失败：" + e.getMessage());
        }
        return ZhihuUtils.getJSONString(1, "添加问题失败！");
    }

    @RequestMapping("/{qid}")
    public String questionDetail(@PathVariable("qid")int qid, Model model){
        try {
            Question question = questionService.getQuestionById(qid);
            List<ViewObject> viewObjects = commentService.getCommentsAndUsers(qid, 0, 0, 0, hostHolder.getUser());
            User user = hostHolder.getUser();
            boolean followed;
            if(user == null){
                followed = false;
            }else {
                followed = followService.isFollower(CommentService.ENTITY_QUESTION, qid, user.getId());
            }
            List<User> followers = new ArrayList<>();
            for(Integer uid:followService.getFollowers(CommentService.ENTITY_QUESTION, qid, 0, 10)){
                User follower = userService.getUser(uid);
                followers.add(follower);
            }

            model.addAttribute("followers", followers);
            model.addAttribute("followed", followed);
            model.addAttribute("question", question);
            model.addAttribute("comments", viewObjects);
            model.addAttribute("followerCount", followService.getFollowerCount(CommentService.ENTITY_QUESTION, qid));
        } catch (Exception e) {
            logger.error("加载问题详情页失败："+e.getMessage());
        }
        return "detail";
    }
}
