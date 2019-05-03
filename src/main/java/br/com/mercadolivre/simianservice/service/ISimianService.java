package br.com.mercadolivre.simianservice.service;

import br.com.mercadolivre.simianservice.domain.DnaStructure;
import br.com.mercadolivre.simianservice.domain.Stats;

public interface ISimianService {
	boolean isSimian(String[] dna) throws Exception;
	Stats getStats();
	DnaStructure findOrCreateByDna(String[] dna);
}
