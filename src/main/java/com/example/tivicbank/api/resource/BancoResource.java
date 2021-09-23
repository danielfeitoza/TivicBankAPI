package com.example.tivicbank.api.resource;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.tivicbank.api.model.Banco;
import com.example.tivicbank.api.repository.BancoRepository;

@RestController
@RequestMapping("/api/bancos")
@CrossOrigin("http://localhost:4200")
public class BancoResource {
	
	@Autowired
	private BancoRepository bancoRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@GetMapping
	public List<Banco> listaBancos(){
		return bancoRepository.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Banco> bancoSearch(@PathVariable Long id){
		Banco banco = bancoRepository.findById(id).get();
		return !banco.getNome_banco().isEmpty() ? ResponseEntity.ok(banco) : ResponseEntity.notFound().build();
	}
}
