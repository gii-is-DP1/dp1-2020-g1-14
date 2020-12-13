package org.springframework.samples.petclinic.web;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.BeanWrapperImpl;
import org.springframework.samples.petclinic.service.RestauranteService.AforoResConstraint;

public class AforoResValidator implements ConstraintValidator<AforoResConstraint, Object>{

	private String min;
	private String max;
	
	@Override
	public void initialize(AforoResConstraint aforoRes) {

	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		Integer minValue = (Integer) new BeanWrapperImpl(value).getPropertyValue(min);
		Integer maxValue = (Integer) new BeanWrapperImpl(value).getPropertyValue(max);
		return minValue < maxValue;
	}

}
