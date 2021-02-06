package org.springframework.samples.petclinic.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.LineaPedido;
import org.springframework.samples.petclinic.model.Producto;
import org.springframework.samples.petclinic.repository.LineaPedidoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ch.qos.logback.classic.Logger;

@Service
public class LineaPedidoService {

	private static final Logger log = (Logger) LoggerFactory.getLogger(ClienteService.class);

	@Autowired
	private LineaPedidoRepository lineaPedidoRepo;
	
	@Transactional
	public int pedidoCount() {

		log.info("Contando el número total de elementos");
		return (int) lineaPedidoRepo.count();
	}

	@Transactional(readOnly = true)
	public Optional<LineaPedido> findLineaPedidoById(int id) {

		log.info("Devolviendo elemento por su id");
		return lineaPedidoRepo.findById(id);

	}
	
	@Transactional(readOnly = true)
	public Iterable<LineaPedido> findLineaPedidoByProductoId(Integer productoId) {

		log.info("Devolviendo elemento por producto");
		return lineaPedidoRepo.findLineaPedidoByProducto(productoId);
	}
	
	@Transactional(readOnly = true)
	public Iterable<LineaPedido> findLineaPedidoByPedidoId(Integer pedidoId) {

		log.info("Devolviendo elemento por pedido");
		return lineaPedidoRepo.findLineaPedidoByPedido(pedidoId);
	}

	@Transactional
	public Iterable<LineaPedido> findAll() {

		log.info("Buscando todos los elementos");
		return lineaPedidoRepo.findAll();
	}

	@Transactional
	public void save(LineaPedido lp) {

		log.info("Guardando elemento");
		lineaPedidoRepo.save(lp);
	}

	@Transactional
	public void delete(LineaPedido lp) {
		lp.setProducto(null);
		lp.setPedido(null);
		
		log.info("Eliminado un elemento");
		lineaPedidoRepo.delete(lp);
	}

	public List<Producto> getAllProductos() {

		log.info("Obteniendo todos los productos asociados");
		return lineaPedidoRepo.getAllProductos();
	}

	public List<String> getAllProductosName() {

		log.info("Obteniendo todos los nombres de los productos asociados");
		return lineaPedidoRepo.getAllProductosName();
	}
}
