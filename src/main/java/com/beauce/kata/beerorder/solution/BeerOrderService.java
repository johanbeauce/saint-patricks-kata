package com.beauce.kata.beerorder.solution;

public class BeerOrderService {
    public String generateInvoice(Pub pub,
                                  BeerOrders beerOrders) {
        return new Invoice(pub, beerOrders)
                .generate();
    }

    public boolean isOverBudget(BeerOrders beerOrders,
                                double budget) {
        return beerOrders.getTotalPrice() > budget;
    }
}
