package com.liugeng.zhihudemo.controller;

import com.liugeng.zhihudemo.pojo.Comment;
import com.liugeng.zhihudemo.pojo.HostHolder;
import com.liugeng.zhihudemo.service.CommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

@Controller
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    CommentService commentService;
    @Autowired
    HostHolder hostHolder;

    private static final Logger logger = LoggerFactory.getLogger(CommentController.class);

    @RequestMapping(path = "/addComment", method = {RequestMethod.POST})
    public String addComment(Comment comment){
        try {
            comment.setCreateDate(new Date());
            comment.setStatus(0);
            if(hostHolder.getUser() != null){
                comment.setUserId(hostHolder.getUser().getId());
            }else {
                return "redirect:/reglogin";
            }
            comment.setEntityType(CommentService.ENTITY_QUESTION);
            commentService.addComment(comment);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/question/"+comment.getEntityId();
    }
}
