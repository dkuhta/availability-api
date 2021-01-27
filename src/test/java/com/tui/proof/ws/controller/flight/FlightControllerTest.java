package com.tui.proof.ws.controller.flight;

import com.tui.proof.ws.controller.BaseControllerTest;
import com.tui.proof.ws.dto.flight.FlightDto;
import com.tui.proof.ws.service.flight.FlightFilter;
import com.tui.proof.ws.service.flight.FlightService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;

import static com.tui.proof.ws.utils.RandomUtils.randomFlightPage;
import static com.tui.proof.ws.utils.RandomUtils.randomString;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class FlightControllerTest extends BaseControllerTest {

    @MockBean
    private FlightService flightService;

    @Test
    void get_flight_and_expect_ok() throws Exception {
        int pageNumber = 0;
        int pageSize = 2;
        long total = 3;
        Page<FlightDto> expected = randomFlightPage(PageRequest.of(pageNumber, pageSize), total);

        Mockito.when(flightService.find(any(FlightFilter.class), any())).thenReturn(expected);

        getMvc().perform(get("/flights")
                .param("airportOrigin", randomString())
                .param("airportDestination", randomString())
                .param("from", "2021-03-01")
                .param("to", "2021-04-01")
                .param("infants", "0")
                .param("children", "1")
                .param("adults", "2")
                .param("page", "0")
                .param("size", "2")
                .header(HttpHeaders.AUTHORIZATION, buildBasicAuthTokenWithTestUser()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalPages").value(2))
                .andExpect(jsonPath("$.totalElements").value(total))
                .andExpect(jsonPath("$.number").value(pageNumber))
                .andExpect(jsonPath("$.size").value(pageSize))
                .andExpect(jsonPath("$.content.*", hasSize(pageSize)));
    }

    @Test
    void get_flight_and_expect_bad_request() throws Exception {
        getMvc().perform(get("/flights")
                .header(HttpHeaders.AUTHORIZATION, buildBasicAuthTokenWithTestUser()))
                .andExpect(status().isBadRequest());
    }
}