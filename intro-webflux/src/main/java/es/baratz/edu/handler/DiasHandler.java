package es.baratz.edu.handler;

import java.time.Duration;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class DiasHandler {

	private static final String[] SEMANA = {"lunes","martes","miercoles","jueves","viernes","sabado","domingo"};
	
	
	public Mono<ServerResponse> getDias(ServerRequest request) {
		
		Flux<String> dias = Flux.just(SEMANA)
								.delayElements(Duration.ofSeconds(3));
		return ServerResponse.ok().contentType(MediaType.TEXT_EVENT_STREAM)
				.body(dias, String.class);
	}
	
}
