package com.liugeng.zhihudemo.controller;

import com.liugeng.zhihudemo.async.EventModel;
import com.liugeng.zhihudemo.async.EventProducer;
import com.liugeng.zhihudemo.async.EventType;
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
    @Autowired
    EventProducer eventProducer;

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @RequestMapping(value = "/question/add", method = {RequestMethod.POST})
    @ResponseBody
    public String addQuestion(Question question){
        try{
            if(null == hostHolder.getUser()){
                return ZhihuUtils.getJSONString(999);
            }
            if(questionService.addQuestion(question, hostHolder.getUser().getId())>0){
                //添加问题成功后，发布添加问题的事件，用于添加到solr库等
                eventProducer.fireEvent(new EventModel(EventType.ADD_QUESTION)
                        .setEntityId(question.getId())
                        .setExt("question_title", question.getTitle())
                        .setExt("question_content", question.getContent()));

                return ZhihuUtils.getJSONString(0);
            }
        }catch (Exception e){
            logger.error("添加问题失败：" + e.getMessage());
        }
        return ZhihuUtils.getJSONString(1, "添加问题失败！");
    }

    @RequestMapping("/question/{qid}")
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
