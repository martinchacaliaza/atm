package com.example.app.service.impl;

import java.awt.datatransfer.FlavorMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.app.exception.RequestException;
import com.example.app.models.Atm;
import com.example.app.models.CurrentAccount;
import com.example.app.models.OperationCreditAccount;
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
	public Flux<Atm> findAllAtm() {
		return atmDao.findAll();

	}

	@Override
	public Mono<Atm> findByIdAtm(String id) {
		return atmDao.findById(id);

	}

	// 1- Deposito

	@Override
	public Mono<Atm> saveAtmDeposito(Atm atm) {
		Mono<Atm> oper = Mono.just(atm);
		return oper.flatMap(a -> {
			typeOperationAtm type = new typeOperationAtm();
			type.setIdTipo("1");
			type.setDescripcion("Deposito");
			atm.setTipoOperacion(type);

			Mono<CurrentAccount> produc = WebClient.builder()
					.baseUrl("http://" + valorget + "/producto_bancario/api/ProductoBancario/")
					.defaultHeader(HttpHeaders.CONTENT_TYPE).build().get()
					.uri("/numero_cuenta/" + atm.getCuenta_origen() + "/" + atm.getCodigo_bancario_origen()).retrieve()
					.bodyToMono(CurrentAccount.class).log();
			return produc.flatMap(prod -> {

				if (!prod.getCodigo_bancario().equalsIgnoreCase(atm.getCodigo_bancario_origen())) {
					throw new RequestException("El numeor de cuenta no "
							+ " pertenece a entidad bancaria ingresada");
				} else {
					if (!prod.getCodigo_bancario().equalsIgnoreCase(atm.getCodigo_atm_banco())) {

						if (atm.getCodigo_atm_banco().equalsIgnoreCase("123")) {
							atm.setComision_interbancaria(1.50);

						} else if (atm.getCodigo_atm_banco().equalsIgnoreCase("456")) {
							atm.setComision_interbancaria(2.50);

						}
					}
				}
				OperationCurrentAccount coa = new OperationCurrentAccount();
				coa.setDni(a.getDni());
				coa.setCodigo_bancario_origen(a.getCodigo_bancario_origen());
				coa.setCuenta_origen(a.getCuenta_origen());

				coa.setCodigo_bancario_destino("---");
				coa.setCuenta_destino("---");

				coa.setFechaOperacion(a.getFechaOperacion());
				coa.setMontoPago(a.getSoles());

				Mono<OperationCurrentAccount> oper1 = WebClient.builder()
						.baseUrl("http://" + valorget + "/servicio-productos/api/OperCuentasCorrientes/")
						.defaultHeader(HttpHeaders.CONTENT_TYPE).build().post().uri("/deposito/")
						.contentType(MediaType.APPLICATION_JSON).body(BodyInserters.fromValue(coa)).retrieve()
						.bodyToMono(OperationCurrentAccount.class).log();
				return oper1.flatMap(b -> {
					if (b.getDni().equals("")) {
						Mono.empty();

					}
					return atmDao.save(atm);
				});
			});
		});
		// });
	}

	// 2- Retiro
	@Override
	public Mono<Atm> saveAtmRetiro(Atm atm) {
		Mono<Atm> oper = Mono.just(atm);
		return oper.flatMap(a -> {
			typeOperationAtm type = new typeOperationAtm();
			type.setIdTipo("2");
			type.setDescripcion("Retiro");
			atm.setTipoOperacion(type);

			Mono<CurrentAccount> produc = WebClient.builder()
					.baseUrl("http://" + valorget + "/producto_bancario/api/ProductoBancario/")
					.defaultHeader(HttpHeaders.CONTENT_TYPE).build().get()
					.uri("/numero_cuenta/" + atm.getCuenta_origen() + "/" + atm.getCodigo_bancario_origen()).retrieve()
					.bodyToMono(CurrentAccount.class).log();
			return produc.flatMap(prod -> {

				if (!prod.getCodigo_bancario().equalsIgnoreCase(atm.getCodigo_bancario_origen())) {
					throw new RequestException("El numeor de cuenta no "
							+ " pertenece a entidad bancaria ingresada");
				} else {
					if (!prod.getCodigo_bancario().equalsIgnoreCase(atm.getCodigo_atm_banco())) {

						if (atm.getCodigo_atm_banco().equalsIgnoreCase("123")) {
							atm.setComision_interbancaria(1.50);

						} else if (atm.getCodigo_atm_banco().equalsIgnoreCase("456")) {
							atm.setComision_interbancaria(2.50);

						}
					}
				}
				OperationCurrentAccount coa = new OperationCurrentAccount();
				coa.setDni(a.getDni());
				coa.setCodigo_bancario_origen(a.getCodigo_bancario_origen());
				coa.setCuenta_origen(a.getCuenta_origen());
				coa.setCodigo_bancario_destino("---");
				coa.setCuenta_destino("---");
				coa.setFechaOperacion(a.getFechaOperacion());
				coa.setMontoPago(a.getSoles());
				coa.setComision(a.getComision_interbancaria());
				Mono<OperationCurrentAccount> oper1 = WebClient.builder()
						.baseUrl("http://" + valorget + "/servicio-productos/api/OperCuentasCorrientes/")
						.defaultHeader(HttpHeaders.CONTENT_TYPE).build().post().uri("/retiro/")
						.contentType(MediaType.APPLICATION_JSON).body(BodyInserters.fromValue(coa)).retrieve()
						.bodyToMono(OperationCurrentAccount.class).log();
				return oper1.flatMap(b -> {
					if (b.getDni().equals("")) {
						Mono.empty();

					}
					return atmDao.save(atm);

				});
			});
		});
	}

	// 2- tranferencias de cuenta a cuenta
	@Override
	public Mono<Atm> saveAtmTranferencias(Atm atm) {
		Mono<Atm> oper = Mono.just(atm);
		return oper.flatMap(a -> {
			typeOperationAtm type = new typeOperationAtm();
			type.setIdTipo("3");
			type.setDescripcion("Tanferencia a otras cuentas");
			atm.setTipoOperacion(type);

			Mono<CurrentAccount> produc = WebClient.builder()
					.baseUrl("http://" + valorget + "/producto_bancario/api/ProductoBancario/")
					.defaultHeader(HttpHeaders.CONTENT_TYPE).build().get()
					.uri("/numero_cuenta/" + atm.getCuenta_origen() + "/" + atm.getCodigo_bancario_origen()).retrieve()
					.bodyToMono(CurrentAccount.class).log();
			return produc.flatMap(prod -> {
				if (!prod.getCodigo_bancario().equalsIgnoreCase(atm.getCodigo_bancario_origen())) {

					throw new RequestException("El numeor de cuenta no "
							+ " pertenece a entidad bancaria ingresada");
				} else {
					if (!prod.getCodigo_bancario().equalsIgnoreCase(atm.getCodigo_atm_banco())) {

						if (atm.getCodigo_atm_banco().equalsIgnoreCase("123")) {
							atm.setComision_interbancaria(1.50);

						} else if (atm.getCodigo_atm_banco().equalsIgnoreCase("456")) {
							atm.setComision_interbancaria(2.50);

						}
					}
				}
				OperationCurrentAccount coa = new OperationCurrentAccount();
				coa.setDni(a.getDni());
				coa.setCodigo_bancario_origen(a.getCodigo_bancario_origen());
				coa.setCuenta_origen(a.getCuenta_origen());
				coa.setCodigo_bancario_destino(a.getCodigo_bancario_destino());
				coa.setCuenta_destino(a.getCuenta_destino());
				coa.setFechaOperacion(a.getFechaOperacion());
				coa.setMontoPago(a.getSoles());
				coa.setComision(a.getComision_interbancaria());
				Mono<OperationCurrentAccount> oper1 = WebClient.builder()
						.baseUrl("http://" + valorget + "/servicio-productos/api/OperCuentasCorrientes/")
						.defaultHeader(HttpHeaders.CONTENT_TYPE).build().post().uri("/cuenta_a_cuenta/")
						.contentType(MediaType.APPLICATION_JSON).body(BodyInserters.fromValue(coa)).retrieve()
						.bodyToMono(OperationCurrentAccount.class).log();
				return oper1.flatMap(b -> {
					if (b.getDni().equals("")) {
						Mono.empty();
					}
					return atmDao.save(atm);
				});
			});
		});
	}
	
	// 3- Pago de tarjeta credito 
		@Override
		public Mono<Atm> saveAtmPagoCredito(Atm atm) {
			Mono<Atm> oper = Mono.just(atm);
			return oper.flatMap(a -> {
				typeOperationAtm type = new typeOperationAtm();
				type.setIdTipo("4");
				type.setDescripcion("Pago tarjeta de credito");
				atm.setTipoOperacion(type);

				Mono<CurrentAccount> produc = WebClient.builder()
						.baseUrl("http://" + valorget + "/productos_creditos/api/ProductoCredito/")
						.defaultHeader(HttpHeaders.CONTENT_TYPE).build().get()
						.uri("/numero_cuenta/" + atm.getCuenta_origen() + "/" +
						atm.getCodigo_bancario_origen()).retrieve()
						.bodyToMono(CurrentAccount.class).log();
				return produc.flatMap(prod -> {
					if (!prod.getCodigo_bancario().equalsIgnoreCase(atm.getCodigo_bancario_origen())) {

						throw new RequestException("El numero de cuenta no "
								+ " pertenece a entidad bancaria ingresada");
					} else {
						if (!prod.getCodigo_bancario().equalsIgnoreCase(atm.getCodigo_atm_banco())) {

							if (atm.getCodigo_atm_banco().equalsIgnoreCase("123")) {
								atm.setComision_interbancaria(1.50);

							} else if (atm.getCodigo_atm_banco().equalsIgnoreCase("456")) {
								atm.setComision_interbancaria(2.50);

							}
						}
					}
					OperationCreditAccount coa = new OperationCreditAccount();
					coa.setDni(a.getDni());
					coa.setCodigo_bancario(a.getCodigo_bancario_origen());
					coa.setNumero_cuenta(a.getCuenta_origen());			
					coa.setFechaOperacion(a.getFechaOperacion());
					coa.setMontoPago(a.getSoles());			
					
					Mono<OperationCreditAccount> oper1 = WebClient.builder()
							.baseUrl("http://" + valorget + "/operaciones_cuentas_creditos/"
									+ "api/OperCuentasCreditos/")
							.defaultHeader(HttpHeaders.CONTENT_TYPE).build().post().uri("/pago/")
							.contentType(MediaType.APPLICATION_JSON).body(BodyInserters.fromValue(coa)).retrieve()
							.bodyToMono(OperationCreditAccount.class).log();
					return oper1.flatMap(b -> {
						if (b.getDni().equals("")) {
							Mono.empty();
						}
						return atmDao.save(atm);
					});
				});
			});
		}

	@Override
	public Mono<Void> deleteAtm(Atm bank) {
		return atmDao.delete(bank);
	}

	@Override
	public Mono<Atm> saveAtm(Atm atm) {
		// TODO Auto-generated method stub
		return null;
	}

}
