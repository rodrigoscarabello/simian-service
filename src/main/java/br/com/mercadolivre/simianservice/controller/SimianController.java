package br.com.mercadolivre.simianservice.controller;

import br.com.mercadolivre.simianservice.domain.DnaSequenceDTO;
import br.com.mercadolivre.simianservice.service.ISimianService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.ResponseEntity.status;

@Controller
public class SimianController {

	private final ISimianService simianService;

	public SimianController(ISimianService simianService) {
		this.simianService = simianService;
	}

	@PostMapping(value = "/simian", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> isSimian(@RequestBody DnaSequenceDTO dnaStructure) {
		try {
			return simianService.isSimian(dnaStructure.getDna()) ?
					status(OK).build() :
					status(FORBIDDEN).build();
		} catch (Exception e) {
			return ResponseEntity.status(FORBIDDEN).build();
		}
	}
}
