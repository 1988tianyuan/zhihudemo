package com.liugeng.zhihudemo.utils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

public class FreeMarkerUtil {

    private static final Logger logger = LoggerFactory.getLogger(FreeMarkerUtil.class);

    public static String getProcessResult(Map<String, Object> root, String tempName, String encode){
        try {
            Configuration freeMarkerConfig = new Configuration(Configuration.VERSION_2_3_28);
            freeMarkerConfig.setClassForTemplateLoading(FreeMarkerUtil.class, "/templates/mail");
            freeMarkerConfig.setDefaultEncoding(encode);
            freeMarkerConfig.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
            Template template = freeMarkerConfig.getTemplate(tempName, "utf-8");
            StringWriter writer = new StringWriter();
            template.process(root, writer);
            return writer.toString();
        } catch (Exception e) {
            logger.error("输出FreeMarker渲染后文本失败：" + e.getMessage());
        }
        return null;
    }

}
