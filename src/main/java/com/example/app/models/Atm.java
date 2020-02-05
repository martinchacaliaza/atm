package com.example.app.models;


import java.util.Date;

import javax.validation.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter

@Document(collection ="CajeroBancario")
public class Atm {
	

	@Id
	@NotEmpty
	private String id;
	@NotEmpty
	private String codigo_atm_banco;
	@NotEmpty
	private String dni;
	@NotEmpty
	private String codigoBancarioOrigen;
	@NotEmpty
	private String cuentaOrigen;
	@NotEmpty
	private String codigoBancarioDestino;
	@NotEmpty
	private String cuentaDestino;
	@NotEmpty
	private Date fechaOperacion;
	@NotEmpty
	private typeOperationAtm tipoOperacion;
	@NotEmpty
	private Double comision_interbancaria=0.0;
	@NotEmpty
	private Double soles;

	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd/MM/yyyy")
	public Date fechaOperacion() {
		return fechaOperacion;
	}
}










