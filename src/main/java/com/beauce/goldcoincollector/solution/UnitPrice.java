package com.beauce.goldcoincollector.solution;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class UnitPrice {
    private final BigDecimal value;

    public UnitPrice(double unitPrice) {
        if (unitPrice <= 0) {
            throw new IllegalArgumentException("Unit price must be greater than zero");
        }
        this.value = new BigDecimal(unitPrice);
    }

    public double value() {
        return value.doubleValue();
    }

    public double multiply(Quantity quantity) {
        return value
                .multiply(BigDecimal.valueOf(quantity.value()))
                .setScale(1, RoundingMode.HALF_UP)
                .doubleValue();
    }
}
