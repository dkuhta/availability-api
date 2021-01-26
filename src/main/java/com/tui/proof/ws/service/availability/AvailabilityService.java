package com.tui.proof.ws.service.availability;

import com.tui.proof.ws.model.availability.AvailabilityModel;

public interface AvailabilityService {

    AvailabilityModel create();

    boolean isExpired(String id);
}
