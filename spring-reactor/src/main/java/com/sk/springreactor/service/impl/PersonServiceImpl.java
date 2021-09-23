package com.sk.springreactor.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sk.springreactor.model.Person;
import com.sk.springreactor.repository.PersonRepository;
import com.sk.springreactor.service.PersonService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PersonServiceImpl implements PersonService {

	@Autowired
	private PersonRepository repository;
	
	@Override
	public Mono<Person> get(long id) {
		return repository.findById(id);
	}

	@Override
	public Flux<Person> getAll() {
		return repository.findAll();
	}

	@Override
	public Flux<Person> findByName(String name) {
		return repository.findByName(name);
	}

	@Override
	public Mono<Person> create(Mono<Person> person) {
		return person.flatMap(repository::save);
	}

	@Override
	public Mono<Person> update(long id, Mono<Person> mPerson) {
		return repository
				.findById(id)
				.map(p -> {
					Person p1 = mPerson.block();
					p.setName(p1.getName());
					p.setPassword(p1.getPassword());
					return p;
				})
				.flatMap(repository::save);
	}

	@Override
	public Mono<Void> delete(long id) {
		return repository.deleteById(id);
	}

	@Override
	public Mono<Long> count() {
		return repository.count();
	}

}
