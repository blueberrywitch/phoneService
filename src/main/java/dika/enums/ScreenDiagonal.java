package dika.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ScreenDiagonal {
    //    INCH_3_4(0, 3.4),
//    INCH_3_5_4_9(3.5, 4.9),
    INCH_5_0_5_4(5.0, 5.4, "5,0–5,4″"),
    INCH_5_5_5_9(5.5, 5.9, "5,5–5,9″"),
    INCH_6_0_6_4(6.0, 6.4, "6,0–6,4″"),
    INCH_6_5_6_9(6.5, 6.9, "6,5–6,9″"),
    INCH_7_0_7_4(7.0, 7.4, "7,0–7,4″"),
    INCH_7_5_7_9(7.5, 7.9, "7,5–7,9″"),
    INCH_8_0_8_4(8.0, 8.4, "8,0–8,4″"),
    INCH_8_5_8_9(8.5, 8.9, "8,5–8,9″"),
    INCH_9_0_9_4(9.0, 9.4, "9,0–9,4″"),
    INCH_9_5_9_9(9.5, 9.9, "9,5–9,9″"),
    INCH_10_0(10.0, Double.MAX_VALUE, "10,0″ и более");
    private final double displayValueMin;
    private final double displayValueMax;
    private final String displayValue;


    public String getDisplayValue() {
        return displayValue;
    }


}
