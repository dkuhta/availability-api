package com.tui.proof.ws.service.flight;

import com.tui.proof.ws.dto.flight.FlightDto;
import com.tui.proof.ws.utils.RandomUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class FlightServiceImpl implements FlightService {

    @Override
    public Page<FlightDto> find(FlightFilter filter, Pageable pageable) {
        return RandomUtils.randomFlightPage(pageable, pageable.getPageSize() * 3L);
    }
}
