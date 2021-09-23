package com.example.tivicbank.api.model;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "deposito")
public class Deposito {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String codigo_deposito;
	private String valor_deposito;
	
	@JsonFormat(pattern = "yyyy/MM/dd HH:mm")
	private LocalDateTime data_deposito;
	public LocalDateTime getData_deposito() {
		return data_deposito;
	}
	public void setData_deposito(LocalDateTime data_deposito) {
		this.data_deposito = data_deposito;
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
		Deposito other = (Deposito) obj;
		return Objects.equals(id, other.id);
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCodigo_deposito() {
		return codigo_deposito;
	}
	public void setCodigo_deposito(String codigo_deposito) {
		this.codigo_deposito = codigo_deposito;
	}
	public String getValor_deposito() {
		return valor_deposito;
	}
	public void setValor_deposito(String valor_deposito) {
		this.valor_deposito = valor_deposito;
	}
	public Long getId_conta() {
		return id_conta;
	}
	public void setId_conta(Long id_conta) {
		this.id_conta = id_conta;
	}
}
