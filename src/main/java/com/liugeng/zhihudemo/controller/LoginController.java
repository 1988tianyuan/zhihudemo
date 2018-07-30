package com.liugeng.zhihudemo.controller;

import com.liugeng.zhihudemo.async.EventModel;
import com.liugeng.zhihudemo.async.EventProducer;
import com.liugeng.zhihudemo.async.EventType;
import com.liugeng.zhihudemo.pojo.HostHolder;
import com.liugeng.zhihudemo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class LoginController {
    @Autowired
    UserService userService;
    @Autowired
    HostHolder hostHolder;

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @RequestMapping(path = "reg", method = {RequestMethod.POST})
    public String register(HttpServletResponse response, Model model, @RequestParam("username")String userName, @RequestParam("password")String password){
        try{
            Map<String, String> map = userService.register(userName, password);
            if(map.containsKey("ticket")){
                Cookie cookie = new Cookie("ticket", map.get("ticket"));
                cookie.setPath("/");
                response.addCookie(cookie);
            }else {
                //没有ticket说明注册失败，则向前台发送msg信息
                model.addAttribute("msg", map.get("msg"));
                return "login";
            }
            return "redirect:";
        }catch (Exception e){
            logger.error("注册过程出现异常：" + e.getMessage());
            e.printStackTrace();
            return "login";
        }
    }

    @RequestMapping(path = "login", method = {RequestMethod.POST})
    public String login(Model model, HttpServletResponse response,
                        @RequestParam("username")String userName, @RequestParam("password")String password,
                        @RequestParam(value = "next", required = false)String next,
                        @RequestParam(value = "rememberme", defaultValue = "false")boolean rememberMe){
        try{
            Map<String, String> map = userService.login(userName, password);
            if(!map.containsKey("ticket")){
                //没有ticket说明登录失败，则向前台发送msg信息
                model.addAttribute("msg", map.get("msg"));
                return "login";
            }else {
                Cookie cookie = new Cookie("ticket", map.get("ticket"));
                cookie.setPath("/");
                if(rememberMe){
                    cookie.setMaxAge(3600*24*5);
                }
                response.addCookie(cookie);
            }

            if(StringUtils.hasText(next)){
                return "redirect:" + next;
            }
            return "redirect:";
        }catch (Exception e){
            logger.error("登录过程出现异常：" + e.getMessage());
            e.printStackTrace();
            return "login";
        }
    }

    @RequestMapping("logout")
    public String logout(@CookieValue("ticket")String ticket){
        userService.logout(ticket);
        return "redirect:";
    }



    //跳转到登录注册页面
    @RequestMapping("reglogin")
    public String regLogin(@RequestParam(value = "next", required = false)String next, Model model){
        model.addAttribute("next", next);
        return "login";
    }
}
