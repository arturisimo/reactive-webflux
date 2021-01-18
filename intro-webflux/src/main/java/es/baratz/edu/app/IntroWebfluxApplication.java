package es.baratz.edu.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

@ComponentScan(basePackages = {"es.baratz.edu.controller","es.baratz.edu.handler"})
@SpringBootApplication
public class IntroWebfluxApplication {

	public static void main(String[] args) {
		SpringApplication.run(IntroWebfluxApplication.class, args);
	}
	
	@Bean 
	public WebFilter corsFilter() { 
	 return (ServerWebExchange ctx, WebFilterChain chain) -> { 
		 ServerHttpRequest request = ctx.getRequest(); 
		 if (CorsUtils.isCorsRequest(request)) { 
			 ServerHttpResponse response = ctx.getResponse(); 
			 HttpHeaders headers = response.getHeaders(); 
			 headers.add("Access-Control-Allow-Origin", CorsConfiguration.ALL); 
			 headers.add("Access-Control-Allow-Methods", CorsConfiguration.ALL); 
			 headers.add("Access-Control-Allow-Headers", CorsConfiguration.ALL); 
    	 }	 
		 return chain.filter(ctx); 
	 	}; 
	} 
	
	
}
