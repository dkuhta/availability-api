package com.tui.proof.ws.utils;

import com.tui.proof.ws.dto.flight.FlightDto;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public final class RandomUtils {

    private static final Random RANDOM = new Random();

    private RandomUtils() {
    }

    public static String randomId() {
        return UUID.randomUUID().toString();
    }

    public static String randomString() {
        return RandomStringUtils.randomAlphanumeric(randomInt(20, 100));
    }

    public static String randomStringNumeric() {
        return RandomStringUtils.randomNumeric(randomInt(20, 100));
    }

    public static String randomEmail() {
        return randomString() + "@gmail.com";
    }

    public static int randomInt(int min, int max) {
        return min + RANDOM.nextInt(max - min + 1);
    }

    public static long randomLong() {
        return new Random().nextLong();
    }

    public static Page<FlightDto> randomFlightPage(Pageable pageable, long total) {
        return new PageImpl<>(generateFlightList(pageable.getPageSize()), pageable, total);
    }

    public static List<FlightDto> generateFlightList(long count) {
        return LongStream.range(0, count)
                .mapToObj(it -> generateFlight())
                .collect(Collectors.toList());
    }

    public static FlightDto generateFlight() {
        FlightDto dto = new FlightDto();
        dto.setAvailabilityId(randomId());
        dto.setFlightNumber(randomString());
        dto.setCompany(randomString());
        dto.setDate(LocalDate.now());
        dto.setHours(LocalTime.now());
        dto.setPrice(randomLong());
        return dto;
    }
}
