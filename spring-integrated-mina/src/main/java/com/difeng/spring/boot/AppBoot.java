package com.difeng.spring.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.PropertyPlaceholderAutoConfiguration;
import org.springframework.boot.autoconfigure.web.DispatcherServletAutoConfiguration;
import org.springframework.boot.autoconfigure.web.EmbeddedServletContainerAutoConfiguration;
import org.springframework.boot.autoconfigure.web.ErrorMvcAutoConfiguration;
import org.springframework.boot.autoconfigure.web.HttpEncodingAutoConfiguration;
import org.springframework.boot.autoconfigure.web.HttpMessageConvertersAutoConfiguration;
import org.springframework.boot.autoconfigure.web.MultipartAutoConfiguration;
import org.springframework.boot.autoconfigure.web.ServerPropertiesAutoConfiguration;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

/**
 * @Description:TODO
 * @author:duyongfeng
 * @time:2016年6月12日 下午12:50:43
 */
@Configuration
@Import({
	DispatcherServletAutoConfiguration.class,
	EmbeddedServletContainerAutoConfiguration.class,
	ErrorMvcAutoConfiguration.class,
	HttpEncodingAutoConfiguration.class,
	HttpMessageConvertersAutoConfiguration.class,
	MultipartAutoConfiguration.class,
	ServerPropertiesAutoConfiguration.class,
	PropertyPlaceholderAutoConfiguration.class,
	WebMvcAutoConfiguration.class
})
//@ComponentScan(value="cn.com.acsno.nqs.webapi")
@ImportResource("/applicationContext-mina.xml")
public class AppBoot {
    public static void main(String[] args) {
		SpringApplication.run(AppBoot.class, args);
	}
}

