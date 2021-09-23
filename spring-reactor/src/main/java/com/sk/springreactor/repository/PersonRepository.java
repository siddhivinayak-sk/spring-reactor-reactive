package com.sk.springreactor.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.sk.springreactor.model.Person;

import reactor.core.publisher.Flux;

@Repository
public interface PersonRepository extends PersonRepositoryI, ReactiveMongoRepository<Person, Long> {

	Flux<Person> findByName(String name);
	
}
