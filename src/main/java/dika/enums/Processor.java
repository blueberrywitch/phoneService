package dika.enums;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum Processor {
    BIONIC,
    EXYNOS,
    HELIO,
    SNAPDRAGON,
    TEGRA,
    KIRIN,
    MEDIATEK,
    UNISOC;

    private static final Map<String, Processor> LOOKUP =
            Stream.of(values()).collect(Collectors.toMap(Enum::name, e -> e));

    public static Processor fromString(String text) {
        if (text == null || text.isBlank()) return null;
        return LOOKUP.getOrDefault(text.trim().toUpperCase(), null);
    }
}
