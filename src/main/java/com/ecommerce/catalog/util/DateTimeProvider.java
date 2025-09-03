package com.ecommerce.catalog.util;

import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * Centralized time provider.
 * Always use this class instead of calling LocalDateTime.now() directly.
 */
public class DateTimeProvider {

    private static final ZoneId ZONE = ZoneId.systemDefault(); 

    public static LocalDateTime now() {
        return LocalDateTime.now(ZONE);
    }
}
