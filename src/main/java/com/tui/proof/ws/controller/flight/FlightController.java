package com.tui.proof.ws.controller.flight;

import com.tui.proof.ws.dto.flight.FlightDto;
import com.tui.proof.ws.service.availability.AvailabilityService;
import com.tui.proof.ws.service.flight.FlightFilter;
import com.tui.proof.ws.service.flight.FlightService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/flights")
public class FlightController {

    public static final String AVAILABILITY_HEADER = "availability-id";

    private final FlightService service;
    private final AvailabilityService availabilityService;

    public FlightController(FlightService service,
                            AvailabilityService availabilityService) {
        this.service = service;
        this.availabilityService = availabilityService;
    }

    @GetMapping
    public ResponseEntity<Page<FlightDto>> get(FlightFilter filter, Pageable pageable) {
        return ResponseEntity.ok()
                .header(AVAILABILITY_HEADER, availabilityService.create().getId())
                .body(service.find(filter, pageable));
    }
}
