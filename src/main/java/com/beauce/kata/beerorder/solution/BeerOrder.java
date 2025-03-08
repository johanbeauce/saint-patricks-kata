package com.beauce.kata.beerorder.solution;

import java.math.BigDecimal;
import java.math.RoundingMode;

public record BeerOrder(Beer beer, int quantity) {
    public BeerOrder {
        if (null == beer) {
            throw new IllegalArgumentException("Beer cannot be null");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }
    }

    public double totalPrice() {
        return BigDecimal.valueOf(beer().price())
                .multiply(BigDecimal.valueOf(quantity()))
                .setScale(1, RoundingMode.HALF_UP)
                .doubleValue();
    }

    @Override
    public String toString() {
        return "%s - %d x %s€ = %s€"
                .formatted(beer.name(), quantity, beer.price(), totalPrice());
    }
}
