package com.liugeng.zhihudemo.controller;

import com.liugeng.zhihudemo.async.EventModel;
import com.liugeng.zhihudemo.async.EventProducer;
import com.liugeng.zhihudemo.async.EventType;
import com.liugeng.zhihudemo.pojo.Comment;
import com.liugeng.zhihudemo.pojo.HostHolder;
import com.liugeng.zhihudemo.pojo.User;
import com.liugeng.zhihudemo.service.CommentService;
import com.liugeng.zhihudemo.service.LikeService;
import com.liugeng.zhihudemo.service.QuestionService;
import com.liugeng.zhihudemo.utils.ZhihuUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LikeController {
    @Autowired
    LikeService likeService;
    @Autowired
    HostHolder hostHolder;
    @Autowired
    EventProducer eventProducer;
    @Autowired
    CommentService commentService;
    @Autowired
    QuestionService questionService;

    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);

    @RequestMapping(path = "/like", method = RequestMethod.POST)
    @ResponseBody
    public String like(@RequestParam("commentId")int commentId){
        User user;
        if(null == (user=hostHolder.getUser())){
            return ZhihuUtils.getJSONString(999);
        }
        long likeNum = likeService.like(user.getId(), commentId, CommentService.ENTITY_COMMENT);

        //创建点赞事件，由producer将事件发送到消息队列，由后续订阅了这个事件的消息处理器进行处理
        EventModel eventModel = new EventModel(EventType.LIKE);
        Comment comment = commentService.getCommentById(commentId);
        eventModel.setActorId(user.getId())
                  .setEntityId(commentId)
                  .setEntityType(CommentService.ENTITY_COMMENT)
                  .setEntityOwnerId(comment.getUserId())
                  .setExt("comment", comment.getContent())
                  .setExt("question", questionService.getQuestionById(comment.getEntityId()).getTitle());
        eventProducer.fireEvent(eventModel);

        return ZhihuUtils.getJSONString(0, String.valueOf(likeNum));
    }

    @RequestMapping(path = "/dislike", method = RequestMethod.POST)
    @ResponseBody
    public String dislike(@RequestParam("commentId")int commentId){
        User user;
        if(null == (user=hostHolder.getUser())){
            return ZhihuUtils.getJSONString(999);
        }
        likeService.dislike(user.getId(), commentId, CommentService.ENTITY_COMMENT);
        long likeNum = likeService.likeNum(commentId, CommentService.ENTITY_COMMENT);
        return ZhihuUtils.getJSONString(0, String.valueOf(likeNum));
    }

}
