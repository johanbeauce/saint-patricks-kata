package com.beauce.kata.beerorder.solution;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BeerOrderServiceTest {
    private BeerOrderService service;

    @BeforeEach
    void setup() {
        this.service = new BeerOrderService();
    }

    @Test
    void shouldGenerateInvoiceCorrectly() {
        var pub = new Pub("O’Malley’s Pub");
        var beerOrders = new BeerOrders(
                new BeerOrder(
                        new Beer("Guinness", new UnitPrice(5.0)),
                        new Quantity(10)),
                new BeerOrder(
                        new Beer("Kilkenny", new UnitPrice(4.5)),
                        new Quantity(5)));

        var invoice = service.generateInvoice(pub, beerOrders);

        assertThat(invoice)
                .contains(
                        "Guinness - 10 x 5.0€ = 50.0€",
                        "Kilkenny - 5 x 4.5€ = 22.5€",
                        "Total: 72.5€");
    }

    @Test
    void shouldDetectOverBudgetOrders() {
        var beerOrders = new BeerOrders(
                new BeerOrder(
                        new Beer("Beer1", new UnitPrice(6.0)),
                        new Quantity(20)),
                new BeerOrder(
                        new Beer("Beer2", new UnitPrice(5.5)),
                        new Quantity(15)));

        assertThat(service.isOverBudget(beerOrders, 100.0))
                .isTrue();
    }

    @Test
    void shouldNotDetectOverBudgetOrders() {
        var beerOrders = new BeerOrders(
                new BeerOrder(
                        new Beer("Beer1", new UnitPrice(5.0)),
                        new Quantity(5)),
                new BeerOrder(
                        new Beer("Beer2", new UnitPrice(4.0)),
                        new Quantity(2)));

        assertThat(service.isOverBudget(beerOrders, 100.0))
                .isFalse();
    }
}