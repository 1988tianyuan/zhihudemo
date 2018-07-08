package com.liugeng.zhihudemo.service;

import com.liugeng.zhihudemo.dao.CommentDao;
import com.liugeng.zhihudemo.pojo.Comment;
import com.liugeng.zhihudemo.pojo.Question;
import com.liugeng.zhihudemo.pojo.User;
import com.liugeng.zhihudemo.pojo.ViewObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import javax.swing.text.View;
import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {
    @Autowired
    CommentDao commentDao;
    @Autowired
    SensitiveWordsService sensitiveWordsService;
    @Autowired
    UserService userService;
    @Autowired
    QuestionService questionService;
    @Autowired
    LikeService likeService;

    public static final int ENTITY_QUESTION = 0;
    public static final int ENTITY_COMMENT = 1;
    public static final int ENTITY_USER = 2;

    public List<Comment> getCommentsByEntity(int entityId, int entityType, int offset, int limit){
        return commentDao.getCommentsByEntity(entityId, entityType, offset, limit);
    }

    public List<ViewObject> getCommentsAndUsers(int entityId, int entityType, int offset, int limit, User localUser){
        List<ViewObject> viewObjects = new ArrayList<>();
        List<Comment> comments = getCommentsByEntity(entityId, entityType, offset, limit);
        for(Comment c:comments){
            ViewObject viewObject = new ViewObject();
            viewObject.set("comment", c);
            viewObject.set("user", userService.getUser(c.getUserId()));
            viewObject.set("liked", likeService.likeStatus(localUser==null?0:localUser.getId(), c.getId(), ENTITY_COMMENT));
            long likeNum = likeService.likeNum(c.getId(), ENTITY_COMMENT);
            viewObject.set("likeNum", likeNum);
            viewObjects.add(viewObject);
        }
        return viewObjects;
    }

    public int addComment(Comment comment){
        //敏感词和html标签过滤
        comment.setContent(HtmlUtils.htmlEscape(comment.getContent()));
        comment.setContent(sensitiveWordsService.filter(comment.getContent()));
        //更新问题的回答数目
        if(comment.getEntityType()==0){
            int oldCommentCount = getCommentsCount(comment.getEntityId(), comment.getEntityType());
            int newCommentCount = ++oldCommentCount;
            questionService.updateCommentCount(comment.getEntityId(), newCommentCount);
        }
        return commentDao.addComment(comment)>0 ? comment.getId():0;
    }

    public int getUserCommentCount(int uid){
        return commentDao.getUserCommentCount(uid);
    }

    public int getCommentsCount(int entityId, int entityType){
        return commentDao.getCommentsCount(entityId, entityType);
    }

    public Comment getCommentById(int commentId){
        return commentDao.getCommentById(commentId);
    }

    public boolean deleteComment(int id, int status){
        return commentDao.updateStatus(id, status)>0;
    }

    public List<Integer> cidList(){
        return commentDao.getIdList();
    }

}
