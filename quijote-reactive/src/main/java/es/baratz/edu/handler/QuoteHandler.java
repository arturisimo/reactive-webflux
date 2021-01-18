package es.baratz.edu.handler;

import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import es.baratz.edu.model.Quote;
import es.baratz.edu.repository.QuoteRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class QuoteHandler {
	
	private static final Logger log = LoggerFactory.getLogger(QuoteHandler.class);
    
	@Autowired
	QuoteRepository quoteRepository;
	
	@Value("${delay.item.ms}")
	private int delay; 
	
	public Mono<ServerResponse> getQuotes(ServerRequest request) {
		
		Flux<Quote> quotes = quoteRepository.findAll().delayElements(Duration.ofMillis(delay));
		
		return ServerResponse.ok().contentType(MediaType.TEXT_EVENT_STREAM)
							.body(quotes, Quote.class);
	}
	
	
	public Mono<ServerResponse> getQuoteById(ServerRequest request) {
		String id = request.pathVariable("id");
		
		Mono<Quote> quote = quoteRepository.findById(id);
		
		log.info("id: {}", id);
		
		return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
								  .body(quote, Quote.class)
								  .switchIfEmpty(ServerResponse.notFound().build());
				
	}
}
