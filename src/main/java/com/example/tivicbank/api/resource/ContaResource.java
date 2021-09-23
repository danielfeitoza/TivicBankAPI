package com.example.tivicbank.api.resource;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.tivicbank.api.event.RecursoCriadoEvent;
import com.example.tivicbank.api.model.BaseModel;
import com.example.tivicbank.api.model.Conta;
import com.example.tivicbank.api.model.Saque;
import com.example.tivicbank.api.repository.ContaRepository;
import com.example.tivicbank.api.repository.SaqueRepository;
import com.example.tivicbank.api.util.LoadScript;

@RestController
@RequestMapping("/api/contas")
@CrossOrigin("http://localhost:4200")
public class ContaResource {

	@Autowired
	private ContaRepository contaRepository;
	
	@Autowired
	private SaqueRepository saqueRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@GetMapping
	public List<Conta> ListaBancos(){
		return contaRepository.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Optional<Conta>> contaSearch(@PathVariable Long id){
		Optional<Conta> conta = contaRepository.findById(id);
		return !conta.isEmpty() ? ResponseEntity.ok(conta) : ResponseEntity.notFound().build();
	}
	
	@PostMapping("/teste")
	public ResponseEntity<Optional<Conta>> contaSearchPost(@Valid @RequestBody BaseModel id){
		Optional<Conta> conta = contaRepository.findById(id.getId());
		return !conta.isEmpty() ? ResponseEntity.ok(conta) : ResponseEntity.notFound().build();
	}
	
	@PostMapping("/")
	public ResponseEntity<Conta> criar(@Valid @RequestBody Conta contat, HttpServletResponse response) {
		
		Conta contaSalva = contaRepository.findById(contat.getId()).get();

		publisher.publishEvent(new RecursoCriadoEvent(this, response, contaSalva.getId()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(contaSalva);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Conta> contaUpdate(@PathVariable Long id, @Valid @RequestBody Conta conta){
		Conta contaUpdate = contaRepository.getById(id);
		String[] ig = {"id", "conta", "tipo","agencia", "id_cliente", "id_banco"};
		BeanUtils.copyProperties(conta, contaUpdate, ig);
		contaRepository.save(contaUpdate);
		return ResponseEntity.ok(contaUpdate);
	}
	
}
