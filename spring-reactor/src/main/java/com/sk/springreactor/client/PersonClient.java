package com.sk.springreactor.client;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import com.sk.springreactor.model.Person;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class PersonClient {

	public static void main(String...args) {
		PersonClient personClient = new PersonClient("http://127.0.0.1:8080");
		personClient.get(101l);
		personClient.create(600000);
		//personClient.update(600000);
		//personClient.delete(600000);
	}

	private String baseUri;
	private WebClient webClient;
	
	public PersonClient(String uri) {
		baseUri = uri;
		webClient = WebClient.create(baseUri);
	}
	
	public void get(long id) {
		Mono<Person> person = webClient
				.get()
				.uri("/person/" + id)
				.headers(headers -> headers.setBasicAuth("admin", "adminpwd"))
				.accept(MediaType.APPLICATION_JSON)
				.attribute("Content-Type", MediaType.APPLICATION_JSON)
				.retrieve()
				.bodyToMono(Person.class);
		System.err.println(person.block());
	}
	
	public void getAll() {
		Flux<Person> person = webClient
				.get()
				.uri("/person")
				.headers(headers -> headers.setBasicAuth("admin", "adminpwd"))
				.retrieve()
				.bodyToFlux(Person.class);
		person.subscribe(System.out::println);
	}
	
	public void findByName(String name) {
		Flux<Person> person = webClient
				.get()
				.uri(uriBuilder -> uriBuilder.path("/person/search").queryParam("name", name).build())
				.headers(headers -> headers.setBasicAuth("admin", "adminpwd"))
				.retrieve()
				.bodyToFlux(Person.class);
		person.subscribe(System.out::println);
	}
	
	public void create(long id) {
		Mono<Person> person = webClient
				.post()
				.uri("/person")
				.headers(headers -> headers.setBasicAuth("admin", "adminpwd"))
				.accept(MediaType.APPLICATION_JSON)
				.attribute("Content-Type", MediaType.APPLICATION_JSON)
				.body(Mono.just(new Person(id, "User-" + id, "Pass" + id)), Person.class)
				.retrieve()
				.bodyToMono(Person.class);
		System.err.println(person.block());
	}
	
	public void update(long id) {
		Mono<Person> person = webClient
				.put()
				.uri("/person/" + id)
				.headers(headers -> headers.setBasicAuth("admin", "adminpwd"))
				.body(Mono.just(new Person(id, "UX102Z-" + id, "PX102Z-" + id)), Person.class)
				.retrieve()
				.bodyToMono(Person.class);
		System.err.println(person.block());
	}
	
	public void delete(long id) {
		Mono<Void> person = webClient
				.delete()
				.uri("/person/" + id)
				.headers(headers -> headers.setBasicAuth("admin", "adminpwd"))
				.retrieve()
				.bodyToMono(Void.class);
		person.block();
	}
	
	
	
	
	
	
}
