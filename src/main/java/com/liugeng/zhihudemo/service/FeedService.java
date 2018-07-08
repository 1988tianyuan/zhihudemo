package com.liugeng.zhihudemo.service;

import com.liugeng.zhihudemo.dao.FeedDao;
import com.liugeng.zhihudemo.pojo.Feed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedService {
    @Autowired
    FeedDao feedDao;

    public List<Feed> getFeedByUsers(int maxId, List<Integer> users, int count){
        return feedDao.getFeedByUsers(users, maxId, count);
    }

    public boolean addFeed(Feed feed){
        return feedDao.addFeed(feed) > 0;
    }

    public Feed getFeedById(int fid){
        return feedDao.getFeedById(fid);
    }
}
