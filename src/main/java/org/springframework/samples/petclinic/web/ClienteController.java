package org.springframework.samples.petclinic.web;

import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Cliente;
import org.springframework.samples.petclinic.model.Oferta;
import org.springframework.samples.petclinic.model.Restaurante;
import org.springframework.samples.petclinic.service.ClienteService;
import org.springframework.samples.petclinic.service.exceptions.CantBeAMemberException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ch.qos.logback.classic.Logger;

@Controller
@RequestMapping("/clientes")
public class ClienteController {
	
	private static final Logger log = (Logger) LoggerFactory.getLogger(ClienteController.class);
	
    @Autowired
    private ClienteService clienteService;
    
    @GetMapping()
    public String listadoClientes(ModelMap modelMap) {
        String vista="clientes/listadoClientes";
        Iterable<Cliente> clientes = clienteService.findAll();
        modelMap.addAttribute("clientes",clientes);
        
        log.info("Mostrando lista de clientes");
        
        return vista;
    }
    
    @GetMapping(path="/new")
    public String crearCliente(ModelMap modelMap) {
    	String view="clientes/editCliente";
    	modelMap.addAttribute("cliente", new Cliente());
    	
    	log.info("Operación para añadir cliente en ejecucion");
    	
    	return view;
    }
    
    @PostMapping(path="/save")
    public String salvarCliente(@Valid Cliente cliente, BindingResult result, ModelMap modelMap) {
    	String view="clientes/listadoClientes";
    	if(result.hasErrors()) {
    		modelMap.addAttribute("cliente", cliente);
    		
    		log.error("Los datos introducidos no cumplen ciertas condiciones, revisar los campos");
    		
    		return "clientes/editCliente";
    	}else {
    		clienteService.save(cliente);
    		modelMap.addAttribute("message","Event succesfully saved!");
    		view=listadoClientes(modelMap);
    		
    		log.info("Cliente creado con éxito");
    	}
    	return view;
    }
    
    @PostMapping(path="/save/{clienteId}")
    public String salvarCliente(@Valid Cliente cliente, BindingResult result, ModelMap modelMap,
    							@RequestParam(value = "version", required = false) Integer version, @PathVariable("clienteId") int clienteId) {
    	String view="clientes/listadoClientes";
    	if(result.hasErrors()) {
    		modelMap.addAttribute("cliente", cliente);
    		
    		log.error("Los datos introducidos no cumplen ciertas condiciones, revisar los campos");
    		
    		return "clientes/editCliente";
    	}else {
    		Cliente clienteToUpdate = clienteService.findClienteById(clienteId).get();
    		if(clienteToUpdate.getVersion() != version) {
    			log.error("Las versiones de cliente no coinciden: clienteToUpdate version " + clienteToUpdate.getVersion() + " cliente version "+version);
    			modelMap.addAttribute("message", "Ha ocurrido un error inesperado por favor intentalo de nuevo");
    			return listadoClientes(modelMap);
    		}
    		clienteService.update(cliente);
    		modelMap.addAttribute("message","Event succesfully saved!");
    		view=listadoClientes(modelMap);
    		
    		log.info("Cliente creado con éxito");
    	}
    	return view;
    }
    
    @GetMapping(path="delete/{clienteId}")
    public String borrarCliente(@PathVariable("clienteId") int clienteId, ModelMap modelMap) {
    String view="clientes/listadoCliente";
    Optional<Cliente> cliente=clienteService.findClienteById(clienteId);
    if(cliente.isPresent()) {
        clienteService.delete(cliente.get());
        modelMap.addAttribute("message","Event succesfully deleted!");
        view=listadoClientes(modelMap);
        
        log.info("Cliente eliminado con éxito");
        
    }else {
        modelMap.addAttribute("message","Event not found!");
        view=listadoClientes(modelMap);
        
        log.error("No se ha encontrado el cliente para eliminar");
    }
    return view;
}
    @GetMapping(path="/socios")
    public String listadoSocios(ModelMap modelMap) {
    	String vista="clientes/listadoSocios";
    	Iterable<Cliente> clientes = clienteService.findSocios();
    	modelMap.addAttribute("clientes", clientes);
    	
    	log.info("Mostrando lista de socios");
    	
    	return vista;
    	
    }
   
    @GetMapping(path="/upgrade/{clienteId}") 
    public String upgradeCliente(@PathVariable("clienteId") int clienteId, ModelMap modelMap)  {
    	String view = "clientes/listadoClientes";
    	Optional<Cliente> cliente = clienteService.findClienteById(clienteId);
    	try {
			clienteService.checkSocio(cliente.get());
		} catch (CantBeAMemberException e) {
			// TODO Auto-generated catch block
			modelMap.addAttribute("message","No cumple las condiciones necesarias o ya es socio");
			view=listadoClientes(modelMap);
			
			log.error("No se puede convertir a socio: no se cumplen las condiciones o ya es socio");
			
			return view;
		}
        modelMap.addAttribute("cliente", cliente);
        modelMap.addAttribute("message", "Convertido a socio con éxito");
        view= listadoClientes(modelMap);
        
        log.info("Convertido a socio satisfactoriamente");
        
        return view;
  
    }
}