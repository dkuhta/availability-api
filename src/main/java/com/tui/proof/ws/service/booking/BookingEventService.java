package com.tui.proof.ws.service.booking;

import com.tui.proof.ws.dto.booking.BookingCreateDto;
import com.tui.proof.ws.event.Event;

public interface BookingEventService {

    Event<?> create(BookingCreateDto dto);

    Event<?> addFlight(String id, String flightNumber);

    Event<?> deleteFlight(String id, String flightNumber);

    Event<?> confirm(String id);

    Event<?> delete(String id);
}
