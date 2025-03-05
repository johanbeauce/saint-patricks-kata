package com.beauce.goldcoincollector.solution;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("For a Beer Order Service")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class BeerOrderServiceTest {
    private BeerOrderService service;

    @BeforeEach
    void setup() {
        this.service = new BeerOrderService();
    }

    @Test
    void should_generate_invoice_correctly() {
        BeerOrders orders = new BeerOrders(List.of(
                new BeerOrder(new Beer("Guinness"), 10, BigDecimal.valueOf(5.0)),
                new BeerOrder(new Beer("Kilkenny"), 5, BigDecimal.valueOf(4.5))
        ));

        String invoice = service.generateInvoice("O’Malley’s Pub", orders);

        assertThat(invoice).contains(
                "Guinness - 10 x 5.0€ = 50.0€",
                "Kilkenny - 5 x 4.5€ = 22.5€",
                "Total: 72.5€");
    }

    @Test
    void should_detect_over_budget_orders() {
        BeerOrders orders = new BeerOrders(List.of(
                new BeerOrder(new Beer("Guinness"), 20, BigDecimal.valueOf(6.0)),
                new BeerOrder(new Beer("Kilkenny"), 15, BigDecimal.valueOf(5.5))
        ));

        assertThat(service.isOverBudget(orders, 100.0)).isTrue();
    }

    @Test
    void should_not_detect_over_budget_orders() {
        BeerOrders orders = new BeerOrders(List.of(
                new BeerOrder(new Beer("Guinness"), 5, BigDecimal.valueOf(5.0)),
                new BeerOrder(new Beer("Kilkenny"), 2, BigDecimal.valueOf(4.0))
        ));

        assertThat(service.isOverBudget(orders, 100.0)).isFalse();
    }

    @Test
    void should_throw_exception_for_invalid_order() {
        var unitPrice = BigDecimal.valueOf(5.0);
        var guinness = new Beer("Guinness");
        assertThatThrownBy(() -> new BeerOrder(guinness, 0, unitPrice))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Quantity must be greater than zero");
    }
}