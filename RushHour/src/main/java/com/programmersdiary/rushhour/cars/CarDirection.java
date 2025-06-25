package com.programmersdiary.rushhour.cars;

public enum CarDirection {
    HORIZONTAL(1, 0), VERTICAL(0, 1);

    private final int xMultiplier;
    private final int yMultiplier;

    CarDirection(int xMultiplier, int yMultiplier) {
        this.xMultiplier = xMultiplier;
        this.yMultiplier = yMultiplier;
    }

    public int getXMultiplier() {
        return xMultiplier;
    }

    public int getYMultiplier() {
        return yMultiplier;
    }
}
