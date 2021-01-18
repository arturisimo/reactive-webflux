package es.baratz.edu.app;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import es.baratz.edu.model.Quote;
import es.baratz.edu.repository.QuoteRepository;
import reactor.core.publisher.Flux;

@Component
public class DataLoader implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(DataLoader.class);
    
    @Value("${data.source}")
	private String dataSource; 
    
    @Autowired
	QuoteRepository quoteRepository;
    
    @Override
    public void run(final ApplicationArguments args) {
        if (quoteRepository.count().block() == 0L) {
        	
        	try {
        		
        		Supplier<String> idSupplier = getIdSequenceSupplier();
                try (BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream(dataSource)))) {
                	
                	Flux.fromStream(br.lines()
	                			.filter(line -> !line.trim().isEmpty())
	                			.map(line -> new Quote(idSupplier.get(),"El Quijote", line))
	                			.map(quote -> quoteRepository.save(quote))
                			).subscribe(mono -> log.info("quote: {}", mono.block()));
                	
                }
                
                log.info("Repository contains now {} entries.", quoteRepository.count().block());
        		
			} catch (Exception e) {
				log.error("{}", e.getMessage());
			}
        	
            
        }
    }

    private Supplier<String> getIdSequenceSupplier() {
        return new Supplier<String>() {
        	Long l = 0L;

    	    @Override
    	    public String get() {
    	        // adds padding zeroes
    	        return String.format("%05d", l++);
    	    }
		};
    }
    
   
}