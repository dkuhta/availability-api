package com.tui.proof.ws.model.booking;

import com.tui.proof.ws.dto.booking.Holder;
import com.tui.proof.ws.model.BaseModel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

@NoArgsConstructor
@Data
public class BookingModel extends BaseModel {

    @NonNull
    private String availabilityId;

    @NotNull
    private Holder holder;

    @NotEmpty
    private Set<String> flights;

    @NonNull
    private BookingStatus status = BookingStatus.CREATED;
}
