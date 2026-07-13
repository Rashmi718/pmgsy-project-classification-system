package com.pmgsy.classifier.util;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public final class DateTimeUtil {

    private DateTimeUtil() {
    }

    public static OffsetDateTime nowUtc() {
        return OffsetDateTime.ofInstant(Instant.now(), ZoneOffset.UTC);
    }
}
