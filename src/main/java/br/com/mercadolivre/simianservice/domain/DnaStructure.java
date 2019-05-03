package br.com.mercadolivre.simianservice.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "dna_estructure")
public class DnaStructure {

	@Id
	private Integer id;
	private String[] dna;
	private Boolean simian;

	public DnaStructure() {}

	public DnaStructure(String[] dna) {
		this.id = String.join("", dna).hashCode();
		this.dna = dna;
	}

	public DnaStructure(boolean simian) {
		this.simian = simian;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String[] getDna() {
		return dna;
	}

	public void setDna(String[] dna) {
		this.dna = dna;
	}

	public Boolean getSimian() {
		return simian;
	}

	public void setSimian(Boolean simian) {
		this.simian = simian;
	}

}
