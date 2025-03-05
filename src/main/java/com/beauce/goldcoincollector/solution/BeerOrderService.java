package com.beauce.goldcoincollector.solution;

public class BeerOrderService {
    public String generateInvoice(String pubName, BeerOrders orders) {
        return orders.generateInvoice(pubName);
    }

    public boolean isOverBudget(BeerOrders orders, double budget) {
        return orders.isOverBudget(budget);
    }
}
