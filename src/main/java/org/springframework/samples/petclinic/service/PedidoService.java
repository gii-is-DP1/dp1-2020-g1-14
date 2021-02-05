package org.springframework.samples.petclinic.service;

import java.util.Optional;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Estado;
import org.springframework.samples.petclinic.model.Oferta;
import org.springframework.samples.petclinic.model.Pedido;
import org.springframework.samples.petclinic.model.Producto;
import org.springframework.samples.petclinic.repository.PedidoRepository;
import org.springframework.samples.petclinic.service.exceptions.CantCancelOrderException;
import org.springframework.samples.petclinic.service.exceptions.MinOrderPriceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ch.qos.logback.classic.Logger;

@Service
public class PedidoService {

	private static final Logger log = (Logger) LoggerFactory.getLogger(PedidoService.class);

	@Autowired
	private PedidoRepository pedidoRepo;

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

		log.info("Devolviendo elemento por su usuario");
		return pedidoRepo.findPedidosByRestauranteId(restauranteId);
	}

	@Transactional
	public void save(Pedido pedido)  {
		log.info("Guardando elemento");
		pedidoRepo.save(pedido);
	}

	@Transactional
	public void delete(Pedido pedido) throws CantCancelOrderException {
		if (pedido.getEstado() == Estado.EN_REPARTO || pedido.getEstado() == Estado.RECIBIDO) {

			log.error("No se puede cancelar el pedido debido al estado en el que se encuentra");
			throw new CantCancelOrderException();
		} else {

			log.info("Eliminado un elemento");
			pedidoRepo.delete(pedido);
		}
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
