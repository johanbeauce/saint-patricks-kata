package com.beauce.kata.beerorder.solution;

public record BeerOrder(Beer beer, Quantity quantity) {
    public BeerOrder {
        if (null == beer) {
            throw new IllegalArgumentException("Beer cannot be null");
        }
    }

    public double totalPrice() {
        return beer.price()
                .multiplyBy(quantity);
    }

    @Override
    public String toString() {
        return "%s - %d x %s€ = %s€"
                .formatted(beer.name(), quantity.value(), beer.price().value(), totalPrice());
    }
}
