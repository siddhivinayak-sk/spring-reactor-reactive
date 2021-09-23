package com.sk.springreactor.model;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonView;

@Scope(scopeName = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
@Document("person")
public class Person {
	@Id
	private long id;
	private String name;
	private String password;
	
	public Person() {
		super();
	}

	public Person(long id, String name, String password) {
		super();
		this.id = id;
		this.name = name;
		this.password = password;
	}

	@JsonView(WithoutPasswordView.class)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@JsonView(WithoutPasswordView.class)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JsonView(WithPasswordView.class)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
    public interface WithoutPasswordView {};
    public interface WithPasswordView extends WithoutPasswordView {}
    
	@Override
	public String toString() {
		return "Person [id=" + id + ", name=" + name + ", password=" + password + "]";
	};
}
