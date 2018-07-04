package com.liugeng.zhihudemo;

import com.liugeng.zhihudemo.utils.ApplicationContextProvider;
import com.liugeng.zhihudemo.utils.ZhihuUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StringUtils;
import org.springframework.web.util.HtmlUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ZhihudemoApplicationTests {
	@Autowired
	ApplicationContextProvider applicationContextProvider;

	@Test
	public void contextLoads() {
		ApplicationContext applicationContext = applicationContextProvider.getApplicationContext();
		String[] controllers = applicationContext.getBeanNamesForAnnotation(Controller.class);
		System.out.println("加载的Controller一共有："+controllers.length);
		for(int i = 0; i<controllers.length; i++){
			System.out.print(controllers[i]+",");
		}
	}

}
