package com.liugeng.zhihudemo.controller;

import com.liugeng.zhihudemo.pojo.Feed;
import com.liugeng.zhihudemo.pojo.HostHolder;
import com.liugeng.zhihudemo.service.CommentService;
import com.liugeng.zhihudemo.service.FeedService;
import com.liugeng.zhihudemo.service.FollowService;
import com.liugeng.zhihudemo.utils.JedisAdapter;
import com.liugeng.zhihudemo.utils.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

@Controller
public class FeedController {
    @Autowired
    FeedService feedService;
    @Autowired
    HostHolder hostHolder;
    @Autowired
    FollowService followService;
    @Autowired
    JedisAdapter jedisAdapter;

    //新鲜事拉取模式，将当前用户关注的所有用户产生的所有新鲜事全部拉取到当前页面
    @RequestMapping(value = "/pullFeeds", method = {RequestMethod.GET})
    public String getPullFeeds(Model model){
        int localUid = hostHolder.getUser() == null ? 0 : hostHolder.getUser().getId();
        List<Integer> followeeUsers = new ArrayList<>();
        if(localUid != 0){
            followeeUsers = followService.getFollowees(CommentService.ENTITY_USER, localUid, 0, Integer.MAX_VALUE);
        }
        List<Feed> feedList = feedService.getFeedByUsers(Integer.MAX_VALUE, followeeUsers, 10);
        model.addAttribute("feeds", feedList);
        return "feeds";
    }

    //新鲜事推送模式，获取缓存在redis中的当前用户的新鲜事timeline，抓取到当前页面
    //后台采用异步消息队列的方式发布新鲜事事件
    @RequestMapping(value = "/pushFeeds", method = {RequestMethod.GET})
    public String getPushFeeds(Model model){
        int localUid = hostHolder.getUser() == null ? 0 : hostHolder.getUser().getId();
        List<String> feedIds = new ArrayList<>();
        if(localUid != 0){
            feedIds = jedisAdapter.lrange(RedisKeyUtil.getTimelineKey(localUid), 0, Integer.MAX_VALUE);
        }
        List<Feed> feeds = new ArrayList<>();
        for(String feed:feedIds){
            if(feed == null){
                continue;
            }
            Feed pushFeed = feedService.getFeedById(Integer.parseInt(feed));
            if(pushFeed == null)continue;
            feeds.add(pushFeed);
        }
        model.addAttribute("feeds", feeds);
        return "feeds";
    }

}
