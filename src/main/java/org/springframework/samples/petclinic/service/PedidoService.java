package org.springframework.samples.petclinic.service;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Estado;
import org.springframework.samples.petclinic.model.Pedido;
import org.springframework.samples.petclinic.model.Producto;
import org.springframework.samples.petclinic.repository.PedidoRepository;
import org.springframework.samples.petclinic.service.exceptions.CantCancelOrderException;
import org.springframework.samples.petclinic.service.exceptions.MinOrderPriceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PedidoService {
	@Autowired
	private PedidoRepository pedidoRepo;
	
	@Transactional
	public int pedidoCount() {
		return (int) pedidoRepo.count();
	}
	
	@Transactional
	public Iterable<Pedido> findAll() {
		return pedidoRepo.findAll();
	}
	@Transactional(readOnly=true)
	public Optional<Pedido> findPedidoById(int id) {
		return pedidoRepo.findById(id);
	}
	
	public void save(Pedido pedido) {
		pedidoRepo.save(pedido);
	}
	
	public void delete(Pedido pedido) throws CantCancelOrderException {
		if(pedido.getEstado() == Estado.EN_REPARTO || pedido.getEstado() == Estado.RECIBIDO) {
			throw new CantCancelOrderException();
		}else 
		pedidoRepo.delete(pedido);
	}
	
	public Double getTotalPrice(int id) /*throws MinOrderPriceException*/{
		Double total =pedidoRepo.getTotalPrice(id);
		/*if(total < 10) {
			throw new MinOrderPriceException();
		}else*/
			return total;
	}
	
	public Iterable<Producto> getAllProductos() {
		return pedidoRepo.getAllProductos();
	}

}
