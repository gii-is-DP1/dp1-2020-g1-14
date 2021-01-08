package org.springframework.samples.petclinic.web;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.samples.petclinic.model.Producto;
import org.springframework.samples.petclinic.service.ProductoService;
import org.springframework.stereotype.Component;
@Component
public class ProductoFormatter implements Formatter<Producto>{
	private final ProductoService productoService;

	@Autowired
	public ProductoFormatter(ProductoService productoService) {
		this.productoService = productoService;
	}

	@Override
	public String print(Producto producto, Locale locale) {
		return producto.getName();
	}

	@Override
	public Producto parse(String text, Locale locale) throws ParseException {
		Iterable<Producto> findProductos = this.productoService.findAll();
		for (Producto nombre : findProductos) {
			if (nombre.getName().equals(text)) {
				return nombre;
			}
		}
		throw new ParseException("name not found: " + text, 0);
	}
}

