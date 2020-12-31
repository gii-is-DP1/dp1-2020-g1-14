package org.springframework.samples.petclinic.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.LineaPedido;
import org.springframework.samples.petclinic.model.Producto;

public interface LineaPedidoRepository extends CrudRepository<LineaPedido,Integer>{
	@Query("SELECT p FROM Producto p")
	public List<Producto> getAllProductos();
	
	@Query("SELECT p.name FROM Producto p")
	public List<String> getAllProductosName();
}
