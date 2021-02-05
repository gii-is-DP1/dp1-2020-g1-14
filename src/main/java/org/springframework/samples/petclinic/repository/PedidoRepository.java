package org.springframework.samples.petclinic.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Oferta;
import org.springframework.samples.petclinic.model.Pedido;
import org.springframework.samples.petclinic.model.Producto;

public interface PedidoRepository extends CrudRepository<Pedido,Integer> {
	
	@Query("SELECT SUM(p.producto.precio * p.cantidad) FROM LineaPedido p WHERE pedido.id =:id")
	public Double getTotalPrice(@Param("id") int id);
	
	@Query("SELECT p FROM Producto p")
	public Iterable<Producto> getAllProductos();
	
	@Query("SELECT o FROM Oferta o")
	public Iterable<Oferta> getAllOfertas();
	
	@Query("SELECT p FROM Pedido p WHERE p.restaurante.id =:restauranteId") 
	public Iterable<Pedido> findPedidosByRestauranteId(@Param("restauranteId") Integer restauranteId);
	
	@Query("SELECT p FROM Pedido p WHERE p.oferta.id =:ofertaId") 
	public Iterable<Pedido> findPedidosByOfertaId(@Param("ofertaId") Integer ofertaId);
}
