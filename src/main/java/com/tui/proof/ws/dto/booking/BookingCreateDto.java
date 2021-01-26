package com.tui.proof.ws.dto.booking;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
public class BookingCreateDto implements Serializable {

    @NotBlank
    private String availabilityId;

    @NotNull
    private Holder holder;

    @NotEmpty
    private Set<String> flights;
}
