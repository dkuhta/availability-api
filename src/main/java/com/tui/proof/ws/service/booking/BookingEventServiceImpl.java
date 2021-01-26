package com.tui.proof.ws.service.booking;

import com.tui.proof.ws.dto.booking.*;
import com.tui.proof.ws.event.Event;
import com.tui.proof.ws.service.event.EventService;
import com.tui.proof.ws.utils.SecurityUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import static com.tui.proof.ws.utils.SecurityUtils.getCurrentUsername;

@Log4j2
@Service
public class BookingEventServiceImpl implements BookingEventService {

    private final EventService eventService;
    private final ApplicationEventPublisher eventPublisher;

    public BookingEventServiceImpl(EventService eventService,
                                   ApplicationEventPublisher eventPublisher) {
        this.eventService = eventService;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public Event<?> create(BookingCreateDto dto) {
        final String eventId = eventService.generateId();
        log.info("publish booking create event {}", eventId);
        Event<BookingCreateDto> event = new Event<>(eventId, getCurrentUsername(), dto);
        eventPublisher.publishEvent(event);
        return event;
    }

    @Override
    public Event<?> addFlight(String id, String flightNumber) {
        final String eventId = eventService.generateId();
        log.info("publish add flight to booking event {}", eventId);

        BookingAddFlightDto dto = new BookingAddFlightDto();
        dto.setBookingId(id);
        dto.setFlightNumber(flightNumber);

        Event<BookingAddFlightDto> event = new Event<>(eventId, getCurrentUsername(), dto);
        eventPublisher.publishEvent(event);

        return event;
    }

    @Override
    public Event<?> deleteFlight(String id, String flightNumber) {
        final String eventId = eventService.generateId();
        log.info("publish delete flight to booking event {}", eventId);

        BookingDeleteFlightDto dto = new BookingDeleteFlightDto();
        dto.setBookingId(id);
        dto.setFlightNumber(flightNumber);

        Event<BookingDeleteFlightDto> event = new Event<>(eventId, getCurrentUsername(), dto);
        eventPublisher.publishEvent(event);

        return event;
    }

    @Override
    public Event<?> confirm(String id) {
        final String eventId = eventService.generateId();
        log.info("publish confirm booking event {}", eventId);

        BookingConfirmDto dto = new BookingConfirmDto();
        dto.setBookingId(id);

        Event<BookingConfirmDto> event = new Event<>(eventId, getCurrentUsername(), dto);
        eventPublisher.publishEvent(event);

        return event;
    }

    @Override
    public Event<?> delete(String id) {
        final String eventId = eventService.generateId();
        log.info("publish delete booking event {}", eventId);

        BookingDeleteDto dto = new BookingDeleteDto();
        dto.setBookingId(id);

        Event<BookingDeleteDto> event = new Event<>(eventId, getCurrentUsername(), dto);
        eventPublisher.publishEvent(event);

        return event;
    }
}
