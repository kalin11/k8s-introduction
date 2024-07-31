package ru.kalin.k8sdemoproject.service.util;

import lombok.RequiredArgsConstructor;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@RequiredArgsConstructor
public class ClockService {

    private final Clock clock;

    public LocalDateTime now() {
        return clock.instant().atOffset(ZoneOffset.UTC).toLocalDateTime();
    }
}
