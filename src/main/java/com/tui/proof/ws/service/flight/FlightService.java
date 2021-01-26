package com.tui.proof.ws.service.flight;

import com.tui.proof.ws.dto.flight.FlightDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FlightService {

    Page<FlightDto> find(FlightFilter filter, Pageable pageable);
}
