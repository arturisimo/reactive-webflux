package es.baratz.edu.controller;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import es.baratz.edu.handler.QuoteHandler;

@RestController
public class QuoteController {
	
	@Value("${delay.item.ms}")
	private int delay; 
	
	@Bean
	public RouterFunction<ServerResponse> quoteRouter(QuoteHandler handler) {
		
		return route().GET("/quotes", handler::getQuotes)
					  .GET("/quotes/{id}", handler::getQuoteById)
					  .build();
	
	}
	
}