package com.tui.proof.ws.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import static java.util.Objects.isNull;

public final class SecurityUtils {

    private SecurityUtils() {
    }

    public static String getCurrentUsername() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (isNull(authentication)) {
            throw new RuntimeException("These is no user");
        }

        return authentication.getName();
    }
}
