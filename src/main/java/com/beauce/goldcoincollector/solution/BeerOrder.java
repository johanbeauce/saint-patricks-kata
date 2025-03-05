package com.beauce.goldcoincollector.solution;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BeerOrder {
    private final Beer beer;
    private final int quantity;
    private final BigDecimal unitPrice;

    public BeerOrder(Beer beer,
                     int quantity,
                     BigDecimal unitPrice) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }
        if (unitPrice.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Unit price must be greater than zero");
        }

        this.beer = beer;
        this.quantity = quantity;
        this.unitPrice = unitPrice.setScale(1, RoundingMode.HALF_UP);
    }

    public BigDecimal getTotalCost() {
        return unitPrice
                .multiply(BigDecimal.valueOf(quantity))
                .setScale(1, RoundingMode.HALF_UP);
    }

    @Override
    public String toString() {
        return "%s - %d x %s€ = %s€"
                .formatted(beer.name(), quantity, unitPrice, getTotalCost());
    }
}
