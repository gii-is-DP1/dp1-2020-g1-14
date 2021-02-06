package org.springframework.samples.petclinic.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.LineaPedido;
import org.springframework.samples.petclinic.model.Producto;

public interface LineaPedidoRepository extends CrudRepository<LineaPedido,Integer>{
	@Query("SELECT p FROM Producto p")
	public List<Producto> getAllProductos();
	
	@Query("SELECT p.name FROM Producto p")
	public List<String> getAllProductosName();
	
	@Query("SELECT lp FROM LineaPedido lp WHERE lp.producto.id =:ProductoId") 
	public Iterable<LineaPedido> findLineaLedidoByProducto(@Param("ProductoId") Integer ProductoId);
	
	@Query("SELECT lp FROM LineaPedido lp WHERE lp.pedido.id =:PedidoId") 
	public Iterable<LineaPedido> findLineaLedidoByPedido(@Param("PedidoId") Integer PedidoId);
}
