package com.tui.proof.ws.controller;

import com.tui.proof.ws.security.JwtRequest;
import com.tui.proof.ws.security.JwtResponse;
import com.tui.proof.ws.utils.MvcTestUtils;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static com.tui.proof.ws.utils.MvcTestUtils.requestBody;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class BaseControllerTest {

    @Value("${spring.security.user.name}")
    private String username;

    @Value("${spring.security.user.password}")
    private String password;

    @Autowired
    private MockMvc mvc;

    public MockMvc getMvc() {
        return mvc;
    }

    protected String jwtTokenWithTestUser() {
        return jwtAuthToken(username, password);
    }

    @SneakyThrows
    protected String jwtAuthToken(String username, String password) {
        JwtRequest jwtRequest = new JwtRequest();
        jwtRequest.setUsername(username);
        jwtRequest.setPassword(password);

        MvcResult authResult = getMvc().perform(post("/auth")
                .content(requestBody(jwtRequest))
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        final JwtResponse jwtResponse = MvcTestUtils.parseResponse(authResult, JwtResponse.class);

        return "Bearer " + jwtResponse.getJwttoken();
    }

    public String getUsername() {
        return username;
    }
}