package com.beauce.kata;

import com.beauce.kata.beerorder.BeerOrderService;

import java.util.List;

public class PatricksPub {

    public static void main(String[] args) {
        var service = new BeerOrderService();

        var beerNames = List.of(
                "Guinness", "Kilkenny", "Heineken", "Budweiser", "Corona", "Stella Artois", "Carlsberg", "Coors Light",
                "Miller Lite", "Pabst Blue Ribbon", "Blue Moon", "Samuel Adams", "Sierra Nevada", "Newcastle Brown Ale",
                "Bass Pale Ale", "Beck's", "Amstel Light", "Dos Equis", "Modelo", "Pacifico", "Tecate", "Victoria",
                "Red Stripe", "Labatt Blue", "Molson Canadian", "Foster's", "Peroni", "Birra Moretti", "Asahi", "Sapporo",
                "Kirin", "Tsingtao", "Tiger", "Singha", "Chang", "San Miguel", "Estrella Damm", "Mahou", "Alhambra",
                "Leffe", "Hoegaarden", "Duvel", "Chimay", "Orval", "Rochefort", "Westmalle", "La Chouffe", "Delirium Tremens",
                "Tripel Karmeliet"
        );
        var quantities = List.of(10, 5, 8, 12, 7, 9, 11, 6, 4, 3, 15, 14, 13, 2, 1, 10, 5, 8, 12, 7, 9, 11, 6, 4, 3, 15, 14, 13, 2, 1, 10, 5, 8, 12, 7, 9, 11, 6, 4, 3, 15, 14, 13, 2, 1, 10, 5, 8, 10, 5);
        var unitPrices = List.of(5.0, 4.5, 6.0, 5.5, 4.0, 3.5, 6.5, 5.0, 4.5, 3.0, 6.0, 5.5, 4.0, 3.5, 6.5, 5.0, 4.5, 6.0, 5.5, 4.0, 3.5, 6.5, 5.0, 4.5, 3.0, 6.0, 5.5, 4.0, 3.5, 6.5, 5.0, 4.5, 6.0, 5.5, 4.0, 3.5, 6.5, 5.0, 4.5, 3.0, 6.0, 5.5, 4.0, 3.5, 6.5, 5.0, 4.5, 6.0, 5.5, 4.0);

        var invoice = service.generateInvoice(
                "O’Malley’s Pub",
                beerNames,
                quantities,
                unitPrices
        );

        System.out.println(invoice);

        System.out.println("is it over budget? " + service.isOverBudget(
                quantities,
                unitPrices,
                2000.0
        ));

    }
}
