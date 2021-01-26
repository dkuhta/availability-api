package com.tui.proof.ws.dto.booking;

import com.tui.proof.ws.model.booking.BookingStatus;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
public class BookingDto extends BookingCreateDto implements Serializable {

    private String id;
    private LocalDateTime dateCreated;
    private BookingStatus status;
    private Holder holder;
    private Set<String> flights;
}
