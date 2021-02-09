package es.baratz.edu.controller;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import es.baratz.edu.handler.UnsplashHandler;

@RestController
public class CollectionController {
	
	@Bean
	public RouterFunction<ServerResponse> itemsRouter(UnsplashHandler handler) {
		
		return route().GET("/", handler::index)
					  .GET("/collections/{filter}", handler::searchCollections)
					  .build();
	
	}
	
}
