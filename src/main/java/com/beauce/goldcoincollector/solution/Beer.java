package com.beauce.goldcoincollector.solution;

import java.util.Objects;

public record Beer(String name) {
    public Beer {
        Objects.requireNonNull(name, "Beer name cannot be null");
    }
}
