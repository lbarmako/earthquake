package com.intfinit.earthquakes.common;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import static java.time.ZoneOffset.UTC;

public class DateTimeHelper {

    public static LocalDateTime toLocalDateTime(OffsetDateTime offsetDateTime) {
        return offsetDateTime == null ? null
                : offsetDateTime.toZonedDateTime().withZoneSameInstant(UTC).toLocalDateTime();
    }
}
