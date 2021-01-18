package es.baratz.edu.controller;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import es.baratz.edu.handler.DiasHandler;

@RestController
public class DiasController {
	
	@Bean
	public RouterFunction<ServerResponse> itemsRouter(DiasHandler handler) {
		
		return route().GET("/dias", handler::getDias)
					  .build();
	
	}
	
}
