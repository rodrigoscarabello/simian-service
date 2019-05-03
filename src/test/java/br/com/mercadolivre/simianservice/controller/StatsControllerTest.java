package br.com.mercadolivre.simianservice.controller;

import br.com.mercadolivre.simianservice.SimianServiceApplicationTests;
import br.com.mercadolivre.simianservice.dao.ISimianDAO;
import br.com.mercadolivre.simianservice.domain.Stats;
import br.com.mercadolivre.simianservice.service.ISimianService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StatsControllerTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private ISimianService simianService;

	@Autowired
	private ISimianDAO simianDAO;

	@LocalServerPort
	private int port;

	@Before
	public void configure() {
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
	}

	@Test
	public void shouldReturnStatsFromDatabase() throws IOException {
		Stats stats = this.restTemplate.getForObject("/stats", Stats.class);
		assertThat(stats.getCountMutantDna()).isEqualTo(3);
		assertThat(stats.getCountHumanDna()).isEqualTo(4);
		assertThat(stats.getRatio()).isEqualByComparingTo(BigDecimal.valueOf(0.75));

		ResponseEntity<String> response = restTemplate.getForEntity(SimianServiceApplicationTests.HTTP_LOCALHOST + this.port + "/stats", String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

		ObjectMapper mapper = new ObjectMapper();
		JsonNode root = mapper.readTree(response.getBody());
		JsonNode countMutantDna = root.path("count_mutant_dna");
		assertThat(countMutantDna.asText()).isNotNull().isEqualTo("3");

		JsonNode countHumanDna = root.path("count_human_dna");
		assertThat(countHumanDna.asText()).isNotNull().isEqualTo("4");

		JsonNode ratio = root.path("ratio");
		assertThat(ratio.asText()).isNotNull().isEqualTo("0.75");
	}

}