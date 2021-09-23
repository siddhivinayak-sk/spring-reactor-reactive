package com.sk.springreactor.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.mongodb.client.result.UpdateResult;
import com.sk.springreactor.model.Person;
import com.sk.springreactor.repository.PersonRepositoryI;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class PersonRepositoryImpl implements PersonRepositoryI {

	@Autowired
	private ReactiveMongoTemplate template;

	@Override
	public Flux<Person> getAllPersonStartedWith(String str) {
		Query query = new Query(Criteria.where("name").regex(str));
		Flux<Person> retVal = template.find(query, Person.class);
		return retVal;
	}

	@Override
	public long updatePassword(String name, String password) {
		Query query = new Query(Criteria.where("name").is(name));
		Update update = new Update();
		update.set("password", password);
		Mono<UpdateResult> result = template.updateFirst(query, update, Person.class);
		return result.block().getMatchedCount();
	}
	
	
	
}
