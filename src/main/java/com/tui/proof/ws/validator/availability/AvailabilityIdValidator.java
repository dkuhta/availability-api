package com.tui.proof.ws.validator.availability;

import com.tui.proof.ws.service.availability.AvailabilityService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AvailabilityIdValidator implements ConstraintValidator<ValidAvailabilityId, String> {

    @Autowired
    private AvailabilityService availabilityService;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        try {
            return !availabilityService.isExpired(value);
        } catch (Exception e) {
            return false;
        }
    }
}
