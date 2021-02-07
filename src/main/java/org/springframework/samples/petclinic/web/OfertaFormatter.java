package org.springframework.samples.petclinic.web;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.samples.petclinic.model.Oferta;
import org.springframework.samples.petclinic.service.OfertaService;
import org.springframework.stereotype.Component;

@Component
public class OfertaFormatter implements Formatter<Oferta>{
	private final OfertaService ofertaService;

	/*Formatter de oferta para que se muestren correctamente las ofertas en el campo de select,
	a la hora de seleccionar una en la creación de un pedido.*/
	@Autowired
	public OfertaFormatter(OfertaService ofertaService) {
		this.ofertaService = ofertaService;
	}

	@Override
	public String print(Oferta oferta, Locale locale) {
		return oferta.getDescripcion();
	}

	@Override
	public Oferta parse(String text, Locale locale) throws ParseException {
		Iterable<Oferta> findOfertas = this.ofertaService.findAll();
		for (Oferta d : findOfertas) {
			if (d.getDescripcion().equals(text)) {
				return d;
			}
		}
		throw new ParseException("name not found: " + text, 0);
	}
}
