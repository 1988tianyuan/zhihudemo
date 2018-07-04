package com.liugeng.zhihudemo;

import com.liugeng.zhihudemo.pojo.User;
import com.liugeng.zhihudemo.utils.FreeMarkerUtil;
import com.liugeng.zhihudemo.utils.MailSender;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class PageTemplateTest {
    @Autowired
    MailSender mailSender;

    @Test
    public void test(){
        try {
            Configuration freeMarkerConfig = new Configuration(Configuration.VERSION_2_3_28);
            freeMarkerConfig.setClassForTemplateLoading(PageTemplateTest.class, "/templates/mail");
            freeMarkerConfig.setDefaultEncoding("utf-8");
            freeMarkerConfig.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
            Template template = freeMarkerConfig.getTemplate("mailTemplate.ftl", "utf-8");
            Map<String, String> map = new HashMap<>();
            map.put("name", "刘耕");
            StringWriter writer = new StringWriter();
            template.process(map, writer);
            System.out.println(writer.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test2(){
        Map<String, Object> map = new HashMap<>();
        User user = new User();
        user.setName("刘耕");
        user.setId(10);
        map.put("user", user);
        String result = FreeMarkerUtil.getProcessResult(map, "mailTemplate.ftl", "utf-8");
        System.out.println(result);
    }

    @Test
    public void sendMailTest(){
        String title = "哈哈哈哈哈！";
        String to = "467651794@qq.com";
        String template = "mailTemplate.ftl";
        Map<String, Object> map = new HashMap<>();
        User user = new User();
        user.setName("刘耕");
        user.setId(10);
        map.put("user", user);
        boolean hasSend = mailSender.sendWithTemplate(to, title, template, map);
        Assert.assertTrue(hasSend);
    }

}
