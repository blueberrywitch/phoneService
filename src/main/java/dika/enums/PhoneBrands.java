package dika.enums;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum PhoneBrands {
    APPLE,
    SAMSUNG,
    XIAOMI,
    HUAWEI,
    OPPO,
    VIVO,
    GOOGLE,
    NOKIA,
    MOTOROLA,
    SONY,
    LG,
    ONEPLUS,
    ASUS,
    REALME,
    LENOVO;

    private static final Map<String, PhoneBrands> LOOKUP =
            Stream.of(values()).collect(Collectors.toMap(Enum::name, e -> e));

    public static PhoneBrands fromString(String text) {
        if (text == null || text.isBlank()) return null;
        return LOOKUP.getOrDefault(text.trim().toUpperCase(), null);
    }

    public static List<String> getAllAsStrings() {
        return Stream.of(values())
                .map(Enum::name)
                .collect(Collectors.toList());
    }
}
