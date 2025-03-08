package com.beauce.kata.beerorder.solution;

import java.util.List;
import java.util.function.Consumer;

public class BeerOrders {
    private final List<BeerOrder> beerOrders;

    public BeerOrders(BeerOrder... beerOrders) {
        if (beerOrders == null || beerOrders.length == 0) {
            throw new IllegalArgumentException("BeerOrders cannot be empty");
        }
        this.beerOrders = List.of(beerOrders);
    }

    public void forEach(Consumer<BeerOrder> action) {
        beerOrders.forEach(action);
    }

    public double getTotalPrice() {
        return beerOrders.stream()
                .mapToDouble(BeerOrder::totalPrice)
                .sum();
    }
}
