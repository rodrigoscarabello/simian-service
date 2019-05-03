package br.com.mercadolivre.simianservice.service;

import br.com.mercadolivre.simianservice.SimianServiceApplicationTests;
import br.com.mercadolivre.simianservice.dao.ISimianDAO;
import br.com.mercadolivre.simianservice.domain.Stats;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SimianServiceTest {

	@Autowired
	private ISimianService simianService;

	@Autowired
	private ISimianDAO simianDAO;

	@Test(expected = Exception.class)
	public void isSimian() throws Exception {
		simianDAO.deleteAll();

		simianService.isSimian(new String[]{"CTGTGA", "CTAAAA", "TATTGT", "AGAGGG", "CCCCTA", "TCACTGGG"});
		simianService.isSimian(new String[]{});

		assertThat(simianService.isSimian(SimianServiceApplicationTests.SIMIAN_SEQUENCE_1)).isTrue();
		assertThat(simianService.isSimian(SimianServiceApplicationTests.HUMAN_SEQUENCE_1)).isTrue();
	}

	@Test
	public void getStats() {
		simianDAO.deleteAll();

		List<String[]> dnaStructures = new ArrayList<>();

		dnaStructures.add(SimianServiceApplicationTests.SIMIAN_SEQUENCE_1);
		dnaStructures.add(SimianServiceApplicationTests.SIMIAN_SEQUENCE_2);
		dnaStructures.add(SimianServiceApplicationTests.SIMIAN_SEQUENCE_3);
		dnaStructures.add(SimianServiceApplicationTests.HUMAN_SEQUENCE_1);
		dnaStructures.add(SimianServiceApplicationTests.HUMAN_SEQUENCE_2);
		dnaStructures.add(SimianServiceApplicationTests.HUMAN_SEQUENCE_3);
		dnaStructures.add(SimianServiceApplicationTests.HUMAN_SEQUENCE_4);

		dnaStructures.forEach(simianService::findOrCreateByDna);

		Stats stats = simianService.getStats();

		assertThat(stats.getCountMutantDna()).isEqualTo(3);
		assertThat(stats.getCountHumanDna()).isEqualTo(4);
		assertThat(stats.getRatio()).isEqualByComparingTo(BigDecimal.valueOf(0.75));
	}

	@Test
	public void findOrCreateByDna() {
		simianDAO.deleteAll();
		assertThat(simianService.findOrCreateByDna(SimianServiceApplicationTests.SIMIAN_SEQUENCE_1)).isNotNull().hasFieldOrPropertyWithValue("simian", true);
		assertThat(simianService.findOrCreateByDna(SimianServiceApplicationTests.HUMAN_SEQUENCE_1)).isNotNull().hasFieldOrPropertyWithValue("simian", false);
	}
}