package com.example.app.models;

import java.util.Date;


import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OperationCurrentAccount {

	private String dni;
	private String codigoBancarioOrigen;
	private String cuentaOrigen;
	private String codigoBancarioDestino;
	private String cuentaDestino;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fechaOperacion;
	private TypeOperation tipoOperacion;
	private double montoPago;
	private Double comision = 0.0;
		
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd/MM/yyyy")
	public Date fechaOperacion() {
		return fechaOperacion;
	}
}










