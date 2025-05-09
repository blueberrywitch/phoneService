package dika.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@AllArgsConstructor
@Getter
public enum InternalStorage {
    GB_32("32GB"),
    GB_64("64GB"),
    GB_128("128GB"),
    GB_256("256GB"),
    GB_512("512GB"),
    TB_1("1TB"),
    TB_2("2TB");

    private final String displayValue;

    public String getDisplayValue() {
        return displayValue;
    }

    private static final Map<String, InternalStorage> LOOKUP =
            Stream.of(values())
                    .collect(Collectors.toMap(
                            s -> s.displayValue.toLowerCase(),
                            s -> s
                    ));

}
