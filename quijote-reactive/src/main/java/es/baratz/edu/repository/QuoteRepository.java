package es.baratz.edu.repository;

import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import org.springframework.stereotype.Repository;

import es.baratz.edu.model.Quote;

@Repository
public interface QuoteRepository extends ReactiveSortingRepository<Quote, String> {
	
}