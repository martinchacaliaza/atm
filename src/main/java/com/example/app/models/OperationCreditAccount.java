package com.example.app.models;

import java.util.Date;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import reactor.core.publisher.Flux;

@Getter
@Setter

public class OperationCreditAccount {

	
	private String dni;
	
	private String codigo_bancario;
	
	private String numero_cuenta;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fechaOperacion;
	
	private TypeOperation tipoOperacion;
	
	private Double montoPago;
	

	
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd/MM/yyyy")
	public Date fechaOperacion() {
		return fechaOperacion;
	}
}










