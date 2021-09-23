package com.example.tivicbank.api.model;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name= "saque")
public class Saque {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String codigo_saque;
	private String valor_saque;
	
	@JsonFormat(pattern = "yyyy/MM/dd HH:mm")
	private LocalDateTime data_saque;
	public LocalDateTime getData_saque() {
		return data_saque;
	}
	public void setData_saque(LocalDateTime data_saque) {
		this.data_saque = data_saque;
	}
	private Long id_conta;
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Saque other = (Saque) obj;
		return Objects.equals(id, other.id);
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCodigo_saque() {
		return codigo_saque;
	}
	public void setCodigo_saque(String codigo_saque) {
		this.codigo_saque = codigo_saque;
	}
	public String getValor_saque() {
		return valor_saque;
	}
	public void setValor_saque(String valor_saque) {
		this.valor_saque = valor_saque;
	}
	public Long getId_conta() {
		return id_conta;
	}
	public void setId_conta(Long id_conta) {
		this.id_conta = id_conta;
	}
	
}


