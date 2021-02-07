package org.springframework.samples.petclinic.web;

import static org.junit.jupiter.api.Assertions.assertEquals;


import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.samples.petclinic.model.Oferta;
import org.springframework.samples.petclinic.service.OfertaService;


@ExtendWith(MockitoExtension.class)
public class OfertaFormatterTests {

	
	@Mock
	private OfertaService ofertaService;

	private OfertaFormatter ofertaFormatter;

	@BeforeEach
	void setup() {
		ofertaFormatter = new OfertaFormatter(ofertaService);
	}
	
	@Test
	void testPrint() {
		Oferta oferta = new Oferta();
		oferta.setDescripcion("Descuento 3 euros");;
		String ofertaDescripcion = ofertaFormatter.print(oferta, Locale.ENGLISH);
		assertEquals("Descuento 3 euros", ofertaDescripcion);
	}
	
	
	@Test
	void shouldParse() throws ParseException {
		Mockito.when(ofertaService.findAll()).thenReturn(makeOfertas());
		Oferta oferta = ofertaFormatter.parse("Descuento 3 euros", Locale.ENGLISH);
		assertEquals("Descuento 3 euros", oferta.getDescripcion());
	}
	
	
	@Test
	void shouldThrowParseException() throws ParseException {
		Mockito.when(ofertaService.findAll()).thenReturn(makeOfertas());
		Assertions.assertThrows(ParseException.class, () -> {
			ofertaFormatter.parse("Descuento de 2 euros", Locale.ENGLISH);
		});
	}
	
	
	/**
	 * Helper method to produce some sample offer just for test purpose
	 * @return {@link Collection} of {@link Oferta}
	 */
	private Collection<Oferta> makeOfertas() {
		Collection<Oferta> ofertas = new ArrayList<>();
		ofertas.add(new Oferta() {
			{
				setDescripcion("Descuento 3 euros");
			}
		});
		ofertas.add(new Oferta() {
			{
				setDescripcion("Descuento 5 euros");
			}
		});
		return ofertas;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
