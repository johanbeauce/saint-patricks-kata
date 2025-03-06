package com.beauce.goldcoincollector.solution;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

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
        var orders = new BeerOrders(List.of(
                new BeerOrder(new Beer("Guinness"), new Quantity(10), new UnitPrice(5.0)),
                new BeerOrder(new Beer("Kilkenny"), new Quantity(5), new UnitPrice(4.5))
        ));

        var pub = new Pub("O’Malley’s Pub");
        var invoice = service.generateInvoice(pub, orders);

        assertThat(invoice).contains(
                "Guinness - 10 x 5.0€ = 50.0€",
                "Kilkenny - 5 x 4.5€ = 22.5€",
                "Total: 72.5€");
    }

    @Test
    void should_detect_over_budget_orders() {
        BeerOrders orders = new BeerOrders(List.of(
                new BeerOrder(new Beer("Guinness"), new Quantity(20), new UnitPrice(6.0)),
                new BeerOrder(new Beer("Kilkenny"), new Quantity(15), new UnitPrice(5.5))
        ));

        assertThat(service.isOverBudget(orders, 100.0)).isTrue();
    }

    @Test
    void should_not_detect_over_budget_orders() {
        BeerOrders orders = new BeerOrders(List.of(
                new BeerOrder(new Beer("Guinness"), new Quantity(5), new UnitPrice(5.0)),
                new BeerOrder(new Beer("Kilkenny"), new Quantity(2), new UnitPrice(4.0))
        ));

        assertThat(service.isOverBudget(orders, 100.0)).isFalse();
    }

    @Test
    void should_throw_exception_for_invalid_order() {
        var unitPrice = new UnitPrice(5.0);
        var guinness = new Beer("Guinness");
        assertThatThrownBy(() -> new BeerOrder(guinness, new Quantity(0), unitPrice))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Quantity must be greater than zero");
    }
}