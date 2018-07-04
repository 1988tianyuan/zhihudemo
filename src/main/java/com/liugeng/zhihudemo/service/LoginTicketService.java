package com.liugeng.zhihudemo.service;

import com.liugeng.zhihudemo.dao.LoginTicketDao;
import com.liugeng.zhihudemo.pojo.LoginTicket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class LoginTicketService {
    @Autowired
    LoginTicketDao loginTicketDao;

    public String loginTicket(int uid){
        LoginTicket loginTicket = new LoginTicket();
        Date expiredTime = new Date();
        expiredTime.setTime(1000*3600*24*3000 + expiredTime.getTime());

        loginTicket.setUserId(uid);
        loginTicket.setStatus(0);
        loginTicket.setTicket(UUID.randomUUID().toString().replaceAll("-",""));
        loginTicket.setExpired(expiredTime);

        loginTicketDao.addTicket(loginTicket);
        return loginTicket.getTicket();
    }

    public void logoutTicket(String ticket){
        loginTicketDao.updateTicket(ticket, 1);
    }

}
