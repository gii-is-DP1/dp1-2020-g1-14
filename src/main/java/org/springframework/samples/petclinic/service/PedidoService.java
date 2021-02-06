package org.springframework.samples.petclinic.service;

import java.util.Optional;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Cliente;
import org.springframework.samples.petclinic.model.LineaPedido;
import org.springframework.samples.petclinic.model.Oferta;
import org.springframework.samples.petclinic.model.Pedido;
import org.springframework.samples.petclinic.model.Producto;
import org.springframework.samples.petclinic.repository.PedidoRepository;
import org.springframework.samples.petclinic.service.exceptions.MinOrderPriceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ch.qos.logback.classic.Logger;

@Service
public class PedidoService {

	private static final Logger log = (Logger) LoggerFactory.getLogger(PedidoService.class);

	@Autowired
	private PedidoRepository pedidoRepo;
	@Autowired
	private LineaPedidoService lineaPedidoService;
	@Autowired
	private ClienteService clienteService;

	@Transactional
	public int pedidoCount() {

		log.info("Contando el número total de elementos");
		return (int) pedidoRepo.count();
	}

	@Transactional
	public Iterable<Pedido> findAll() {

		log.info("Buscando todos los elementos");
		return pedidoRepo.findAll();
	}

	@Transactional(readOnly = true)
	public Optional<Pedido> findPedidoById(int id) {

		log.info("Devolviendo elemento por su id");
		return pedidoRepo.findById(id);
	}
	
	@Transactional(readOnly = true)
	public Iterable<Pedido> findPedidosByRestauranteId(Integer restauranteId) {

		log.info("Devolviendo elemento por restaurante");
		return pedidoRepo.findPedidosByRestauranteId(restauranteId);
	}
	
	@Transactional(readOnly = true)
	public Iterable<Pedido> findPedidosByOfertaId(Integer OfertaId) {

		log.info("Devolviendo elemento por oferta");
		return pedidoRepo.findPedidosByOfertaId(OfertaId);
	}
	
	@Transactional(readOnly = true)
	public Iterable<Pedido> findPedidosByClienteId(Integer ClienteId) {

		log.info("Devolviendo elemento por cliente");
		return pedidoRepo.findPedidosByClienteId(ClienteId);
	}
	
	@Transactional(readOnly = true)
	public Iterable<Pedido> findPedidosByClienteIdYRestauranteId(Integer clienteId, Integer restauranteId) {

		log.info("Devolviendo elemento por cliente y restaurante");
		return pedidoRepo.findPedidosByClienteIdYRestauranteId(clienteId, restauranteId);
	}
	
	@Transactional(readOnly = true)
	public Iterable<Pedido> findPedidosByUsuarioIdYRestauranteId(String username, Integer restauranteId) {
		Cliente cliente = clienteService.findClienteByUsuario(username).get();
		log.info("Devolviendo elemento por cliente y restaurante");
		return pedidoRepo.findPedidosByClienteIdYRestauranteId(cliente.getId(), restauranteId);
	}

	@Transactional
	public void save(Pedido pedido)  {
		log.info("Guardando elemento");
		pedidoRepo.save(pedido);
	}

	@Transactional
	public void delete(Pedido pedido) {
		pedido.setCliente(null);
		pedido.setOferta(null);
		pedido.setRestaurante(null);
		Iterable<LineaPedido> lineaPedido = lineaPedidoService.findLineaPedidoByPedidoId(pedido.getId());
		for(LineaPedido i:lineaPedido) {
			lineaPedidoService.delete(i);
		}

		log.info("Eliminado un elemento");
		pedidoRepo.delete(pedido);
	}

	@Transactional
	public Double getTotalPrice(int id) /* throws MinOrderPriceException */ {
		Double total = pedidoRepo.getTotalPrice(id);
		/*
		 * if(total < 10) { throw new MinOrderPriceException(); }else
		 */
		log.info("Obteniendo el precio total de un pedido");
		return total;
	}

	@Transactional
	public Double getTotalPriceE(int id)  throws MinOrderPriceException  {
		Double total = pedidoRepo.getTotalPrice(id);

		if(total < 10) { throw new MinOrderPriceException(); }else

			log.info("Obteniendo el precio total de un pedido");
		return total;
	}

	@Transactional
	public Iterable<Producto> getAllProductos() {

		log.info("Obteniendo todos los productos");
		return pedidoRepo.getAllProductos();
	}

	@Transactional
	public Iterable<Oferta> getAllOfertas() {

		log.info("Obteniendo todas las ofertas");
		return pedidoRepo.getAllOfertas();
	}

}
