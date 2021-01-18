package es.baratz.edu.controller;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import es.baratz.edu.model.Accion;
import es.baratz.edu.service.BolsaService;
import reactor.core.publisher.Flux;

@RestController
public class BolsaController {
	
	@Autowired
	BolsaService bolsaService;
	
	@GetMapping(value="/acciones", produces = "text/event-stream")
	public Flux<Accion> getAcciones() {
		return Flux.fromIterable(bolsaService.getAcciones()).delayElements(Duration.ofSeconds(3));
	}
	
	

}
