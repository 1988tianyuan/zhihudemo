package com.liugeng.zhihudemo.service;

import com.liugeng.zhihudemo.dao.QuestionDao;
import com.liugeng.zhihudemo.pojo.Comment;
import com.liugeng.zhihudemo.pojo.Question;
import com.liugeng.zhihudemo.pojo.ViewObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    QuestionDao questionDao;
    @Autowired
    UserService userService;
    @Autowired
    SensitiveWordsService sensitiveWordsService;
    @Autowired
    FollowService followService;

    public List<Question> getLatestQuestion(int uid, int offset, int limit){
        return questionDao.getLatestQuestion(uid, offset, limit);
    }

    public List<ViewObject> getQuestionsAndUsers(int uid, int offset, int limit, int localUid){
        List<Question> questionList = getLatestQuestion(uid, offset, limit);
        List<ViewObject> viewObjectList = new ArrayList<>();
        for(Question question:questionList){
            ViewObject viewObject = new ViewObject();
            viewObject.set("question", question);
            viewObject.set("followCount", followService.getFollowerCount(CommentService.ENTITY_QUESTION, question.getId()));
            viewObject.set("user", userService.getUser(question.getUserId()));
            viewObject.set("followed", followService.isFollower(CommentService.ENTITY_QUESTION, question.getId(), localUid));
            viewObjectList.add(viewObject);
        }
        return viewObjectList;
    }

    public int addQuestion(Question question, int userId){
        //对文本进行html标签过滤，将标签添加转义字符
        question.setTitle(HtmlUtils.htmlEscape(question.getTitle()));
        question.setContent(HtmlUtils.htmlEscape(question.getContent()));

        //敏感词过滤
        question.setContent(sensitiveWordsService.filter(question.getContent()));
        question.setTitle(sensitiveWordsService.filter(question.getTitle()));

        question.setCreateDate(new Date());
        question.setCommentCount(0);
        question.setUserId(userId);
        return questionDao.addQuestion(question) > 0 ? question.getId() : 0;
    }

    public int updateCommentCount(int qid, int newCommentCount){
        return questionDao.updateCommentCount(newCommentCount, qid);
    }

    public Question getQuestionById(int qid){
        return questionDao.getQuestionById(qid);
    }
}
