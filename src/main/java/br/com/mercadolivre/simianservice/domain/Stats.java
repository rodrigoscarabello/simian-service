package br.com.mercadolivre.simianservice.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Stats {

	@JsonProperty("count_mutant_dna")
	private Long countMutantDna;

	@JsonProperty("count_human_dna")
	private Long countHumanDna;

	private BigDecimal ratio;

	public Stats(long simian, long human) {
		this.countMutantDna = simian;
		this.countHumanDna = human;
		this.ratio = new BigDecimal(simian).setScale(2, RoundingMode.HALF_UP)
										   .divide(new BigDecimal(human == 0 ? 1 : human), RoundingMode.HALF_UP);
	}

	public Long getCountMutantDna() {
		return countMutantDna;
	}

	public void setCountMutantDna(Long countMutantDna) {
		this.countMutantDna = countMutantDna;
	}

	public Long getCountHumanDna() {
		return countHumanDna;
	}

	public void setCountHumanDna(Long countHumanDna) {
		this.countHumanDna = countHumanDna;
	}

	public BigDecimal getRatio() {
		return ratio;
	}

	public void setRatio(BigDecimal ratio) {
		this.ratio = ratio;
	}
}
