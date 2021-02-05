package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Producto;


public interface ProductoRepository extends CrudRepository<Producto, Integer>{

	@Query("SELECT producto FROM Producto producto WHERE producto.precio=:precio")
	public Collection<Producto> findByPrecio(@Param("precio") Double precio);
	
	@Query("SELECT DISTINCT producto FROM Producto producto WHERE producto.name=:name")
	public Collection<Producto> findByName(@Param("name") String name);
	
	@Query("SELECT p FROM Producto p WHERE p.restaurante.id =:restauranteId") 
	public Iterable<Producto> findProductosByRestauranteId(@Param("restauranteId") Integer restauranteId);
 }
