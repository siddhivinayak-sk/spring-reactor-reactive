package com.sk.springreactor.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.sk.springreactor.model.Person;
import com.sk.springreactor.service.PersonService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
//@Transactional
public class PersonHandler {

	@Autowired
	private PersonService service;
	
	public Mono<ServerResponse> getPerson(ServerRequest request) {
		return service
				.get(Long.parseLong(request.pathVariable("id")))
				.flatMap(person -> ServerResponse
						.ok()
						.body(BodyInserters.fromValue(person))
						.switchIfEmpty(ServerResponse.notFound().build()));
	}
	
	public Flux<ServerResponse> getAllPerson(ServerRequest request) {
		return service
				.getAll()
				.flatMap(person -> ServerResponse
						.ok()
						.body(BodyInserters.fromValue(person))
						.switchIfEmpty(ServerResponse.notFound().build()));
	}
	
	public Flux<ServerResponse> findByName(ServerRequest request) {
		return service
				.findByName(request.pathVariable("name"))
				.flatMap(person -> ServerResponse
						.ok()
						.body(BodyInserters.fromValue(person))
						.switchIfEmpty(ServerResponse.notFound().build()));
	}
	
	public Mono<ServerResponse> createPerson(ServerRequest request) {
		Mono<Person> mPerson = request.bodyToMono(Person.class);
		return service
				.create(mPerson)
				.flatMap(person -> ServerResponse
						.ok()
						.body(BodyInserters.fromValue(person))
						.switchIfEmpty(ServerResponse.notFound().build()));
	}
	
	public Mono<ServerResponse> updatePerson(ServerRequest request) {
		long id = Long.parseLong(request.pathVariable("id"));
		Mono<Person> mPerson = request.bodyToMono(Person.class);
		return service
				.update(id, mPerson)
				.flatMap(person -> ServerResponse
						.ok()
						.body(BodyInserters.fromValue(person))
						.switchIfEmpty(ServerResponse.notFound().build()));
	}
	
	public Mono<ServerResponse> deletePerson(ServerRequest request) {
		long id = Long.parseLong(request.pathVariable("id"));
		return service
				.delete(id)
				.flatMap(person -> ServerResponse
						.ok()
						.body(BodyInserters.fromValue(person))
						.switchIfEmpty(ServerResponse.notFound().build()));
	}

	
	
//    private final Validator validator = new PersonValidator();
//    
//    private void validate(Person person) {
//        Errors errors = new BeanPropertyBindingResult(person, "person");
//        validator.validate(person, errors);
//        if (errors.hasErrors()) {
//            throw new ServerWebInputException(errors.toString()); 
//        }
//    }	
}
