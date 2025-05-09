package dika.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ScreenDiagonal {

    INCH_5_0_5_4(5.0, 5.4, "5,0–5,4″"),
    INCH_5_5_5_9(5.5, 5.9, "5,5–5,9″"),
    INCH_6_0_6_4(6.0, 6.4, "6,0–6,4″"),
    INCH_6_5_6_9(6.5, 6.9, "6,5–6,9″"),
    INCH_7_0_7_4(7.0, 7.4, "7,0–7,4″"),
    INCH_10_0(7.5, Double.MAX_VALUE, "7,5″ и более");
    private final double displayValueMin;
    private final double displayValueMax;
    private final String displayValue;


    public String getDisplayValue() {
        return displayValue;
    }


}
