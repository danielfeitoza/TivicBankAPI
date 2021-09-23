package com.example.tivicbank.api.resource;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.tivicbank.api.event.RecursoCriadoEvent;
import com.example.tivicbank.api.model.Cliente;
import com.example.tivicbank.api.model.Conta;
import com.example.tivicbank.api.repository.ClienteRepository;

@RestController
@RequestMapping("/api/clientes")
@CrossOrigin("http://localhost:4200")
public class ClienteResource {
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@GetMapping
	public List<Cliente> listaClientes(){
		return clienteRepository.findAll();
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Cliente> criarCliente(@Valid @RequestBody Cliente cliente, HttpServletResponse response){
		Cliente clienteNovo = clienteRepository.save(cliente);
		
		publisher.publishEvent(new RecursoCriadoEvent(this, response, clienteNovo.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(clienteNovo);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Optional<Cliente>> clinteSearch (@PathVariable Long id) {
		Optional<Cliente> cliente = clienteRepository.findById(id);
		return !cliente.isEmpty() ? ResponseEntity.ok(cliente) : ResponseEntity.notFound().build();
	}
	//Mexer amanhã validações e exceções
	@PostMapping("/login")
	public ResponseEntity<Cliente> contaLogin(@Valid @RequestBody Cliente cliente){
		//Cliente clienteLogin = clienteRepository.(cliente.getNome().);
		return ResponseEntity.status(HttpStatus.CREATED).body(cliente);
	}
}
