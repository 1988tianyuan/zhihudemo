package com.liugeng.zhihudemo.controller;

import com.liugeng.zhihudemo.pojo.HostHolder;
import com.liugeng.zhihudemo.pojo.Question;
import com.liugeng.zhihudemo.pojo.User;
import com.liugeng.zhihudemo.pojo.ViewObject;
import com.liugeng.zhihudemo.service.*;
import org.apache.solr.client.solrj.SolrServerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.HtmlUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class SearchController {
    @Autowired
    SearchService searchService;
    @Autowired
    UserService userService;
    @Autowired
    QuestionService questionService;
    @Autowired
    FollowService followService;
    @Autowired
    HostHolder hostHolder;

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String searchQuestion(@RequestParam("keywords")String keywords, Model model){
        try {
            List<Question> questionList = searchService.searchQuestion(keywords, 0 ,10, "<span style='color:red'>", "</span>");
            List<ViewObject> questions = new ArrayList<>();
            int localUid = 0;
            User localUser = hostHolder.getUser();
            if(localUser != null){
                localUid = localUser.getId();
            }
            for(Question question:questionList){
                ViewObject viewObject = new ViewObject();
                Question detailQuestion = questionService.getQuestionById(question.getId());
                User user = userService.getUser(detailQuestion.getUserId());
                if(question.getTitle() != null){
                    detailQuestion.setTitle(question.getTitle());
                }
                if(question.getContent() != null){
                    detailQuestion.setContent(question.getContent());
                }
                viewObject.set("question", detailQuestion);
                viewObject.set("user", user);
                viewObject.set("followCount", followService.getFollowerCount(CommentService.ENTITY_QUESTION, question.getId()));
                viewObject.set("followed", followService.isFollower(CommentService.ENTITY_QUESTION, question.getId(), localUid));
                questions.add(viewObject);
            }
            model.addAttribute("questions", questions);
            return "result";
        } catch (Exception e) {
            logger.error("搜索问题时出错："+e.getMessage());
            e.printStackTrace();
        }
        return null;
    }


}
