package org.springframework.samples.petclinic.web;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.samples.petclinic.model.Restaurante;
import org.springframework.samples.petclinic.service.RestauranteService;
import org.springframework.stereotype.Component;

@Component
public class RestauranteFormatter implements Formatter<Restaurante> {
	private final RestauranteService reService;

	@Autowired
	public RestauranteFormatter(RestauranteService restauranteService) {
		this.reService = restauranteService;
	}

	@Override
	public String print(Restaurante restaurante, Locale locale) {
		return restaurante.getName();
	}

	@Override
	public Restaurante parse(String text, Locale locale) throws ParseException {
		Iterable<Restaurante> findRestaurantes = this.reService.findAll();
		for (Restaurante nombre : findRestaurantes) {
			if (nombre.getName().equals(text)) {
				return nombre;
			}
		}
		throw new ParseException("name not found: " + text, 0);
	}
}
