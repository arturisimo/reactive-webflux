package es.baratz.edu.repository;

import es.baratz.edu.model.Accion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BolsaRepository extends JpaRepository<Accion,Integer>{
	
	
}