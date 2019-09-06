package com.dozenx.web.springboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAutoConfiguration
@SpringBootApplication
@EnableScheduling
@MapperScan({"com.awifi.**.dao","com.dozenx.**.dao"})
@ComponentScan(basePackages = {"com.awifi.**","com.dozenx.**","com.dozenx.swagger.**"})
public class MSApplication {
	public static void main(String[] args) {
		SpringApplication.run(MSApplication.class, args);
	}
}
