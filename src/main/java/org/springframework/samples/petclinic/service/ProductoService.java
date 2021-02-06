package org.springframework.samples.petclinic.service;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Ingrediente;
import org.springframework.samples.petclinic.model.LineaPedido;
import org.springframework.samples.petclinic.model.Producto;
import org.springframework.samples.petclinic.repository.ProductoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ch.qos.logback.classic.Logger;

@Service
public class ProductoService {
	@Autowired
	private ProductoRepository productoRepo;
	@Autowired
	private LineaPedidoService lineaPedidoService;
	@Autowired
	private IngredienteService ingredienteService;
	
	private static final Logger log = (Logger) LoggerFactory.getLogger(ProductoService.class);
	
	@Transactional
	public int productoCount() {
		
		log.info("Contando el número total de elementos");
		return (int) productoRepo.count();
	}
	@Transactional
	public Iterable<Producto> findAll() {
		
		log.info("Buscando todos los elementos");
		return productoRepo.findAll();
		
	}
	@Transactional(readOnly=true)
	public Optional<Producto> findProductoById(int id) {
		
		log.info("Devolviendo elemento por su id");
		return productoRepo.findById(id);
	}
	
	@Transactional(readOnly = true)
	public Iterable<Producto> findProductosByRestauranteId(Integer restauranteId) {

		log.info("Obtener productos por restaurante");
		return productoRepo.findProductosByRestauranteId(restauranteId);
	}
	
	public void save(Producto producto) /*throws WrongDataProductosException*/ {
//		if((producto.getName().length() < 3 || producto.getName().length() > 50) || !producto.getAlergenos().matches("^[a-zA-Z,.!? ]*$")||producto.getPrecio() <= 0) {
//			log.error("No se cumplen las condiciones al crear el producto");
//			throw new WrongDataProductosException();
//		}else
		
		log.info("Guardando elemento");
		productoRepo.save(producto);
	}
	public void delete(Producto producto) {
		producto.setRestaurante(null);
		Set<Ingrediente> ingredientes = producto.getIngredientes();
		for(Ingrediente i:ingredientes) {
			Set<Producto> productos = i.getProductos();
			productos.remove(producto);
			i.setProductos(productos);
			ingredienteService.save(i);
		}
		Iterable<LineaPedido> lineaPedido = lineaPedidoService.findLineaPedidoByProductoId(producto.getId());
		for(LineaPedido i:lineaPedido) {
			lineaPedidoService.delete(i);
		}
		
		log.info("Eliminado un elemento");
		productoRepo.delete(producto);
	}
	
	@Transactional(readOnly = true)
	public Collection<Producto> findProductoByPrecio(Double precio) {
		
		log.info("Buscando productos por su precio");
		return productoRepo.findByPrecio(precio);
	}
	public Collection<Producto> findProductoByName(String name) {
		
		log.info("Buscando productos por su nombre");
		return productoRepo.findByName(name);
	}
}	

