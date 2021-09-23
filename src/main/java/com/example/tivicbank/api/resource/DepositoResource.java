package com.example.tivicbank.api.resource;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.tivicbank.api.event.RecursoCriadoEvent;
import com.example.tivicbank.api.model.Conta;
import com.example.tivicbank.api.model.ContaDeposito;
import com.example.tivicbank.api.model.ContaSaque;
import com.example.tivicbank.api.model.Deposito;
import com.example.tivicbank.api.model.Saque;
import com.example.tivicbank.api.repository.ContaRepository;
import com.example.tivicbank.api.repository.DepositoRepository;
import com.example.tivicbank.api.util.LoadScript;

@RestController
@RequestMapping("/api/deposito")
@CrossOrigin("http://localhost:4200")
public class DepositoResource {

	@Autowired
	private DepositoRepository depositoRepository;
	
	@Autowired
	private ContaRepository contaRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	
	@GetMapping
	public List<Deposito> listaDepositos(){
		List<Deposito> t = depositoRepository.findAll();
		return t;
	}
	
	@PostMapping
	public ResponseEntity<Deposito> criarDeposito(@Valid @RequestBody ContaDeposito infosaque, HttpServletResponse response) throws ParseException {
		Conta contaSalva = contaRepository.findById(infosaque.getId()).get();
		//var foi = load(contaSalva.getSaldo(), infosaque.get_valor());
		String teste = contaSalva.getSaldo();
		double as = load(teste);
		double ns = load(infosaque.getValor_deposito());
		double newSaldo = as + ns;
		contaSalva.setSaldo(String.format("%.2f", new BigDecimal(newSaldo)));
		contaRepository.save(contaSalva);
		
		Random random = new Random();
		String criptCodigoSaque = LoadScript.getHashMd5("TivicB" + contaSalva.getSaldo() + random.nextDouble());
		Deposito newDeposito = new Deposito();
		newDeposito.setCodigo_deposito(criptCodigoSaque);
		newDeposito.setValor_deposito(Double.toString(ns));
		newDeposito.setId_conta(contaSalva.getId());
		//DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
		//String agora = LocalDateTime.now().format(formatter);
		newDeposito.setData_deposito(LocalDateTime.now());
		Deposito deposito = depositoRepository.save(newDeposito);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, newDeposito.getId()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(newDeposito);
	}
	
	public double load(String number) throws ParseException {
		NumberFormat nf = NumberFormat.getNumberInstance(new Locale("pt", "BR"));
		double newSaldo = nf.parse(number).doubleValue();
		return newSaldo;
	}
}
