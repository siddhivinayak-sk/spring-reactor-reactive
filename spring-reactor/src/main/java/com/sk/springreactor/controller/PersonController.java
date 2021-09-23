package com.sk.springreactor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sk.springreactor.model.Person;
import com.sk.springreactor.service.PersonService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/persons")
public class PersonController {

	@Autowired
	private PersonService service;
	
	@GetMapping("/{id}")
	public Mono<ResponseEntity<Person>> getPersion(@PathVariable long id) {
		return service
				.get(id)
				.map(ResponseEntity::ok)
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}

	@GetMapping
	public Flux<Person> getAllPerson() {
		return service.getAll();
	}

	@PostMapping
	public Mono<Person> addNewPerson(@RequestBody Person person) {
		return service.create(Mono.just(person));
	}

	@PutMapping("/{id}")
	public Mono<ResponseEntity<Person>> updatePerson(@PathVariable long id, @RequestBody Person person) {
		return service
				.update(id, Mono.just(person))
				.map(ResponseEntity::ok)
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}

	@DeleteMapping("/{id}")
	public Mono<ResponseEntity<Void>> deletePerson(@PathVariable long id) {
		return service
				.delete(id)
				.map(ResponseEntity::ok)
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}
}
