package com.liugeng.zhihudemo.pojo;

import org.springframework.stereotype.Component;

@Component
public class HostHolder {
    private static ThreadLocal<User> userThreadLocal = new ThreadLocal<>();

    public void setUser(User user){
        userThreadLocal.set(user);
    }

    public User getUser(){
        return userThreadLocal.get();
    }

    public void clear(){
        userThreadLocal.remove();
    }

}
