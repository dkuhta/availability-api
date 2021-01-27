package com.tui.proof.ws.controller.booking;

import com.tui.proof.ws.controller.BaseControllerTest;
import com.tui.proof.ws.controller.exception.ErrorInfo;
import com.tui.proof.ws.controller.exception.ServerError;
import com.tui.proof.ws.dto.booking.BookingCreateDto;
import com.tui.proof.ws.dto.booking.BookingDto;
import com.tui.proof.ws.dto.booking.Holder;
import com.tui.proof.ws.model.availability.AvailabilityModel;
import com.tui.proof.ws.model.booking.BookingStatus;
import com.tui.proof.ws.model.event.EventModel;
import com.tui.proof.ws.model.event.EventStatus;
import com.tui.proof.ws.respository.availability.AvailabilityRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Set;

import static com.tui.proof.ws.utils.MvcTestUtils.parseResponse;
import static com.tui.proof.ws.utils.MvcTestUtils.requestBody;
import static com.tui.proof.ws.utils.RandomUtils.*;
import static java.lang.Thread.sleep;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.LOCATION;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class BookingControllerTest extends BaseControllerTest {

    @MockBean
    private AvailabilityRepository availabilityRepository;

    @Test
    void create_and_expect_accepted() throws Exception {
        final BookingDto booking = createBooking();

        assertNotNull(booking);
        assertNotNull(booking.getId());
        assertNotNull(booking.getDateCreated());
        assertEquals(BookingStatus.CREATED, booking.getStatus());
        assertNotNull(booking.getHolder());
        assertNotNull(booking.getFlights());
    }

    private BookingDto createBooking() throws Exception {
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

        return validateAsyncResult(bookingCreateResult);
    }

    private AvailabilityModel createAvailability() {
        AvailabilityModel availabilityModel = new AvailabilityModel();
        availabilityModel.setId(randomId());
        availabilityModel.setDateCreated(LocalDateTime.now());
        availabilityModel.setUserName(getUsername());
        return availabilityModel;
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

        assertEquals(ServerError.INPUT_INVALID.getStatusCode(), errorInfo.getStatusCode());
        assertNotNull(errorInfo.getMessage());
    }

    @Test
    void get_details_and_expect_ok() throws Exception {
        BookingDto booking = createBooking();
        String id = booking.getId();

        getMvc().perform(get("/bookings/{id}", id)
                .header(AUTHORIZATION, jwtTokenWithTestUser()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(booking.getId()))
                .andExpect(jsonPath("$.status").value(booking.getStatus().toString()))
                .andExpect(jsonPath("$.dateCreated").isNotEmpty());
    }

    @Test
    void add_flight_and_expect_accepted() throws Exception {
        BookingDto booking = createBooking();
        String id = booking.getId();

        String flightNumber = randomId();

        final MvcResult mvcResult = getMvc().perform(put("/bookings/{id}/flights/{flightNumber}", id, flightNumber)
                .header(AUTHORIZATION, jwtTokenWithTestUser()))
                .andExpect(status().isAccepted())
                .andReturn();

        BookingDto result = validateAsyncResult(mvcResult);
        assertEquals(id, result.getId());
        assertEquals(BookingStatus.CREATED, result.getStatus());
        assertTrue(result.getFlights().contains(flightNumber));
    }

    @Test
    void delete_flight_and_expect_accepted() throws Exception {
        BookingDto booking = createBooking();
        String id = booking.getId();

        String flightNumber = booking.getFlights().iterator().next();

        final MvcResult mvcResult = getMvc().perform(delete("/bookings/{id}/flights/{flightNumber}", id, flightNumber)
                .header(AUTHORIZATION, jwtTokenWithTestUser()))
                .andExpect(status().isAccepted())
                .andReturn();

        BookingDto result = validateAsyncResult(mvcResult);
        assertEquals(id, result.getId());
        assertEquals(BookingStatus.CREATED, result.getStatus());
        assertFalse(result.getFlights().contains(flightNumber));
    }

    @Test
    void confirm_and_expect_accepted() throws Exception {
        BookingDto booking = createBooking();
        String id = booking.getId();

        final MvcResult mvcResult = getMvc().perform(post("/bookings/{id}/confirm", id)
                .header(AUTHORIZATION, jwtTokenWithTestUser()))
                .andExpect(status().isAccepted())
                .andReturn();

        BookingDto result = validateAsyncResult(mvcResult);
        assertEquals(id, result.getId());
        assertEquals(BookingStatus.CONFIRMED, result.getStatus());
    }

    @Test
    void delete_and_expect_accepted() throws Exception {
        BookingDto booking = createBooking();
        String id = booking.getId();

        final MvcResult mvcResult = getMvc().perform(delete("/bookings/{id}", id)
                .header(AUTHORIZATION, jwtTokenWithTestUser()))
                .andExpect(status().isAccepted())
                .andReturn();

        BookingDto result = validateAsyncResult(mvcResult);
        assertEquals(id, result.getId());
        assertEquals(BookingStatus.DELETED, result.getStatus());

    }

    private BookingDto validateAsyncResult(MvcResult mvcResult) throws Exception {
        final String location = mvcResult.getResponse().getHeader(LOCATION);
        assertNotNull(location);

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
                assertNotNull(bookingLocation);
                MvcResult bookingResult = getMvc().perform(get(bookingLocation)
                        .header(AUTHORIZATION, jwtTokenWithTestUser()))
                        .andExpect(status().isOk())
                        .andReturn();
                return parseResponse(bookingResult, BookingDto.class);
            }

            sleep(1000);
        }

        throw new RuntimeException("Async operation has not been completed");
    }
}