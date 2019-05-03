package br.com.mercadolivre.simianservice.controller;

import br.com.mercadolivre.simianservice.SimianServiceApplicationTests;
import br.com.mercadolivre.simianservice.domain.DnaSequenceDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SimianControllerTest {

	private static final String SIMIAN = "/simian";

	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	private int port;

	@Test
	public void shouldReturn200OkWhenDnaSequenceIsSimian() {
		DnaSequenceDTO dto = new DnaSequenceDTO();
		dto.setDna(SimianServiceApplicationTests.SIMIAN_SEQUENCE_1);
		ResponseEntity<Map> entity = this.restTemplate.postForEntity(SimianServiceApplicationTests.HTTP_LOCALHOST + this.port + SIMIAN, dto, Map.class);

		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@Test
	public void shouldReturn403ForbiddenWhenDnaSequenceIsHuman() {
		DnaSequenceDTO dto = new DnaSequenceDTO();
		dto.setDna(SimianServiceApplicationTests.HUMAN_SEQUENCE_2);
		ResponseEntity<Map> entity = this.restTemplate.postForEntity(SimianServiceApplicationTests.HTTP_LOCALHOST + this.port + SIMIAN, dto, Map.class);

		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
	}

	@Test
	public void shouldReturn403ForbiddenWhenDnaSequenceContainsInvalidCharacters() {
		DnaSequenceDTO dto = new DnaSequenceDTO();
		dto.setDna(SimianServiceApplicationTests.INVALID_CHARACTERS);
		ResponseEntity<Map> entity = this.restTemplate.postForEntity(SimianServiceApplicationTests.HTTP_LOCALHOST + this.port + SIMIAN, dto, Map.class);

		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
	}

	@Test
	public void shouldReturn403ForbiddenWhenDnaSequenceIsNotSquareMatrix() {
		DnaSequenceDTO dto = new DnaSequenceDTO();
		dto.setDna(SimianServiceApplicationTests.INVALID_SQUARE_MATRIX);
		ResponseEntity<Map> entity = this.restTemplate.postForEntity(SimianServiceApplicationTests.HTTP_LOCALHOST + this.port + SIMIAN, dto, Map.class);

		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
	}

}
