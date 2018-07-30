package com.liugeng.zhihudemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
@EnableAutoConfiguration
public class ZhihudemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZhihudemoApplication.class, args);
	}

//	@Override
//	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
//		return builder.sources(ZhihudemoApplication.class);
//	}
}
