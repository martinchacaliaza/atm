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
import reactor.core.publisher.Mono;

@Getter
@Setter

public class CurrentAccount {
	
	private String id;
	private String dni;
	private String numero_cuenta;
	private TypeCurrentAccount tipoProducto;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fecha_afiliacion;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fecha_caducidad;
	private double saldo;
	private String usuario;
	private String clave;
	private String codigo_bancario;
	
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd/MM/yyyy")
	public Date fecha_afiliacion() {
		return fecha_afiliacion;
	}
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd/MM/yyyy")
	public Date fecha_caducidad() {
		return fecha_caducidad;
	}
}










