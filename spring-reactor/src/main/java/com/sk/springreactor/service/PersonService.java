package com.sk.springreactor.service;

import com.sk.springreactor.model.Person;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PersonService {
	
	Mono<Person> get(long id);
	
	Flux<Person> getAll();
	
	Flux<Person> findByName(String name);
	
	Mono<Person> create(Mono<Person> person);
	
	Mono<Person> update(long id, Mono<Person> person);
	
	Mono<Void> delete(long id);
	
	Mono<Long> count();

}
