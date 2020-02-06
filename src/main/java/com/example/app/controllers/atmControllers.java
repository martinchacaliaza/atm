package com.example.app.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.app.models.Atm;
import com.example.app.service.AtmService;

import io.swagger.annotations.ApiOperation;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequestMapping("/api/atm")
@RestController
public class atmControllers {

	@Autowired
	private AtmService bankService;

	@ApiOperation(value = "LISTA TODOS LOS MOV. EN EL CAJERO", notes="")
	@GetMapping
	public Mono<ResponseEntity<Flux<Atm>>> findAll() {
		return Mono.just(ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(bankService.findAllAtm())

		);
	}

	@ApiOperation(value = "LISTA MOV POR ID CAJERO", notes="")
	@GetMapping("/{id}")
	public Mono<ResponseEntity<Atm>> viewId(@PathVariable String id) {
		return bankService.findByIdAtm(id)
				.map(p -> ResponseEntity.ok()
						.contentType(MediaType.APPLICATION_JSON).body(p))
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}

	@ApiOperation(value = "ACTUALIZA CAJERO POR ID", notes="")
	@PutMapping
	public Mono<Atm> updateClientePersonal(@RequestBody Atm cliente) {
		return bankService.saveAtm(cliente);
	}
	
	@ApiOperation(value = "GUARDA MOV DEPOSITOS", notes="")
	@PostMapping("/deposito")
	public Mono<Atm> guardarMovDepo(@RequestBody Atm atm) {
			return bankService.saveAtmDeposito(atm);

	}
	
	@ApiOperation(value = "GUARDA MOV DEPOSITOS", notes="")
	@PostMapping("/retiro")
	public Mono<Atm> guardarMovRet(@RequestBody Atm atm) {
			return bankService.saveAtmRetiro(atm);

	}
	
	@ApiOperation(value = "GUARDA  CUENTA A CUENTA", notes="")
	@PostMapping("/transferencia")
	public Mono<Atm> guardarMovTransf(@RequestBody Atm atm) {
			return bankService.saveAtmTranferencias(atm);
	}
	
	@ApiOperation(value = "GUARDA  CUENTA A CREDITO", notes="")
	@PostMapping("/bancacredito")
	public Mono<Atm> guardarMovTransf2(@RequestBody Atm atm) {
			return bankService.saveAtmPagoCredito2(atm);
	}
	
	@ApiOperation(value = "GUARDA MOV CUENTA A CREDITO", notes="")
	@PostMapping("/pagoCredit")
	public Mono<Atm> guardarMovPagos(@RequestBody Atm atm) {
			return bankService.saveAtmPagoCredito(atm);

	}
	
	@ApiOperation(value = "ELIMINA MOV", notes="")
	@DeleteMapping("/{id}")
	public Mono<ResponseEntity<Void>> deleteBanco(@PathVariable String id) {
		return bankService.findByIdAtm(id)
				.flatMap(s ->{
			return bankService.deleteAtm(s).
					then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)));
		}).defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.NO_CONTENT));
	}
}



