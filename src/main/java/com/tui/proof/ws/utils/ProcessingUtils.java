package com.tui.proof.ws.utils;

import lombok.SneakyThrows;

public final class ProcessingUtils {

    private ProcessingUtils() {
    }

    @SneakyThrows
    public static void sleep(Long millis) {
        Thread.sleep(millis);
    }
}
