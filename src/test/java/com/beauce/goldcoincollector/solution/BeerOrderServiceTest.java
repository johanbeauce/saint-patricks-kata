package com.beauce.goldcoincollector.solution;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BeerOrderServiceTest {
    private BeerOrderService service;

    @BeforeEach
    void setup() {
        this.service = new BeerOrderService();
    }

    @Test
    void shouldGenerateInvoiceCorrectly() {
        BeerOrders orders = new BeerOrders(List.of(
                new BeerOrder("Guinness", 10, BigDecimal.valueOf(5.0)),
                new BeerOrder("Kilkenny", 5, BigDecimal.valueOf(4.5))
        ));

        String invoice = service.generateInvoice("O’Malley’s Pub", orders);

        assertThat(invoice).contains("Guinness - 10 x 5.0€ = 50.0€");
        assertThat(invoice).contains("Kilkenny - 5 x 4.5€ = 22.5€");
        assertThat(invoice).contains("Total: 72.5€");
    }

    @Test
    void shouldDetectOverBudgetOrders() {
        BeerOrders orders = new BeerOrders(List.of(
                new BeerOrder("Guinness", 20, BigDecimal.valueOf(6.0)),
                new BeerOrder("Kilkenny", 15, BigDecimal.valueOf(5.5))
        ));

        assertThat(service.isOverBudget(orders, 100.0)).isTrue();
    }

    @Test
    void shouldNotDetectOverBudgetOrders() {
        BeerOrders orders = new BeerOrders(List.of(
                new BeerOrder("Guinness", 5, BigDecimal.valueOf(5.0)),
                new BeerOrder("Kilkenny", 2, BigDecimal.valueOf(4.0))
        ));

        assertThat(service.isOverBudget(orders, 100.0)).isFalse();
    }

    @Test
    void shouldThrowExceptionForInvalidOrder() {
        assertThatThrownBy(() -> new BeerOrder("Guinness", 0, BigDecimal.valueOf(5.0)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Quantity must be greater than zero");
    }
}