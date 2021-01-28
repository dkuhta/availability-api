package com.tui.proof.ws.service.booking;

import com.tui.proof.ws.dto.booking.*;
import com.tui.proof.ws.exception.LogicalException;
import com.tui.proof.ws.model.booking.BookingModel;
import com.tui.proof.ws.model.booking.BookingStatus;
import com.tui.proof.ws.respository.booking.BookingRepository;
import com.tui.proof.ws.service.availability.AvailabilityService;
import com.tui.proof.ws.utils.SecurityUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.UUID;

import static java.time.LocalDateTime.now;

@Log4j2
@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository repository;
    private final AvailabilityService availabilityService;

    public BookingServiceImpl(BookingRepository repository, AvailabilityService availabilityService) {
        this.repository = repository;
        this.availabilityService = availabilityService;
    }

    @Override
    public BookingDto create(BookingCreateDto dto) {
        validateAvailability(dto.getAvailabilityId());

        BookingModel model = new BookingModel();
        model.setId(UUID.randomUUID().toString());
        model.setDateCreated(now());
        model.setUserName(SecurityUtils.getCurrentUsername());
        model.setAvailabilityId(dto.getAvailabilityId());
        model.setHolder(dto.getHolder());
        model.setFlights(dto.getFlights());

        repository.save(model);

        return map(model);
    }

    private BookingDto map(BookingModel model) {
        BookingDto dto = new BookingDto();
        dto.setId(model.getId());
        dto.setDateCreated(model.getDateCreated());
        dto.setStatus(model.getStatus());
        dto.setHolder(model.getHolder());
        dto.setFlights(model.getFlights());
        return dto;
    }

    @Override
    public BookingDto get(String id) {
        return map(repository.find(id));
    }

    @Override
    public BookingDto addFlight(BookingAddFlightDto dto) {
        BookingModel booking = getAndValidate(dto.getBookingId());
        if (CollectionUtils.isEmpty(booking.getFlights())) {
            booking.setFlights(new HashSet<>());
        }
        booking.getFlights().add(dto.getFlightNumber());
        repository.save(booking);
        return map(booking);
    }

    private BookingModel getAndValidate(String id) {
        BookingModel booking = repository.find(id);
        validateAvailability(booking);
        return booking;
    }

    private void validateAvailability(BookingModel booking) {
        validateAvailability(booking.getAvailabilityId());
    }

    private void validateAvailability(String availabilityId) {
        if (availabilityService.isExpired(availabilityId)) {
            throw new LogicalException("Availability has been expired");
        }
    }

    @Override
    public BookingDto deleteFlight(BookingDeleteFlightDto dto) {
        BookingModel booking = getAndValidate(dto.getBookingId());
        booking.getFlights().remove(dto.getFlightNumber());
        repository.save(booking);
        return map(booking);
    }

    @Override
    public BookingDto confirm(BookingConfirmDto dto) {
        BookingModel booking = getAndValidate(dto.getBookingId());
        booking.setStatus(BookingStatus.CONFIRMED);
        repository.save(booking);
        return map(booking);
    }

    @Override
    public BookingDto delete(BookingDeleteDto dto) {
        BookingModel booking = getAndValidate(dto.getBookingId());
        booking.setStatus(BookingStatus.DELETED);
        repository.save(booking);
        return map(booking);
    }
}
