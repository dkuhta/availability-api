package com.tui.proof.ws.service.flight;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class FlightFilter {

    private String airportOrigin;
    private String airportDestination;
    private LocalDate from;
    private LocalDate to;
    private short infants;
    private short children;
    private short adults;
}
