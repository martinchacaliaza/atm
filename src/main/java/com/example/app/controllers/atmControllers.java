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
		return Mono.just(ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON_UTF8)
				.body(bankService.findAllAtm())

		);
	}

	@ApiOperation(value = "LISTA MOV POR ID CLIENTE", notes="")
	@GetMapping("/{id}")
	public Mono<ResponseEntity<Atm>> viewId(@PathVariable String id) {
		return bankService.findByIdAtm(id)
				.map(p -> ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON_UTF8).body(p))
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}

	@ApiOperation(value = "ACTUALIZA BANCO POR ID", notes="")
	@PutMapping
	public Mono<Atm> updateClientePersonal(@RequestBody Atm cliente) {
		System.out.println(cliente.toString());
		return bankService.saveAtm(cliente);
	}
	
	@ApiOperation(value = "GUARDA MOV", notes="")
	@PostMapping
	public Mono<Atm> guardarCliente(@RequestBody Atm bank) {
			return bankService.saveAtm(bank);

	}
	
	
	@ApiOperation(value = "ELIMINA MOV", notes="")
	@DeleteMapping("/{id}")
	public Mono<ResponseEntity<Void>> deleteBanco(@PathVariable String id) {
		return bankService.findByIdAtm(id)
				.flatMap(s -> {
			return bankService.deleteAtm(s).
					then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)));
		}).defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.NO_CONTENT));
	}

}



