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
import org.springframework.samples.petclinic.model.Producto;
import org.springframework.samples.petclinic.service.ProductoService;

@ExtendWith(MockitoExtension.class)

public class ProductoFormatterTests {

	
	@Mock
	private ProductoService productoService;

	private ProductoFormatter productoFormatter;

	@BeforeEach
	void setup() {
		productoFormatter = new ProductoFormatter(productoService);
	}

	@Test
	void testPrint() {
		Producto producto = new Producto();
		producto.setName("Tarta");;
		String productoName = productoFormatter.print(producto, Locale.ENGLISH);
		assertEquals("Tarta", productoName);
	}

	
	@Test
	void shouldParse() throws ParseException {
		Mockito.when(productoService.findAll()).thenReturn(makeProductos());
		Producto producto = productoFormatter.parse("Tarta", Locale.ENGLISH);
		assertEquals("Tarta", producto.getName());
	}
	
	
	@Test
	void shouldThrowParseException() throws ParseException {
		Mockito.when(productoService.findAll()).thenReturn(makeProductos());
		Assertions.assertThrows(ParseException.class, () -> {
			productoFormatter.parse("Pescado", Locale.ENGLISH);
		});
	}
	
	
	/**
	 * Helper method to produce some sample products just for test purpose
	 * @return {@link Collection} of {@link Producto}
	 */
	private Collection<Producto> makeProductos() {
		Collection<Producto> productos = new ArrayList<>();
		productos.add(new Producto() {
			{
				setName("Tarta");
			}
		});
		productos.add(new Producto() {
			{
				setName("Ensalada");
			}
		});
		return productos;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
