package com.beauce.kata.beerorder.solution;

public record Invoice(Pub pub, BeerOrders beerOrders) {
    public Invoice {
        if (null == pub) {
            throw new IllegalArgumentException("Pub cannot be null");
        }
        if (null == beerOrders) {
            throw new IllegalArgumentException("BeerOrders cannot be null");
        }
    }

    public String generate() {
        return """
                Invoice for %s:
                %s
                Total: %s€""".formatted(pub.name(), beerOrders.toString(), beerOrders.getTotalPrice());
    }
}
