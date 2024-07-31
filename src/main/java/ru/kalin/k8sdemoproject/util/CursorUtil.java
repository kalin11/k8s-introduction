package ru.kalin.k8sdemoproject.util;

import jakarta.annotation.Nullable;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CursorUtil {

    public static Cursor getCursor(
        @Nullable Integer limit,
        @Nullable Integer offset,
        Integer defaultLimit,
        Integer defaultOffset
    ) {
        return new Cursor(
            limit == null ? defaultLimit : limit,
            offset == null ? defaultOffset : offset
        );
    }

    @Value
    @Data
    @RequiredArgsConstructor
    public static class Cursor {
        Integer limit;
        Integer offset;
    }
}
