package es.baratz.edu.handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import es.baratz.edu.dto.UnsplashCollection;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class UnsplashHandler {

	@Value("${unsplash.client-id}")
	private String clientId;
	
	@Value("${unsplash.collections}")
	private String urlCollections;
	
	@Value("${unsplash.page_size}")
	private int pageSize;
	
	private static final Logger LOG = LoggerFactory.getLogger(UnsplashHandler.class);
	
	
	public Mono<ServerResponse> index(ServerRequest request) {
			Map<String, String> data = new HashMap<>();
			String url = request.uri().getScheme() + "://" + request.uri().getHost() + ":" + request.uri().getPort() + "/collections";
			data.put("urlCollections", url);
			return ServerResponse.ok().contentType(MediaType.TEXT_HTML).render("index", data);
	}
	
	/**
	 * Get collections api.unsplash.com/collections paginated using Mono.expand
	 * https://unsplash.com/documentation#list-collections
	 * 
	 * @param request
	 * @return
	 */
	public Mono<ServerResponse> searchCollections(ServerRequest request) {
		
		String filter = request.pathVariable("filter");
		
		LOG.info("GET collections url: /collections/{}", filter);

		String url = urlCollections + "?client_id=" + clientId  +"&page=1&per_page=" + pageSize;
		
		Flux<UnsplashCollection> collections = getCollections(url).expand(response -> {
			
			if (response.statusCode().is2xxSuccessful()) {
				Optional<String> nextLink = getNextLink(response.headers().asHttpHeaders());
				if (nextLink.isPresent()){
					return getCollections(nextLink.get());
				}
			} else {
				LOG.error("error");
				return response.createException().flatMap( Mono::error );
			}
			return Flux.empty();
				
	    }).flatMap(response -> response.bodyToFlux(UnsplashCollection.class))
									 .filter(collection ->  hasFilter(collection, filter));
		
		return ServerResponse.ok().contentType(MediaType.TEXT_EVENT_STREAM)
				.body(collections, UnsplashCollection.class);
	}
	
	
	private Mono<ClientResponse> getCollections(String url) {
		LOG.info("get collection {}", url);
		return WebClient.create(url).get().exchange();                 
    }
	
	/**
	 * https://unsplash.com/documentation#pagination-headers
	 * 
	 * @param links
	 * @return
	 */
	private Optional<String> getNextLink(HttpHeaders headers) {
		
		List<String> links = headers.getValuesAsList("link");
		Optional<String> nextLink = links.stream().filter(link->link.contains("rel=\"next\"")).findAny();
		try {
			return nextLink.map( next -> {
				return next.split(";")[0].replace("<", "").replace(">", "");
			});
		} catch (Exception e) {
			return Optional.empty();
		}
		
	}
	
	private boolean hasFilter(UnsplashCollection collection, String filter) {
		try {
			if ("all".equals(filter))
				return true;
			return checkContain(collection.getId(), filter) || checkContain(collection.getTitle(), filter) || checkContain(collection.getDescription(), filter) || checkContain(collection.getCoverPhotoId(), filter);
		} catch (Exception e) {
			LOG.error(e.getMessage());
			return false;
		}
		
	}
	
	private boolean checkContain(String text, String pattern) {
		try {
			return text.contains(pattern);
		} catch (Exception e) {
			return false;
		}
	}

}
