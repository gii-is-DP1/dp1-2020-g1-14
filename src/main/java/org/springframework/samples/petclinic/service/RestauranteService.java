package org.springframework.samples.petclinic.service;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Gerente;
import org.springframework.samples.petclinic.model.Ingrediente;
import org.springframework.samples.petclinic.model.Oferta;
import org.springframework.samples.petclinic.model.Pedido;
import org.springframework.samples.petclinic.model.Producto;
import org.springframework.samples.petclinic.model.Reclamacion;
import org.springframework.samples.petclinic.model.Reserva;
import org.springframework.samples.petclinic.model.Restaurante;
import org.springframework.samples.petclinic.repository.RestauranteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RestauranteService {

	@Autowired
	private RestauranteRepository RestaurantRepo;
	@Autowired
	private OfertaService ofertaService;
	@Autowired
	private PedidoService pedidoService;
	@Autowired
	private ReservaService reservaService;
	@Autowired
	private ProductoService productoService;
	@Autowired
	private ReclamacionService reclamacionService;
	@Autowired
	private IngredienteService ingredienteService;
	@Autowired
	private GerenteService gerenteService;
	
	@Transactional
	public int Restaurantscount() {
		return (int) RestaurantRepo.count();
	}
	
	@Transactional
	public Iterable<Restaurante> findAll() {
		log.info("obtención de todos los restaurantes");
		return RestaurantRepo.findAll();
	}
	
	@Transactional(readOnly=true)
	public Optional<Restaurante>findRestauranteById(int id){
		log.info("obtención de restaurante concreto");
		return RestaurantRepo.findById(id);
	}
	
    @Transactional(readOnly = true)
	public Optional<Restaurante> findRestauranteByGerenteUser(String gerenteUser) {
    	log.info("obtener restaurante por usuario de gerente");
		return RestaurantRepo.findRestauranteByGerenteUser(gerenteUser);
	}
	
	@Transactional
	public void save(Restaurante restaurante) {
		RestaurantRepo.save(restaurante);
		log.info("guardar restaurante");
	}
	
	@Transactional
	public void delete(Restaurante restaurante) {
		Gerente gerente = restaurante.getGerente();
		gerenteService.delete(gerente);
		
		Iterable<Oferta> ofertas = ofertaService.findOfertasByRestauranteId(restaurante.getId());
		for(Oferta o:ofertas) {
			ofertaService.delete(o);
		}
		Iterable<Pedido> pedidos = pedidoService.findPedidosByRestauranteId(restaurante.getId());
		for(Pedido p:pedidos) {
			pedidoService.delete(p);
		}
		Iterable<Reserva> reservas = reservaService.findReservasByRestauranteId(restaurante.getId());
		for(Reserva r:reservas) {
			reservaService.delete(r);
		}
		Iterable<Producto> productos = productoService.findProductosByRestauranteId(restaurante.getId());
		for(Producto p:productos) {
			productoService.delete(p);
		}
		Iterable<Reclamacion> reclamaciones = reclamacionService.findReclamacionByRestauranteId(restaurante.getId());
		for(Reclamacion r:reclamaciones) {
			reclamacionService.delete(r);
		}
		Iterable<Ingrediente> ingredientes = ingredienteService.findIngredientesByRestauranteId(restaurante.getId());
		for(Ingrediente i:ingredientes) {
			ingredienteService.delete(i);
		}
		
		RestaurantRepo.delete(restaurante);
		log.info("restaurante elimiinado");
	}

}
