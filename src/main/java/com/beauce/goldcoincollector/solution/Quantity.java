package com.beauce.goldcoincollector.solution;

public record Quantity(int value) {
    public Quantity {
        if (value <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }
    }
}
