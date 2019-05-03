package br.com.mercadolivre.simianservice.controller;

import br.com.mercadolivre.simianservice.domain.Stats;
import br.com.mercadolivre.simianservice.service.ISimianService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class StatsController {

	private final ISimianService simianService;

	public StatsController(ISimianService simianService) {
		this.simianService = simianService;
	}

	@GetMapping(value = "/stats", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Stats getStats() {
		return simianService.getStats();
	}

}
