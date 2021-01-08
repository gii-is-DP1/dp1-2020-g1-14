package org.springframework.samples.petclinic.web;

import org.springframework.samples.petclinic.model.Producto;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class ProductoValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return Producto.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Producto producto = (Producto) target;
		String name = producto.getName();
		String al = producto.getAlergenos();
		Double price = producto.getPrecio();

	}

}
