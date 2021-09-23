package com.sk.springreactor.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.sk.springreactor.handler.PersonHandler;

@Configuration
@EnableWebFlux
//@EnableTransactionManagement
public class WebFluxConfig implements WebFluxConfigurer {
	
	@Autowired
	private PersonHandler handler;
	
	@Bean
    public RouterFunction<?> routerFunctionA() {
		RouterFunction<ServerResponse> route = RouterFunctions
				.route()
				.path("/person", (builder) -> {
					builder
					.GET("/{id}", RequestPredicates.accept(MediaType.APPLICATION_JSON), handler::getPerson)
					.POST(RequestPredicates.accept(MediaType.APPLICATION_JSON), handler::createPerson)
					.PUT("/{id}", RequestPredicates.accept(MediaType.APPLICATION_JSON), handler::updatePerson)
					.DELETE("/{id}", RequestPredicates.accept(MediaType.APPLICATION_JSON), handler::deletePerson)
					;
				})
				.build();
		return route;
	}
}
