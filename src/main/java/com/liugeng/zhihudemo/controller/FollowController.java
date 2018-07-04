package com.liugeng.zhihudemo.controller;

import com.liugeng.zhihudemo.async.EventModel;
import com.liugeng.zhihudemo.async.EventProducer;
import com.liugeng.zhihudemo.async.EventType;
import com.liugeng.zhihudemo.pojo.HostHolder;
import com.liugeng.zhihudemo.pojo.Question;
import com.liugeng.zhihudemo.pojo.User;
import com.liugeng.zhihudemo.pojo.ViewObject;
import com.liugeng.zhihudemo.service.*;
import com.liugeng.zhihudemo.utils.ZhihuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class FollowController {
    @Autowired
    FollowService followService;
    @Autowired
    UserService userService;
    @Autowired
    HostHolder hostHolder;
    @Autowired
    EventProducer eventProducer;
    @Autowired
    QuestionService questionService;
    @Autowired
    CommentService commentService;
    @Autowired
    LikeService likeService;

    @RequestMapping(path = "/followUser", method = {RequestMethod.POST})
    @ResponseBody
    public String followUser(@RequestParam("userId")int userId){
        User user;
        if((user = hostHolder.getUser())==null){
            return ZhihuUtils.getJSONString(999);
        }
        boolean ret = followService.follow(user.getId(), CommentService.ENTITY_USER, userId);
        boolean result = eventProducer.fireEvent(new EventModel(EventType.FOLLOW)
                                    .setActorId(user.getId())
                                    .setEntityId(userId)
                                    .setEntityType(CommentService.ENTITY_USER)
                                    .setEntityOwnerId(userId));
        Map<String, Object> info = new HashMap<>();
        info.put("headUrl", user.getHeadUrl());
        info.put("name", user.getName());
        info.put("id", user.getId());
        info.put("count", followService.getFollowerCount(CommentService.ENTITY_USER, userId));
        return ZhihuUtils.getJSONString(ret&&result ? 0:1, info);
    }

    @RequestMapping(path = "/unfollowUser", method = {RequestMethod.POST})
    @ResponseBody
    public String unFollowUser(@RequestParam("userId")int userId){
        User user;
        if((user = hostHolder.getUser())==null){
            return ZhihuUtils.getJSONString(999);
        }
        boolean ret = followService.unFollow(user.getId(), CommentService.ENTITY_USER, userId);
        Map<String, Object> info = new HashMap<>();
        info.put("headUrl", user.getHeadUrl());
        info.put("name", user.getName());
        info.put("id", user.getId());
        info.put("count", followService.getFollowerCount(CommentService.ENTITY_USER, userId));
        return ZhihuUtils.getJSONString(ret ? 0:1, info);
    }

    @RequestMapping(path = "/followQuestion", method = {RequestMethod.POST})
    @ResponseBody
    public String followQuestion(@RequestParam("questionId")int questionId){
        User user;
        if((user = hostHolder.getUser())==null){
            return ZhihuUtils.getJSONString(999);
        }
        Question q = questionService.getQuestionById(questionId);
        if(q == null){
            return ZhihuUtils.getJSONString(1, "问题不存在！");
        }
        boolean ret = followService.follow(user.getId(), CommentService.ENTITY_QUESTION, questionId);
        boolean result = eventProducer.fireEvent(new EventModel(EventType.FOLLOW)
                .setActorId(user.getId())
                .setEntityId(questionId)
                .setEntityType(CommentService.ENTITY_QUESTION)
                .setEntityOwnerId(q.getUserId()));
        Map<String, Object> info = new HashMap<>();
        info.put("headUrl", user.getHeadUrl());
        info.put("name", user.getName());
        info.put("id", user.getId());
        info.put("count", followService.getFollowerCount(CommentService.ENTITY_QUESTION, questionId));
        return ZhihuUtils.getJSONString(ret ? 0:1, info);
    }

    @RequestMapping(path = "/unfollowQuestion", method = {RequestMethod.POST})
    @ResponseBody
    public String unfollowQuestion(@RequestParam("questionId")int questionId){
        User user;
        if((user = hostHolder.getUser())==null){
            return ZhihuUtils.getJSONString(999);
        }
        boolean ret = followService.unFollow(user.getId(), CommentService.ENTITY_QUESTION, questionId);
        Map<String, Object> info = new HashMap<>();
        info.put("headUrl", user.getHeadUrl());
        info.put("name", user.getName());
        info.put("id", user.getId());
        info.put("count", followService.getFollowerCount(CommentService.ENTITY_QUESTION, questionId));
        return ZhihuUtils.getJSONString(ret ? 0:1, info);
    }

    @RequestMapping(path = "/user/{uid}/followees", method = {RequestMethod.GET})
    public String getFolloweeList(@PathVariable("uid")int uid, Model model){
        List<Integer> followeeList = followService.getFollowees(CommentService.ENTITY_USER, uid, 0, 10);
        List<ViewObject> viewObjects = getViewObjects(followeeList);
        model.addAttribute("followeeList", viewObjects);
        model.addAttribute("curUser", userService.getUser(uid));
        model.addAttribute("followeeCount", followService.getFolloweeCount(uid, CommentService.ENTITY_USER));
        return "followees";
    }

    @RequestMapping(path = "/user/{uid}/followers", method = {RequestMethod.GET})
    public String getFollowerList(@PathVariable("uid")int uid, Model model){
        List<Integer> followerList = followService.getFollowers(CommentService.ENTITY_USER, uid, 0, 10);
        List<ViewObject> viewObjects = getViewObjects(followerList);
        model.addAttribute("followerList", viewObjects);
        model.addAttribute("curUser", userService.getUser(uid));
        model.addAttribute("followerCount", followService.getFollowerCount(CommentService.ENTITY_USER, uid));
        return "followers";
    }

    private List<ViewObject> getViewObjects(List<Integer> list){
        List<ViewObject> viewObjects = new ArrayList<>();
        int localUid = hostHolder.getUser().getId();
        for(Integer i:list){
            User user = userService.getUser(i);
            if(user == null){
                continue;
            }
            ViewObject viewObject = new ViewObject();
            viewObject.set("followeeCount", followService.getFolloweeCount(i, CommentService.ENTITY_USER));
            viewObject.set("followerCount", followService.getFollowerCount(CommentService.ENTITY_USER, i));
            viewObject.set("commentCount", commentService.getUserCommentCount(i));
            viewObject.set("likeCount", likeService.likeCountByUser(i, CommentService.ENTITY_COMMENT));
            viewObject.set("followed", followService.isFollower(CommentService.ENTITY_USER, i, localUid));
            viewObject.set("user", userService.getUser(i));
            viewObjects.add(viewObject);
        }
        return viewObjects;
    }
}
