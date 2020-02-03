package com.example.app.service;


import com.example.app.models.Atm;


import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AtmService {

	Flux<Atm> findAllAtm();
	Mono<Atm> findByIdAtm(String id);
	Mono<Atm> saveAtm(Atm atm);
	Mono<Void> deleteAtm(Atm cliente);
	Mono<Atm> saveAtmDeposito(Atm atm);
	Mono<Atm> saveAtmRetiro(Atm atm);
	Mono<Atm> saveAtmTranferencias(Atm atm);
	Mono<Atm> saveAtmPagoCredito(Atm atm);

	
}
