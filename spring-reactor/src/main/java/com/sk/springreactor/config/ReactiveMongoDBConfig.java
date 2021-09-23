package com.sk.springreactor.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;

@Configuration
@EnableReactiveMongoRepositories("com.sk.springreactor.repository")
public class ReactiveMongoDBConfig 
//extends AbstractReactiveMongoConfiguration 
{

	 //@Bean
	 public MongoClient mongoClient() {
		 return MongoClients.create();
	 }
	
	//@Override
	protected String getDatabaseName() {
		return "reactive";
	}
	
	//@Bean
    public ReactiveMongoTemplate reactiveMongoTemplate() {
        return new ReactiveMongoTemplate(mongoClient(), getDatabaseName());
    }
}
