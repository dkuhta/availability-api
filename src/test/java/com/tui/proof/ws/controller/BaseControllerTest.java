package com.tui.proof.ws.controller;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;

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

    protected String buildBasicAuthTokenWithTestUser() {
        return buildBasicAuthToken(username, password);
    }

    protected String buildBasicAuthToken(String username, String password) {
        String auth = username + ":" + password;
        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.ISO_8859_1));
        return "Basic " + new String(encodedAuth);
    }

    public String getUsername() {
        return username;
    }
}