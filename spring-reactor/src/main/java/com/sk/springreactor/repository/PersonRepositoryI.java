package com.sk.springreactor.repository;

import com.sk.springreactor.model.Person;

import reactor.core.publisher.Flux;

public interface PersonRepositoryI {
	
	Flux<Person> getAllPersonStartedWith(String str);
	
	long updatePassword(String name, String password);

}
