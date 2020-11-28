package org.springframework.samples.petclinic.web;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Cliente;
import org.springframework.samples.petclinic.service.ClienteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/clientes")
public class ClienteController {
    @Autowired
    private ClienteService clienteService;
    @GetMapping()
    public String listadoClientes(ModelMap modelMap) {
        String vista="clientes/listadoClientes";
        Iterable<Cliente> clientes = clienteService.findAll();
        modelMap.addAttribute("clientes",clientes);
        return vista;
    }
    @GetMapping(path="delete/{clienteId}")
    public String borrarCliente(@PathVariable("clienteId") int clienteId, ModelMap modelMap) {
    String view="clientes/listadoCliente";
    Optional<Cliente> cliente=clienteService.findClienteById(clienteId);
    if(cliente.isPresent()) {
        clienteService.delete(cliente.get());
        modelMap.addAttribute("message","Event succesfully deleted!");
        view=listadoClientes(modelMap);
    }else {
        modelMap.addAttribute("message","Event not found!");
        view=listadoClientes(modelMap);
    }
    return view;
}
}