package com.beauce.kata.beerorder.solution;

import java.util.List;
import java.util.stream.Collectors;

public class BeerOrders {
    private final List<BeerOrder> beerOrders;

    public BeerOrders(BeerOrder... beerOrders) {
        if (beerOrders == null || beerOrders.length == 0) {
            throw new IllegalArgumentException("BeerOrders cannot be empty");
        }
        this.beerOrders = List.of(beerOrders);
    }

    public double getTotalPrice() {
        return beerOrders.stream()
                .mapToDouble(BeerOrder::totalPrice)
                .sum();
    }

    @Override
    public String toString() {
        return beerOrders.stream()
                .map(Record::toString)
                .collect(Collectors.joining("\n"));
    }
}
