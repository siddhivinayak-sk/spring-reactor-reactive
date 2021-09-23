package com.sk.springreactor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.sk.springreactor.*")
public class SpringReactorApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringReactorApplication.class, args);
	}

}

//https://dzone.com/articles/open-id-connect-authentication-with-oauth20-author
//https://stackoverflow.com/questions/1087031/whats-the-difference-between-openid-and-oauth


