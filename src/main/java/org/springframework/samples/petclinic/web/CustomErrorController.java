package org.springframework.samples.petclinic.web;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController{

	@Override
	public String getErrorPath() {
		return "/error";
	}

	@RequestMapping("/error")
	public String handleError(HttpServletRequest request, ModelMap modelMap) {
		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
		if(status !=null) {
			int statusCode = Integer.parseInt(status.toString());
			if(statusCode == HttpStatus.NOT_FOUND.value()) {
				modelMap.addAttribute("message", "Error 404, La pagina que busca no se ha encontrado");
				//return "error-404";
			}else if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
				modelMap.addAttribute("message", "Error 500, Ha habido un error en el servidor");
				//return "error-500";
			}else if(statusCode == HttpStatus.FORBIDDEN.value()) {
				modelMap.addAttribute("message", "Error 403, No tiene permiso para entrar a la página solicitada");
				//return "error-404";
			}else {
				modelMap.addAttribute("message", "Ha habido una excepción inesperada");
			}
		}
		return "exception";
	}
}
