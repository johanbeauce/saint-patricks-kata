package com.beauce.goldcoincollector.solution;

public class BeerOrder {
    private final Beer beer;
    private final Quantity quantity;
    private final UnitPrice unitPrice;

    public BeerOrder(Beer beer,
                     Quantity quantity,
                     UnitPrice unitPrice) {
        this.beer = beer;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public double getTotalCost() {
        return unitPrice.multiply(quantity);
    }

    @Override
    public String toString() {
        return "%s - %d x %s€ = %s€"
                .formatted(beer.name(), quantity.value(), unitPrice.value(), getTotalCost());
    }
}
