package com.tui.proof.ws.dto.flight;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tui.proof.ws.model.monetary.Monetary;
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
    @JsonFormat(pattern = "hh:mm")
    private LocalTime hours;
    private Monetary price;
}
