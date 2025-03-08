package com.beauce.kata.beerorder.solution;

import java.math.BigDecimal;
import java.math.RoundingMode;

public record UnitPrice(double value) {
    public UnitPrice {
        if (value <= 0) {
            throw new IllegalArgumentException("Price must be greater than 0");
        }
    }

    public double multiplyBy(Quantity quantity) {
        return BigDecimal.valueOf(value())
                .multiply(BigDecimal.valueOf(quantity.value()))
                .setScale(1, RoundingMode.HALF_UP)
                .doubleValue();
    }
}
