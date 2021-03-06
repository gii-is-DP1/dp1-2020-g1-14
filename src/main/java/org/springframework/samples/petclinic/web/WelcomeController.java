package org.springframework.samples.petclinic.web;

import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Cliente;
import org.springframework.samples.petclinic.model.Person;
import org.springframework.samples.petclinic.model.Restaurante;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.service.ClienteService;
import org.springframework.samples.petclinic.service.GerenteService;
import org.springframework.samples.petclinic.service.RestauranteService;
import org.springframework.samples.petclinic.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Controller
public class WelcomeController {
	
	@Autowired
	private RestauranteService restauranteService;
	@Autowired
	private UserService userService;
	@Autowired
	private ClienteService clienteService;
	
	  @GetMapping({"/","/welcome"})
	  public String welcome(Map<String, Object> model) {	    
		  List<Person> persons = new ArrayList<Person>();
		  Person p1=new Person();
		  p1.setFirstName("Jose Luis");
		  p1.setLastName("Alonso");
		  persons.add(p1);
		  Person p2=new Person();
		  p2.setFirstName("Jacobo");
		  p2.setLastName("García");
		  persons.add(p2);
		  Person p3=new Person();
		  p3.setFirstName("Candelaria");
		  p3.setLastName("Jordano");
		  persons.add(p3);
		  Person p4=new Person();
		  p4.setFirstName("Jaime");
		  p4.setLastName("Ramos");
		  persons.add(p4);
		  Person p5=new Person();
		  p5.setFirstName("Hegoa");
		  p5.setLastName("Ría");
		  persons.add(p5);
		  Person p6=new Person();
		  p6.setFirstName("Badayco");
		  p6.setLastName("Rijo");
		  persons.add(p6);
		  
		  Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		  if(auth.isAuthenticated() && auth.getPrincipal() != "anonymousUser") {
			  String autoridad = userService.findUser(auth.getName()).get().getAuthorities().getAuthority(); //get logged in username
			  if(autoridad.equals("gerente")) {
				String name = auth.getName();
			  	Restaurante restaurante = restauranteService.findRestauranteByGerenteUser(name).get();
			  	model.put("restaurante", restaurante);
			  } else if(autoridad.equals("cliente")) {
					String name = auth.getName();
				  	Cliente cliente = clienteService.findClienteByUsuario(name).get();
				  	model.put("cliente", cliente);
			  }
		  }
		  
		  model.put("Persons", persons);
		  model.put("Title", "proyect");
		  model.put("Group", "G1-14");
	    return "welcome";
	  }
	  
}
