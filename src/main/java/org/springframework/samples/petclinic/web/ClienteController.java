package org.springframework.samples.petclinic.web;

import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Cliente;
import org.springframework.samples.petclinic.service.ClienteService;
import org.springframework.samples.petclinic.service.RestauranteService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.samples.petclinic.service.exceptions.CantBeAMemberException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import ch.qos.logback.classic.Logger;

@Controller
@RequestMapping("/clientes")
public class ClienteController {
	
	private static final Logger log = (Logger) LoggerFactory.getLogger(ClienteController.class);
	
    @Autowired
    private ClienteService clienteService;
	@Autowired
	private UserService userService;
	@Autowired
	private RestauranteService restauranteService;
	@Autowired
	private ClienteValidator clienteValidator;
	
	@InitBinder("cliente")
	public void initGerenteBinder(WebDataBinder dataBinder) {
		dataBinder.setValidator(clienteValidator);
		log.info("inicializando DataBinder");
	}
    
	//Obtener la lista de clientes.
    @GetMapping()
    public String listadoClientes(ModelMap modelMap) {
        String vista="clientes/listadoClientes";
        Iterable<Cliente> clientes = clienteService.findAll();
        modelMap.addAttribute("clientes",clientes);
        
        log.info("Mostrando lista de clientes");
        
        return vista;
    }
    
    //Crear nuevo cliente.
    @GetMapping(path="/new")
    public String crearCliente(ModelMap modelMap) {
    	String view="clientes/editCliente";
    	modelMap.addAttribute("cliente", new Cliente());
    	
    	log.info("Operación para añadir cliente en ejecucion");
    	
    	return view;
    }
    
    
    //Guardar el cliente.
    @PostMapping(path="/save")
    public String salvarCliente(@Valid Cliente cliente, BindingResult result, ModelMap modelMap) {
    	String view="clientes/listadoClientes";
    	if(result.hasErrors()) {
    		modelMap.addAttribute("cliente", cliente);
    		
    		log.error("Los datos introducidos no cumplen ciertas condiciones, revisar los campos");
    		
    		return "clientes/editCliente";
    	}else {
    		clienteService.save(cliente);
    		modelMap.addAttribute("message","client succesfully saved!");
    		view="registrado";
    		
    		log.info("Cliente creado con éxito");
    	}
    	return view;
    }
    
    
    //Elimina un cliente
    @GetMapping(path="delete/{clienteId}")
    public String borrarCliente(@PathVariable("clienteId") int clienteId, ModelMap modelMap) {
    String view="clientes/listadoCliente";
    Optional<Cliente> cliente=clienteService.findClienteById(clienteId);
    if(cliente.isPresent()) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	if(auth.isAuthenticated() && auth.getPrincipal() != "anonymousUser") {
    		String autoridad = userService.findUser(auth.getName()).get().getAuthorities().getAuthority(); //get logged in username
    		if(autoridad.equals("admin")) {
    			view=listadoClientes(modelMap);
    		} else {
    			view="welcome";
    		}
    	}
    	
    	clienteService.delete(cliente.get());
        modelMap.addAttribute("message","client succesfully deleted!");
        
        log.info("Cliente eliminado con éxito");
        
    }else {
        modelMap.addAttribute("message","client not found!");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	if(auth.isAuthenticated() && auth.getPrincipal() != "anonymousUser") {
    		String autoridad = userService.findUser(auth.getName()).get().getAuthorities().getAuthority(); //get logged in username
    		if(autoridad.equals("admin")) {
    			view=listadoClientes(modelMap);
    		} else {
    			view="welcome";
    		}
    	}
        log.error("No se ha encontrado el cliente para eliminar");
    }
    return view;
}
    //Obtener el listado de socios.
    @GetMapping(path="/socios")
    public String listadoSocios(ModelMap modelMap) {
    	String vista="clientes/listadoSocios";
    	Iterable<Cliente> clientes = clienteService.findSocios();
    	modelMap.addAttribute("clientes", clientes);
    	
    	log.info("Mostrando lista de socios");
    	
    	return vista;
    	
    }
   
    //Convertir cliente a socio.
    @GetMapping(path="/upgrade") 
    public String upgradeCliente(ModelMap modelMap)  {
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String name = auth.getName(); //get logged in username
	    Cliente cliente = clienteService.findClienteByUsuario(name).get();
    	try {
			clienteService.checkSocio(cliente);
		} catch (CantBeAMemberException e) {
			// TODO Auto-generated catch block
			modelMap.addAttribute("message","No cumple las condiciones necesarias o ya es socio");
			
			log.error("No se puede convertir a socio: no se cumplen las condiciones o ya es socio");
			
			return "restaurantes/listadoRestaurantes";
		}
        modelMap.addAttribute("cliente", cliente);
        modelMap.addAttribute("message", "Convertido a socio con éxito");
        modelMap.addAttribute("restaurantes", restauranteService.findAll());
        log.info("Convertido a socio satisfactoriamente");
        
        return "restaurantes/listadoRestaurantes";
  
    }
    
    //ingresa dinero a su monedero.
    @GetMapping(path="/ingresa/{ingreso}")
    public String processIngresaMonedero(ModelMap modelMap, @PathVariable("ingreso") int ingreso) {
    	String view="/welcome";
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String name = auth.getName(); //get logged in username
	    Cliente cliente = clienteService.findClienteByUsuario(name).get();
	    cliente.setMonedero(cliente.getMonedero()+ingreso);
	    clienteService.update(cliente);
	    modelMap.addAttribute("message", "Se han ingresado " + ingreso + " correctamente en su monedero");
    	return view;
    }
}