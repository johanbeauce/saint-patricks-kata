package com.beauce.goldcoincollector.solution;

import java.util.Objects;

public record Pub(String name) {
    public Pub {
        Objects.requireNonNull(name, "Pub name cannot be null");
    }
}
