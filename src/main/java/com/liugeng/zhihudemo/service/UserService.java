package com.liugeng.zhihudemo.service;

import com.liugeng.zhihudemo.async.EventModel;
import com.liugeng.zhihudemo.async.EventProducer;
import com.liugeng.zhihudemo.async.EventType;
import com.liugeng.zhihudemo.dao.LoginTicketDao;
import com.liugeng.zhihudemo.dao.UserDao;
import com.liugeng.zhihudemo.pojo.User;
import com.liugeng.zhihudemo.utils.ZhihuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    UserDao userDao;
    @Autowired
    LoginTicketService loginTicketService;
    @Autowired
    EventProducer eventProducer;

    public User getUser(int uid){
        return userDao.getUserById(uid);
    }

    public Map<String, String> register(String name, String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        Map<String,String> map = new HashMap<>();
        if(!StringUtils.hasText(name)){
            map.put("msg", "用户名不能为空！");
            return map;
        }
        if(!StringUtils.hasText(password)){
            map.put("msg", "密码不能为空！");
            return map;
        }
        User user = userDao.getUserByName(name);
        if(user != null){
            map.put("msg", "用户名已被占用！");
            return map;
        }

        user = new User();
        user.setName(name);
        user.setSalt(UUID.randomUUID().toString().substring(0,5));
        user.setPassword(ZhihuUtils.MD5(password+user.getSalt()));
        user.setHeadUrl("");
        userDao.addUser(user);

        String ticket = loginTicketService.loginTicket(user.getId());
        map.put("ticket", ticket);

        return map;
    }

    public Map<String, String> login(String name, String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        Map<String,String> map = new HashMap<>();
        User user = userDao.getUserByName(name);
        if(null == user){
            map.put("msg", "用户名不存在！");
            return map;
        }
        if(!user.getPassword().equals(ZhihuUtils.MD5(password + user.getSalt()))){
            map.put("msg", "密码不正确！");
            return map;
        }

        //如果当前用户登录状态有问题，则发送登录异常事件，由相应处理器进行处理
        //由于没有加入判断当前登录状态的功能，因此默认每次登录都触发该事件
        eventProducer.fireEvent(new EventModel(EventType.LOGIN)
                     .setExt("email", "467651794@qq.com")
                     .setExt("userName", user.getName()));

        String ticket = loginTicketService.loginTicket(user.getId());
        map.put("ticket", ticket);
        return map;
    }

    public void logout(String ticket){
        loginTicketService.logoutTicket(ticket);
    }

    public User getUserByName(String name){
        return userDao.getUserByName(name);
    }

}
