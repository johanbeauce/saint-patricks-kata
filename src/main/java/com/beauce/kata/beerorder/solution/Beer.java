package com.beauce.kata.beerorder.solution;

public record Beer(String name, UnitPrice price) {
    public Beer {
        if (null == name || name.isEmpty()) {
            throw new IllegalArgumentException("Beer name cannot be null or empty");
        }
    }
}
