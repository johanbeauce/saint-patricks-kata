package com.beauce.kata.beerorder;

import java.util.List;

public class BeerOrderService {
    public String generateInvoice(String pubName, List<String> beerNames, List<Integer> quantities, List<Double> unitPrices) {
        if (null == pubName || pubName.isEmpty()) {
            throw new IllegalArgumentException("Pub name cannot be null or empty");
        }

        double totalCost = 0;
        StringBuilder invoice = new StringBuilder("Invoice for " + pubName + ":\n");

        for (int i = 0; i < beerNames.size(); i++) {
            double cost = quantities.get(i) * unitPrices.get(i);
            totalCost += cost;
            invoice.append(beerNames.get(i))
                    .append(" - ")
                    .append(quantities.get(i))
                    .append(" x ")
                    .append(unitPrices.get(i))
                    .append("€ = ")
                    .append(cost)
                    .append("€\n");
        }

        invoice.append("Total: ").append(totalCost).append("€");
        return invoice.toString();
    }

    public boolean isOverBudget(List<Integer> quantities, List<Double> unitPrices, double budget) {
        double totalCost = 0;
        for (int i = 0; i < quantities.size(); i++) {
            totalCost += quantities.get(i) * unitPrices.get(i);
        }

        return totalCost > budget;
    }
}
