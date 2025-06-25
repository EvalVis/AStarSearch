package com.programmersdiary.astar.variants.weightedAStar.weights;

public class ConstantLFunction extends LFunction {

    private final double l;

    public ConstantLFunction(double l) {
        this.l = l;
    }

    @Override
    public double calculateL() {
        return l;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " l = " + l;
    }
}
