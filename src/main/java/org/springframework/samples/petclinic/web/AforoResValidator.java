package org.springframework.samples.petclinic.web;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.hibernate.validator.internal.util.annotation.ConstraintAnnotationDescriptor;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.samples.petclinic.service.RestauranteService.AforoResConstraint;

import com.sun.xml.bind.v2.schemagen.xmlschema.Annotation;

public class AforoResValidator implements ConstraintValidator<AforoResConstraint, Object>{

	private String afRes;
	private String afMax;
	
	@Override
	public void initialize(AforoResConstraint aforoRes) {
		this.afMax = aforoRes.afMax();
		this.afRes = aforoRes.afRes();

	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		Integer resValue = (Integer) new BeanWrapperImpl(value).getPropertyValue(afRes);
		Integer maxValue = (Integer) new BeanWrapperImpl(value).getPropertyValue(afMax);
		return resValue <= maxValue;
	}

}
