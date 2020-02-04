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
	private String codigo_bancario_origen;
	private String cuenta_origen;
	private String codigo_bancario_destino;
	private String cuenta_destino;
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










