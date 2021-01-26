package com.tui.proof.ws.event;

import com.tui.proof.ws.dto.booking.*;
import com.tui.proof.ws.service.booking.BookingService;
import com.tui.proof.ws.service.event.EventService;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.function.Function;

@Log4j2
@Component
public class BookingEventHandler {

    private final EventService eventService;
    private final BookingService bookingService;

    public BookingEventHandler(EventService eventService,
                               BookingService bookingService) {
        this.eventService = eventService;
        this.bookingService = bookingService;
    }

    @EventListener
    public void handleCreateEvent(Event<BookingCreateDto> event) {
        log.info("handle booking create event {}", event.getId());
        handle(event, bookingService::create);
    }

    @EventListener
    public void handleAddFlightEvent(Event<BookingAddFlightDto> event) {
        log.info("handle add flight to booking event {}", event.getId());
        handle(event, bookingService::addFlight);
    }

    @EventListener
    public void handleDeleteFlightEvent(Event<BookingDeleteFlightDto> event) {
        log.info("handle delete flight to booking event {}", event.getId());
        handle(event, bookingService::deleteFlight);
    }

    @EventListener
    public void handleConfirmEvent(Event<BookingConfirmDto> event) {
        log.info("handle confirm booking event {}", event.getId());
        handle(event, bookingService::confirm);
    }

    @EventListener
    public void handleDeleteEvent(Event<BookingDeleteDto> event) {
        log.info("handle confirm booking event {}", event.getId());
        handle(event, bookingService::delete);
    }

    private <T> void handle(Event<T> event, Function<T, BookingDto> function) {
        try {
            bindUserInSecurityContext(event);
            eventService.create(event);
            final BookingDto booking = function.apply(event.getSource());
            final String location = "/bookings/" + booking.getId();
            eventService.setComplete(event.getId(), location);
        } catch (Exception e) {
            eventService.setFailed(event.getId(), e.getMessage());
        }
    }

    private <T> void bindUserInSecurityContext(Event<T> event) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(event.getUserName(), null, Set.of(new SimpleGrantedAuthority("ROLE_USER")));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
