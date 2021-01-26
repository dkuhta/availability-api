package com.tui.proof.ws.controller.booking;

import com.tui.proof.ws.dto.booking.BookingCreateDto;
import com.tui.proof.ws.dto.booking.BookingDto;
import com.tui.proof.ws.event.Event;
import com.tui.proof.ws.service.booking.BookingEventService;
import com.tui.proof.ws.service.booking.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.HttpHeaders.LOCATION;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    private final BookingService bookingService;
    private final BookingEventService bookingEventService;

    public BookingController(BookingService bookingService,
                             BookingEventService bookingEventService) {
        this.bookingService = bookingService;
        this.bookingEventService = bookingEventService;
    }

    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody BookingCreateDto dto) {
        final Event<?> event = bookingEventService.create(dto);
        return buildEventResponse(event);
    }

    @GetMapping("/{id}")
    public BookingDto get(@PathVariable("id") String id) {
        return bookingService.get(id);
    }

    @PutMapping("/{id}/flights/{flightNumber}")
    public ResponseEntity<Void> addFlight(@PathVariable("id") String id, @PathVariable("flightNumber") String flightNumber) {
        final Event<?> event = bookingEventService.addFlight(id, flightNumber);
        return buildEventResponse(event);
    }

    @DeleteMapping("/{id}/flights/{flightNumber}")
    public ResponseEntity<Void> deleteFlight(@PathVariable("id") String id, @PathVariable("flightNumber") String flightNumber) {
        final Event<?> event = bookingEventService.deleteFlight(id, flightNumber);
        return buildEventResponse(event);
    }

    @PostMapping("/{id}/confirm")
    public ResponseEntity<Void> confirm(@PathVariable("id") String id) {
        final Event<?> event = bookingEventService.confirm(id);
        return buildEventResponse(event);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id) {
        final Event<?> event = bookingEventService.delete(id);
        return buildEventResponse(event);
    }

    private ResponseEntity<Void> buildEventResponse(Event<?> event) {
        return ResponseEntity.accepted()
                .header(LOCATION, "/events/" + event.getId())
                .build();
    }
}
