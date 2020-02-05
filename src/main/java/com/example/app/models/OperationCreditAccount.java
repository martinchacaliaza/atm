package com.example.app.models;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OperationCreditAccount {

	
	private String dni;
	
	private String codigoBancario;
	
	private String numeroCuenta;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fechaOperacion;
	
	private TypeOperation tipoOperacion;
	
	private Double montoPago;
	

	
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd/MM/yyyy")
	public Date fechaOperacion() {
		return fechaOperacion;
	}
}










