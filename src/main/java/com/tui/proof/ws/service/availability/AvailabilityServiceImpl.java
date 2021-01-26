package com.tui.proof.ws.service.availability;

import com.tui.proof.ws.model.availability.AvailabilityModel;
import com.tui.proof.ws.respository.availability.AvailabilityRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static java.time.LocalDateTime.now;


@Service
public class AvailabilityServiceImpl implements AvailabilityService {

    @Value("${availability.expiration.minutes}")
    private int availableMinutes;

    private final AvailabilityRepository repository;

    public AvailabilityServiceImpl(AvailabilityRepository repository) {
        this.repository = repository;
    }

    @Override
    public AvailabilityModel create() {
        AvailabilityModel availabilityModel = new AvailabilityModel();
        availabilityModel.setId(UUID.randomUUID().toString());
        availabilityModel.setDateCreated(now());
        availabilityModel.setUserName(SecurityContextHolder.getContext().getAuthentication().getName());
        repository.save(availabilityModel);
        return availabilityModel;
    }

    @Override
    public boolean isExpired(String id) {
        return repository.find(id)
                .getDateCreated()
                .plusMinutes(availableMinutes)
                .isBefore(now());
    }
}
