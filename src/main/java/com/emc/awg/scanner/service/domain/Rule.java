package com.emc.awg.scanner.service.domain;

public class Rule {

	private Long id;
	
	private String antString;
	
	private String description;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAntString() {
		return antString;
	}

	public void setAntString(String antString) {
		this.antString = antString;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Rule [id=" + id + ", antString=" + antString + ", description=" + description + "]";
	}
	
}
