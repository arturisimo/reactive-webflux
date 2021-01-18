package es.baratz.edu.service;

import java.util.List;

import es.baratz.edu.model.Accion;
import es.baratz.edu.repository.BolsaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BolsaServiceImpl implements BolsaService {
	
	@Autowired
	BolsaRepository bolsaRepository;
	
	
	@Override
	public List<Accion> getAcciones() {
		return bolsaRepository.findAll();
	}

	
	
}
