package com.beauce.goldcoincollector.solution;

public class BeerOrderService {
    public String generateInvoice(Pub pub,
                                  BeerOrders orders) {
        return orders.generateInvoice(pub);
    }

    public boolean isOverBudget(BeerOrders orders,
                                double budget) {
        return orders.isOverBudget(budget);
    }
}
