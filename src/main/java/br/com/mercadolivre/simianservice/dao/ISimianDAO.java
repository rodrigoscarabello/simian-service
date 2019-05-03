package br.com.mercadolivre.simianservice.dao;

import br.com.mercadolivre.simianservice.domain.DnaStructure;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ISimianDAO extends MongoRepository<DnaStructure, Integer> {
}
