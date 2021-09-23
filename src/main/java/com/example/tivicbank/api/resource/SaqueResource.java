package com.example.tivicbank.api.resource;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Random;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.tivicbank.api.event.RecursoCriadoEvent;
import com.example.tivicbank.api.model.Conta;
import com.example.tivicbank.api.model.ContaSaque;
import com.example.tivicbank.api.model.Saque;
import com.example.tivicbank.api.repository.ContaRepository;
import com.example.tivicbank.api.repository.SaqueRepository;
import com.example.tivicbank.api.util.LoadScript;

@RestController
@RequestMapping("/api/saque")
@CrossOrigin("http://localhost:4200")
public class SaqueResource {
	@Autowired
	private SaqueRepository saqueRepository;
	
	@Autowired
	private ContaRepository contaRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@PostMapping
	//@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Saque> criar(@Valid @RequestBody ContaSaque infosaque, HttpServletResponse response) throws ParseException {
		Conta contaSalva = contaRepository.findById(infosaque.getId()).get();
		//var foi = load(contaSalva.getSaldo(), infosaque.get_valor());
		String teste = contaSalva.getSaldo();
		double as = load(teste);
		double ns = load(infosaque.getValor_saque());
		double newSaldo = as - ns;
		contaSalva.setSaldo(String.format("%.2f", new BigDecimal(newSaldo)));
		contaRepository.save(contaSalva);
		
		Random random = new Random();
		String criptCodigoSaque = LoadScript.getHashMd5("TivicB" + contaSalva.getSaldo() + random.nextDouble());
		Saque newSaque = new Saque();
		newSaque.setCodigo_saque(criptCodigoSaque);
		newSaque.setValor_saque(Double.toString(ns));
		newSaque.setId_conta(contaSalva.getId());
		newSaque.setData_saque(LocalDateTime.now());
		Saque saque = saqueRepository.save(newSaque);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, saque.getId()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(saque);
	}
	
	public double load(String number) throws ParseException {
		NumberFormat nf = NumberFormat.getNumberInstance(new Locale("pt", "BR"));
		double newSaldo = nf.parse(number).doubleValue();
		return newSaldo;
	}
	
	@GetMapping
	public List<Saque> checkSaques(){
		var tem = LocalDateTime.now();
		return saqueRepository.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Saque> saqueLog(@PathVariable Long id){
		Saque saque = saqueRepository.findById(id).get();
		return !saque.getValor_saque().isEmpty() ? ResponseEntity.ok(saque) : ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteSaque(@PathVariable Long id) {
		saqueRepository.deleteById(id);
	}
}
