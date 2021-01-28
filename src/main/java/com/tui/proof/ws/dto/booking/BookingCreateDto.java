package com.tui.proof.ws.dto.booking;

import com.tui.proof.ws.validator.availability.ValidAvailabilityId;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
public class BookingCreateDto implements Serializable {

    @ValidAvailabilityId(message = "availabilityId is incorrect")
    private String availabilityId;

    @NotNull
    private Holder holder;

    @NotEmpty
    private Set<String> flights;
}
