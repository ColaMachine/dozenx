package com.dozenx;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
//import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
//import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAutoConfiguration
@SpringBootApplication
@EnableScheduling
//@EnableFeignClients
//@EnableEurekaClient
@MapperScan({"com.awifi.**.dao","com.dozenx.**.dao"})
@ComponentScan(basePackages = {"com.awifi.**","com.dozenx.**","com.dozenx.swagger.**"})
public class MSApplication extends SpringBootServletInitializer{
	public static void main(String[] args) {
	//	SpringApplication.run(MSApplication.class, args);
		SpringApplicationBuilder builder = new SpringApplicationBuilder(MSApplication.class);
		builder.headless(false).run(args);
	}
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder){
		return builder.sources(this.getClass());
	}
}
