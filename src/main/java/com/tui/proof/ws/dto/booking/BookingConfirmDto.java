package com.tui.proof.ws.dto.booking;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Getter
@Setter
public class BookingConfirmDto implements Serializable {

    @NotBlank
    private String bookingId;
}
