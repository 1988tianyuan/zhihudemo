package com.liugeng.zhihudemo.controller;

import com.liugeng.zhihudemo.pojo.HostHolder;
import com.liugeng.zhihudemo.pojo.Message;
import com.liugeng.zhihudemo.pojo.User;
import com.liugeng.zhihudemo.pojo.ViewObject;
import com.liugeng.zhihudemo.service.MessageService;
import com.liugeng.zhihudemo.service.UserService;
import com.liugeng.zhihudemo.utils.ZhihuUtils;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/msg")
public class MessageController {
    @Autowired
    MessageService messageService;
    @Autowired
    HostHolder hostHolder;
    @Autowired
    UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);

    @RequestMapping(path = {"/addMessage"}, method = {RequestMethod.POST})
    @ResponseBody
    public String addMessage(@RequestParam("toName") String toName, @RequestParam("content") String content){
        try{
            User fromUser;
            User toUser;
            if(null == (fromUser = hostHolder.getUser())){
                return ZhihuUtils.getJSONString(999, "未登录");
            }
            if(null == (toUser = userService.getUserByName(toName))){
                return ZhihuUtils.getJSONString(1, "目标用户不存在");
            }
            int toId = toUser.getId();
            int fromId = fromUser.getId();
            messageService.addMessage(fromId, toId, content);
            return ZhihuUtils.getJSONString(0);
        }catch (Exception e){
            logger.error("发送站内信失败："+e.getMessage());
            return ZhihuUtils.getJSONString(1, "发送失败");
        }
    }

    @RequestMapping("/list")
    public String getConversationList(Model model){
        try{
            User user;
            if(null==(user=hostHolder.getUser())){
                return "redirect:/reglogin?next=/msg/list";
            }
            int localId = user.getId();
            List<ViewObject> conversations = messageService.getConversationList(localId);
            model.addAttribute("conversations", conversations);
        }catch (Exception e){
            logger.error("获取站内信列表出错："+e.getMessage());
        }
        return "letter";
    }

    @RequestMapping("/detail")
    public String conversationDetail(Model model, @Param("conversationId")String conversationId){
        try{
            if(null==(hostHolder.getUser())){
                return "redirect:/reglogin?next=/msg/detail?conversationId="+conversationId;
            }
            messageService.updateUnread(conversationId);
            List<ViewObject> messages = messageService.getConversationDetail(conversationId, 0 , 10);
            model.addAttribute("messages", messages);
        }catch (Exception e){
            logger.error("获取站内信详情失败："+e.getMessage());
        }
        return "letterDetail";
    }

    @RequestMapping("/delete")
    public String deleteConversation(Model model, @Param("conversationId")String conversationId){
        try{
            User user;
            if(null==(user=hostHolder.getUser())){
                return "redirect:/reglogin?next=/msg/list";
            }
            int localId = user.getId();
            messageService.deleteConversation(conversationId, localId);
        }catch (Exception e){
            logger.error("删除对话失败："+e.getMessage());
        }
        return "redirect:/msg/list";
    }
}
