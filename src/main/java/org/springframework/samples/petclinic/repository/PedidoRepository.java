package org.springframework.samples.petclinic.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Pedido;
import org.springframework.samples.petclinic.model.Producto;

public interface PedidoRepository extends CrudRepository<Pedido,Integer> {
	
	@Query("SELECT SUM(p.producto.precio * p.cantidad) FROM LineaPedido p WHERE pedido.id =:id")
	public Double getTotalPrice(@Param("id") int id);
	
	@Query("SELECT p FROM Producto p")
	public Iterable<Producto> getAllProductos();
}
