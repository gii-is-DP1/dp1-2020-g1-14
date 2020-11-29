package org.springframework.samples.petclinic.web;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.samples.petclinic.service.ClienteService.TelephoneNumberConstraint;

public class TelephoneNumberValidator implements ConstraintValidator<TelephoneNumberConstraint, String> {
	
	@Override    
	public void initialize(TelephoneNumberConstraint tlfNumber) {    }
	
	@Override    
	public boolean isValid(String tlfField, ConstraintValidatorContext cxt) {        
		return tlfField!= null && tlfField.matches("^([0-9]|7[1-9])[0-9]{8}$") ;    
	}

}
