package com.tui.proof.ws.service.booking;

import com.tui.proof.ws.dto.booking.*;

public interface BookingService {

    BookingDto create(BookingCreateDto dto);

    BookingDto get(String id);

    BookingDto addFlight(BookingAddFlightDto dto);

    BookingDto deleteFlight(BookingDeleteFlightDto dto);

    BookingDto confirm(BookingConfirmDto dto);

    BookingDto delete(BookingDeleteDto dto);
}
