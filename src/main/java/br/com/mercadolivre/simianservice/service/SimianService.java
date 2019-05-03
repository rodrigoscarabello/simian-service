package br.com.mercadolivre.simianservice.service;

import br.com.mercadolivre.simianservice.dao.ISimianDAO;
import br.com.mercadolivre.simianservice.domain.DnaStructure;
import br.com.mercadolivre.simianservice.domain.Stats;
import br.com.mercadolivre.simianservice.helper.DnaHelper;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static java.lang.String.join;
import static org.springframework.data.mongodb.core.query.Criteria.where;

@Service
public class SimianService implements ISimianService {

	private final ISimianDAO dao;
	private final MongoTemplate mongoTemplate;

	public SimianService(ISimianDAO dao, MongoTemplate mongoTemplate) {
		this.dao = dao;
		this.mongoTemplate = mongoTemplate;
	}

	@Override
	public boolean isSimian(String[] dna) throws Exception {
		if(!DnaHelper.isValidDna(dna))
			throw new Exception("Dna sequence is invalid!");

		return findOrCreateByDna(dna).getSimian();
	}

	@Override
	public Stats getStats() {
		long simian = mongoTemplate.count(new Query(where("simian").is(true)), DnaStructure.class);
		long human = mongoTemplate.count(new Query(where("simian").is(false)), DnaStructure.class);
		return new Stats(simian, human);
	}

	private boolean analyzeDna(String[] dna) {
		List<String> dnaList = Arrays.asList(dna);
		List<Future<Long>> futures = new ArrayList<>();
		CompletionService<Long> completionService = new ExecutorCompletionService<>(Executors.newCachedThreadPool(Thread::new));

		futures.add(completionService.submit(() -> DnaHelper.analyzeDnaInLine(dnaList)));
		futures.add(completionService.submit(() -> DnaHelper.analyzeDnaInColumns(dnaList)));
		futures.add(completionService.submit(() -> DnaHelper.analyzeDnaInDiagonalLeftRight(dnaList)));
		futures.add(completionService.submit(() -> DnaHelper.analyzeDnaInDiagonalRightLeft(dnaList)));

		try {
			long sequencesFound = 0;
			for(int i = 0; i < futures.size(); i++) {
				Future<Long> f = completionService.take();
				sequencesFound += f.get();
				if(sequencesFound > 1)
					return true;
			}
		} catch (Exception e) {
			return false;
		}

		return false;
	}

	public DnaStructure findOrCreateByDna(String[] dna) {
		DnaStructure structure = dao.findById(join("", dna).hashCode())
				.orElse(new DnaStructure(dna));

		if(structure.getSimian() == null) {
			boolean isSimian = analyzeDna(dna);
			structure.setSimian(isSimian);
			dao.save(structure);
		}

		return structure;
	}
}
