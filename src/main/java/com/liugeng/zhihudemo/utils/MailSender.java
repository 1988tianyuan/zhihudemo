package com.liugeng.zhihudemo.utils;

import com.liugeng.zhihudemo.controller.MessageController;
import freemarker.template.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Properties;

@Service
public class MailSender implements InitializingBean{
    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);
    private JavaMailSenderImpl mailSender;

    public boolean sendWithTemplate(String to, String title, String template, Map<String, Object> model){
        try {
            String nickName = MimeUtility.encodeText("Zhihu问答管理员");
            InternetAddress from = new InternetAddress(nickName + "<467651794@qq.com>");
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message);
            String result = FreeMarkerUtil.getProcessResult(model, template, "utf-8");
            messageHelper.setTo(to);
            messageHelper.setFrom(from);
            messageHelper.setSubject(title);
            messageHelper.setText(result, true);
            mailSender.send(message);
            return true;
        } catch (Exception e) {
            logger.error("邮件发送失败：" + e.getMessage());
        }
        return false;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        mailSender = new JavaMailSenderImpl();
        mailSender.setUsername("467651794@qq.com");
        mailSender.setPassword("ucvwpfpibxwcbhcc");
        mailSender.setHost("smtp.qq.com");
        mailSender.setPort(465);
        mailSender.setProtocol("smtps");
        mailSender.setDefaultEncoding("utf-8");
        Properties javaMailProperties = new Properties();
        javaMailProperties.put("mail.smtp.ssl.enable", true);
        mailSender.setJavaMailProperties(javaMailProperties);
    }
}
