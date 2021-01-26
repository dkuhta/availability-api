package com.tui.proof.ws.dto.flight;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class FlightDto implements Serializable {

    private String availabilityId;
    private String flightNumber;
    private String company;
    private LocalDate date;
    private LocalTime hours;
    private Long price;
}
