package com.example.app.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.app.models.Atm;
import com.example.app.models.OperationCurrentAccount;
import com.example.app.models.typeOperationAtm;
import com.example.app.repository.atmDao;
import com.example.app.service.AtmService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AtmServiceImpl implements AtmService {
	@Value("${com.bootcamp.gateway.url}")
	String valorget;
	
	@Autowired
	public atmDao atmDao;
	
	@Override
	public Flux<Atm> findAllAtm()
	{
	return atmDao.findAll();
	
	}
	@Override
	public Mono<Atm> findByIdAtm(String id)
	{
	return atmDao.findById(id);
	
	}
	
	// 1- Deposito
	
	@Override
	public Mono<Atm> saveAtm(Atm atm)
	{
	 Mono<Atm> oper= Mono.just(atm); 
		
	 return oper.flatMap(a->{
		
		if(a.getTipoOperacion().getIdTipo()=="1") {
			OperationCurrentAccount coa=new OperationCurrentAccount();
			
			coa.setDni(a.getDni());
			coa.setCodigo_bancario_origen(a.getCodigo_bancario_origen());
			coa.setCuenta_origen(a.getCuenta_origen());
			coa.setCodigo_bancario_destino(a.getCodigo_bancario_destino());
			coa.setCuenta_destino(a.getCodigo_bancario_destino());
			coa.setCodigo_bancario_destino(a.getCodigo_bancario_destino());
			coa.setCodigo_bancario_destino(a.getCodigo_bancario_destino());
			coa.setCodigo_bancario_destino(a.getCodigo_bancario_destino());
			coa.setCodigo_bancario_destino(a.getCodigo_bancario_destino());
			coa.setFechaOperacion(a.getFechaOperacion());
			typeOperationAtm type=new typeOperationAtm();
			type.setIdTipo(atm.getTipoOperacion().getIdTipo());
			type.setIdTipo(atm.getTipoOperacion().getDescripcion());
			atm.setTipoOperacion(type);
			coa.setMontoPago(atm.getSoles());
			
	Mono<OperationCurrentAccount> oper1 = WebClient
			.builder()
			.baseUrl("http://"+ valorget +"/servicio-productos/api/OperCuentasCorrientes/")
			.defaultHeader(HttpHeaders.CONTENT_TYPE).build().post()
			.uri("/deposito/")
			.contentType(MediaType.APPLICATION_JSON)
			.body(BodyInserters.fromValue(coa))
			.retrieve().bodyToMono(OperationCurrentAccount.class).log();
			}
			
		return null;

	});
		
		
	
	
	
	
	
	}
	
	@Override
	public Mono<Void> deleteAtm(Atm bank) {
		return atmDao.delete(bank);
	}
	
}
