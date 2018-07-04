package com.liugeng.zhihudemo.interceptor;


import com.liugeng.zhihudemo.dao.LoginTicketDao;
import com.liugeng.zhihudemo.dao.UserDao;
import com.liugeng.zhihudemo.pojo.HostHolder;
import com.liugeng.zhihudemo.pojo.LoginTicket;
import com.liugeng.zhihudemo.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;

@Component
public class LoginCheckInterceptor extends HandlerInterceptorAdapter{
    @Autowired
    LoginTicketDao loginTicketDao;
    @Autowired
    UserDao userDao;
    @Autowired
    HostHolder hostHolder;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String ticket;
        if(null != request.getCookies()){
            for(Cookie cookie:request.getCookies()){
                if(cookie.getName().equals("ticket")){
                    ticket = cookie.getValue();
                    LoginTicket loginTicket = loginTicketDao.getTicket(ticket);
                    if(loginTicket == null || loginTicket.getExpired().before(new Date()) || loginTicket.getStatus() != 0){
                        return true;
                    }
                    User user = userDao.getUserById(loginTicket.getUserId());
                    hostHolder.setUser(user);
                    return true;
                }
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if(modelAndView != null && hostHolder.getUser() != null){
            modelAndView.addObject("user", hostHolder.getUser());
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        hostHolder.clear();
    }
}
