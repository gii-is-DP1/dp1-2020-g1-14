package org.springframework.samples.petclinic.service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Optional;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Cliente;
import org.springframework.samples.petclinic.model.Pedido;
import org.springframework.samples.petclinic.model.Reserva;
import org.springframework.samples.petclinic.repository.ClienteRepository;
import org.springframework.samples.petclinic.service.exceptions.CantBeAMemberException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ch.qos.logback.classic.Logger;

@Service
public class ClienteService {
	@Autowired
	private ClienteRepository clienteRepo;
	@Autowired
	private AuthoritiesService authoritiesService;
	@Autowired
	private UserService userService;
	@Autowired
	private ReservaService reservaService;
	@Autowired
	private PedidoService pedidoService;

	private static final Logger log = (Logger) LoggerFactory.getLogger(ClienteService.class);

	@Transactional
	public int clienteCount() {

		log.info("Contando el número total de elementos");
		return (int) clienteRepo.count();
	}

	@Transactional
	public Iterable<Cliente> findAll() {

		log.info("Buscando todos los elementos");
		return clienteRepo.findAll();

	}

	@Transactional
	public void save(Cliente cliente) {
		// crear usuario
		log.info("Creando usuario");
		userService.saveUser(cliente.getUser());
		
		//crear cliente
		log.info("Guardando elemento");
		clienteRepo.save(cliente);
		
		// crear authorities
		log.info("Creando authorities");
		authoritiesService.saveAuthorities(cliente.getUser().getUsername(), "cliente");
	}
	
	@Transactional
	public void update(Cliente cliente) {
		
		//crear cliente
		log.info("Guardando elemento");
		clienteRepo.save(cliente);
	}

	@Transactional(readOnly = true)
	public Optional<Cliente> findClienteById(int id) {

		log.info("Devolviendo elemento por su id");
		return clienteRepo.findById(id);
	}
	
	@Transactional(readOnly = true)
	public Optional<Cliente> findClienteByUsuario(String username) {

		log.info("Devolviendo elemento por su usuario");
		return clienteRepo.findClienteByUsuario(username);
	}

	@Transactional
	public void delete(Cliente cliente) {
		Iterable<Reserva> reservas = reservaService.findReservasByClienteId(cliente.getId());
		for(Reserva r:reservas) {
			reservaService.delete(r);
		}
		Iterable<Pedido> pedidos = pedidoService.findPedidosByClienteId(cliente.getId());
		for(Pedido p:pedidos) {
			pedidoService.delete(p);
		}
		userService.delete(cliente.getUser());
		
		log.info("Eliminado un elemento");
		clienteRepo.delete(cliente);
	}

	public Cliente checkSocio(Cliente cliente) throws CantBeAMemberException {
		YearMonth startMonth = YearMonth.of(cliente.getUser().getrDate().getYear(),
				cliente.getUser().getrDate().getMonthValue());
		Integer numPed = cliente.getNumPedidos();
		Boolean socio = cliente.getEsSocio();
		if (monthDiff(startMonth) < 6 || numPed < 10 || socio == true) {

			log.error("Error: asegurese de que se cumplen las condiciones necesarias.");
			throw new CantBeAMemberException();
		} else
			cliente.setEsSocio(true);
			clienteRepo.save(cliente);

		log.info("Se establece el cliente como socio");
		return cliente;
	}

	public Integer monthDiff(YearMonth startMonth) {
		YearMonth endMonth = YearMonth.of(LocalDate.now().getYear(), LocalDate.now().getMonth());
		long noOfMonths = ChronoUnit.MONTHS.between(startMonth, endMonth) + 1;

		log.info("Diferencia entre fechas calculada");
		return (int) noOfMonths;
	}

	/*
	 * @Documented
	 * 
	 * @Documented
	 * 
	 * @Constraint(validatedBy= TelephoneNumberValidator.class)
	 * 
	 * @Target( { ElementType.METHOD, ElementType.FIELD})
	 * 
	 * @Retention(RetentionPolicy.RUNTIME) public @interface
	 * TelephoneNumberConstraint{ String message() default
	 * "El número de teléfono no es válido. Debe introducir un número de teléfono válido p.ej: '954678970' o en caso de teléfono móvil: '657908756'."
	 * ; Class<?>[] groups() default {}; Class<? extends Payload>[] payload()
	 * default {};
	 * 
	 * }
	 */
	public Collection<Cliente> findSocios() {

		log.info("Buscando socios entre todos los clientes");
		return clienteRepo.findSocios();
	}

}