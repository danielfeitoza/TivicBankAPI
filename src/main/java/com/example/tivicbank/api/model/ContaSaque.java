package com.example.tivicbank.api.model;

public class ContaSaque {
	public Long id;
	public String valor_saque;
	public String getValor_saque() {
		return valor_saque;
	}
	public void setValor_saque(String valor_saque) {
		this.valor_saque = valor_saque;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
}
