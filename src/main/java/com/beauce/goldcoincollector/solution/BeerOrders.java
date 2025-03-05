package com.beauce.goldcoincollector.solution;

import java.util.List;
import java.util.stream.Collectors;

public class BeerOrders {
    private final List<BeerOrder> orders;

    public BeerOrders(List<BeerOrder> orders) {
        if (orders == null || orders.isEmpty()) {
            throw new IllegalArgumentException("Order list cannot be empty");
        }
        this.orders = List.copyOf(orders);
    }

    public double getTotalCost() {
        return orders.stream()
                .mapToDouble(beerOrder -> beerOrder.getTotalCost().doubleValue())
                .sum();
    }

    public String generateInvoice(String pubName) {
        return """
                Invoice for %s:
                %s
                Total: %s€""".formatted(pubName, getBeerList(), getTotalCost());
    }

    public boolean isOverBudget(double budget) {
        return getTotalCost() > budget;
    }

    private String getBeerList() {
        return orders.stream()
                .map(BeerOrder::toString)
                .collect(Collectors.joining("\n"));
    }
}
