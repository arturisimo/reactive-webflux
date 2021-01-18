## Programación reactiva

La programación reactiva es un nuevo paradigma en el desarrollo de aplicaciones. En la aplicación tradicional por cada petición a nuestra aplicación un hilo (thread) es bloqueado hasta procesar su respuesta; con el modelo de aplicación reactiva un hilo podría manejar varias peticiones de manera asíncrona y notificara mediante eventos su respuesta; además el término "reactivo" se refiere construir aplicaciones en base a eventos u en otras palabras a la reacción al cambio; es decir reaccionar a eventos de E/S. 

Siguiendo el patrón **publicacion/subcripcion** La aplicación está suscrito a un servicio de publicación, el servicio manda flujos de datos a los suscriptores sin que hay una petición previa.


## Spring Webflux

Desde la versión 5.x de Spring se introduce Spring Webflux como framework para el desarrollo de aplicaciones web reactivas. Esta basado en un estandar reactive streams y corre en servidores web como: Netty, Undertow, u contenedores que soporte la especificacion de Servlets 3.1 o superior. Estos contenedores funcionan con un loop de eventos sin bloquear nunca la entrada/salida y sirviendo de manera asíncrona.

Spring Webflux usa el proyecto **Reactor**; Reactor es una implementación de la especificación **Reactive Streams** que incluye adaptadores que facilitan su uso al interactuar con el API de una manera más simple, existen dos clases principales de productores que manejan los resultados donde podemos aplicar los operadores:

* Mono: Mono representa el resultado asíncrono de un valor simple o vacío (0...1) elemento.
* Flux: El flujo representa una secuencia asíncrona de 0 a n elementos.


## Despliegue proyecto



	mvn clean install -Pdocker

	docker-compose build
	
	docker-compose up
	
	docker-compose down -v


Cliente http://localhost:8080/reactive


## Bibliografía

* https://www.reactivemanifesto.org/es

* http://www.reactive-streams.org/

* https://projectreactor.io/

* https://docs.spring.io/spring-framework/docs/current/reference/html/web-reactive.html
