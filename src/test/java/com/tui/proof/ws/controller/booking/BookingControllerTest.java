package com.tui.proof.ws.controller.booking;

import com.tui.proof.ws.controller.BaseControllerTest;
import com.tui.proof.ws.controller.exception.ErrorInfo;
import com.tui.proof.ws.controller.exception.ServerError;
import com.tui.proof.ws.dto.booking.BookingCreateDto;
import com.tui.proof.ws.dto.booking.Holder;
import com.tui.proof.ws.model.availability.AvailabilityModel;
import com.tui.proof.ws.model.booking.BookingModel;
import com.tui.proof.ws.model.booking.BookingStatus;
import com.tui.proof.ws.model.event.EventModel;
import com.tui.proof.ws.model.event.EventStatus;
import com.tui.proof.ws.respository.availability.AvailabilityRepository;
import com.tui.proof.ws.respository.booking.BookingRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static com.tui.proof.ws.utils.MvcTestUtils.parseResponse;
import static com.tui.proof.ws.utils.MvcTestUtils.requestBody;
import static com.tui.proof.ws.utils.RandomUtils.*;
import static java.lang.Thread.sleep;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.LOCATION;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class BookingControllerTest extends BaseControllerTest {

    @MockBean
    private BookingRepository bookingRepository;

    @MockBean
    private AvailabilityRepository availabilityRepository;

    @Test
    void create_and_expect_accepted() throws Exception {
        AvailabilityModel availability = createAvailability();
        String availabilityId = availability.getId();

        BookingCreateDto bookingRequest = createBookingRequest(availabilityId);

        Mockito.when(availabilityRepository.find(availabilityId)).thenReturn(availability);

        MvcResult bookingCreateResult = getMvc().perform(post("/bookings")
                .content(requestBody(bookingRequest))
                .contentType(APPLICATION_JSON)
                .header(AUTHORIZATION, jwtTokenWithTestUser()))
                .andExpect(status().isAccepted())
                .andExpect(header().exists(LOCATION))
                .andReturn();

        validateAsyncResult(bookingCreateResult);
    }

    private BookingCreateDto createBookingRequest(String availabilityId) {
        Holder holder = new Holder();
        holder.setFirstName(randomString());
        holder.setLastName(randomString());
        holder.setAddress(randomString());
        holder.setPostalCode(randomNumeric(10));
        holder.setCountry(randomAlphabetic(10));
        holder.setEmail(randomEmail());
        holder.setTelephones(Collections.singleton(randomNumeric(10)));

        BookingCreateDto createDto = new BookingCreateDto();
        createDto.setAvailabilityId(availabilityId);
        createDto.setHolder(holder);
        createDto.setFlights(Set.of(randomString()));
        return createDto;
    }

    @Test
    void create_and_expect_400() throws Exception {
        final MvcResult mvcResult = getMvc().perform(post("/bookings")
                .content("{}")
                .contentType(APPLICATION_JSON)
                .header(AUTHORIZATION, jwtTokenWithTestUser()))
                .andExpect(status().is4xxClientError())
                .andReturn();

        ErrorInfo errorInfo = parseResponse(mvcResult, ErrorInfo.class);

        Assertions.assertEquals(ServerError.INPUT_INVALID.getStatusCode(), errorInfo.getStatusCode());
        Assertions.assertNotNull(errorInfo.getMessage());
    }

    @Test
    void get_details_and_expect_ok() throws Exception {
        AvailabilityModel availability = createAvailability();
        String availabilityId = availability.getId();

        BookingModel booking = createBooking(availabilityId);
        String id = booking.getId();

        Mockito.when(bookingRepository.find(id)).thenReturn(booking);

        getMvc().perform(get("/bookings/{id}", id)
                .header(AUTHORIZATION, jwtTokenWithTestUser()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(booking.getId()))
                .andExpect(jsonPath("$.status").value(booking.getStatus().toString()))
                .andExpect(jsonPath("$.dateCreated").isNotEmpty());

        //TODO validate other fields in response
    }

    @Test
    void add_flight_and_expect_accepted() throws Exception {
        AvailabilityModel availability = createAvailability();
        String availabilityId = availability.getId();

        BookingModel booking = createBooking(availabilityId);
        String id = booking.getId();

        String flightNumber = randomId();

        Mockito.when(bookingRepository.find(id)).thenReturn(booking);
        Mockito.when(availabilityRepository.find(booking.getAvailabilityId())).thenReturn(availability);

        final MvcResult confirmResult = getMvc().perform(put("/bookings/{id}/flights/{flightNumber}", id, flightNumber)
                .header(AUTHORIZATION, jwtTokenWithTestUser()))
                .andExpect(status().isAccepted())
                .andReturn();

        validateAsyncResult(confirmResult);
    }

    @Test
    void delete_flight_and_expect_accepted() throws Exception {
        AvailabilityModel availability = createAvailability();
        String availabilityId = availability.getId();

        BookingModel booking = createBooking(availabilityId);
        String id = booking.getId();

        String flightNumber = booking.getFlights().iterator().next();

        Mockito.when(bookingRepository.find(id)).thenReturn(booking);
        Mockito.when(availabilityRepository.find(booking.getAvailabilityId())).thenReturn(availability);

        final MvcResult confirmResult = getMvc().perform(delete("/bookings/{id}/flights/{flightNumber}", id, flightNumber)
                .header(AUTHORIZATION, jwtTokenWithTestUser()))
                .andExpect(status().isAccepted())
                .andReturn();

        validateAsyncResult(confirmResult);
    }

    @Test
    void confirm_and_expect_accepted() throws Exception {
        AvailabilityModel availability = createAvailability();
        String availabilityId = availability.getId();

        BookingModel expected = createBooking(availabilityId);
        String id = expected.getId();

        Mockito.when(bookingRepository.find(id)).thenReturn(expected);
        Mockito.when(availabilityRepository.find(expected.getAvailabilityId())).thenReturn(availability);

        final MvcResult confirmResult = getMvc().perform(post("/bookings/{id}/confirm", id)
                .header(AUTHORIZATION, jwtTokenWithTestUser()))
                .andExpect(status().isAccepted())
                .andReturn();

        validateAsyncResult(confirmResult);
    }

    @Test
    void delete_and_expect_accepted() throws Exception {
        AvailabilityModel availability = createAvailability();
        String availabilityId = availability.getId();

        BookingModel booking = createBooking(availabilityId);
        String id = booking.getId();

        Mockito.when(bookingRepository.find(id)).thenReturn(booking);
        Mockito.when(availabilityRepository.find(booking.getAvailabilityId())).thenReturn(availability);

        final MvcResult mvcResult = getMvc().perform(delete("/bookings/{id}", id)
                .header(AUTHORIZATION, jwtTokenWithTestUser()))
                .andExpect(status().isAccepted())
                .andReturn();

        validateAsyncResult(mvcResult);

    }

    private AvailabilityModel createAvailability() {
        AvailabilityModel availabilityModel = new AvailabilityModel();
        availabilityModel.setId(randomId());
        availabilityModel.setDateCreated(LocalDateTime.now());
        availabilityModel.setUserName(getUsername());
        return availabilityModel;
    }

    private BookingModel createBooking(String availabilityId) {
        Set<String> flights = new HashSet<>();
        flights.add(randomId());
        flights.add(randomId());

        BookingModel model = new BookingModel();
        model.setId(randomId());
        model.setDateCreated(LocalDateTime.now());
        model.setStatus(BookingStatus.CREATED);
        model.setUserName(getUsername());
        model.setAvailabilityId(availabilityId);
        model.setFlights(flights);
        return model;
    }

    private void validateAsyncResult(MvcResult mvcResult) throws Exception {
        final String location = mvcResult.getResponse().getHeader(LOCATION);
        Assertions.assertNotNull(location);

        int seconds = 10;
        for (int i = 0; i < seconds; i++) {
            final MvcResult eventResult = getMvc().perform(get(location)
                    .header(AUTHORIZATION, jwtTokenWithTestUser()))
                    .andReturn();

            if (eventResult.getResponse().getStatus() == HttpStatus.OK.value()) {
                final EventModel event = parseResponse(eventResult, EventModel.class);
                if (EventStatus.FAILED.equals(event.getStatus())) {
                    throw new RuntimeException(String.format("event status %s", event.getStatus()));
                }
            }

            if (eventResult.getResponse().getStatus() == HttpStatus.SEE_OTHER.value()) {
                final String bookingLocation = eventResult.getResponse().getHeader(LOCATION);
                Assertions.assertNotNull(bookingLocation);
                return;
            }

            sleep(1000);
        }

        throw new RuntimeException("Async operation has not been completed");
    }
}