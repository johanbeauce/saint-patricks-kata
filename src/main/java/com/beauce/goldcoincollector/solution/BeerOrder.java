package com.beauce.goldcoincollector.solution;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class BeerOrder {
    private final String beerName;
    private final int quantity;
    private final BigDecimal unitPrice;

    public BeerOrder(String beerName, int quantity, BigDecimal unitPrice) {
        if (quantity <= 0) throw new IllegalArgumentException("Quantity must be greater than zero");
        if (unitPrice.compareTo(BigDecimal.ZERO) <= 0) throw new IllegalArgumentException("Unit price must be greater than zero");

        this.beerName = Objects.requireNonNull(beerName, "Beer name cannot be null");
        this.quantity = quantity;
        this.unitPrice = unitPrice.setScale(1, RoundingMode.HALF_UP);
    }

    public BigDecimal getTotalCost() {
        return unitPrice.multiply(BigDecimal.valueOf(quantity)).setScale(1, RoundingMode.HALF_UP);
    }

    public String getBeerName() {
        return beerName;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    @Override
    public String toString() {
        return beerName + " - " + quantity + " x " + unitPrice + "€ = " + getTotalCost() + "€";
    }
}
