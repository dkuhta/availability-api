package com.tui.proof.ws.respository.availability;

import com.tui.proof.ws.model.availability.AvailabilityModel;
import com.tui.proof.ws.respository.InMemoryRepositoryImpl;
import org.springframework.stereotype.Component;

@Component
public class AvailabilityRepository extends InMemoryRepositoryImpl<AvailabilityModel> {

}
