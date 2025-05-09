package dika.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BatteryCapacity {
    MAH_2500_2999(2500, 2999, "2500–2999 мА·ч"),
    MAH_3000_3499(3000, 3499, "3000–3499 мА·ч"),
    MAH_3500_3999(3500, 3999, "3500–3999 мА·ч"),
    MAH_4000_4999(4000, 4499, "4000–4499 мА·ч"),
    MAH_4500(4500, Integer.MAX_VALUE, "4500 мА·ч и более");

    private final double capacityMin;
    private final double capacityMax;
    private final String displayValue;

    public String getDisplayValue() {
        return displayValue;
    }
}
