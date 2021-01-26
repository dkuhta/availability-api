package com.tui.proof.ws.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.SneakyThrows;
import org.springframework.test.web.servlet.MvcResult;

public final class MvcTestUtils {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper().setDefaultPropertyInclusion(JsonInclude.Include.NON_NULL)
                    .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                    .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                    .enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
                    .findAndRegisterModules();

    private MvcTestUtils() {
    }

    @SneakyThrows
    public static String requestBody(Object request) {
            return OBJECT_MAPPER.writeValueAsString(request);
    }

    @SneakyThrows
    public static <T> T parseResponse(MvcResult result, Class<T> responseClass) {
            String contentAsString = result.getResponse().getContentAsString();
            return OBJECT_MAPPER.readValue(contentAsString, responseClass);
    }

    @SneakyThrows
    public static <T> T parseResponse(MvcResult result, TypeReference<T> typeReference) {
        String contentAsString = result.getResponse().getContentAsString();
        return OBJECT_MAPPER.readValue(contentAsString, typeReference);
    }
}
