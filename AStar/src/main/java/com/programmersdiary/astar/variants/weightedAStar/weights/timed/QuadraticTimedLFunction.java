package com.programmersdiary.astar.variants.weightedAStar.weights.timed;

public class QuadraticTimedLFunction extends TimedLFunction {

    private final double a;
    private final double b;
    private final double c;

    public QuadraticTimedLFunction(long startTimeNano, double a, double b, double c) {
        super(startTimeNano);
        this.a = a;
        this.b = b;
        this.c = c;
    }

    @Override
    public double calculateL() {
        int secondsPassed = getSecondsPassed();
        return a * secondsPassed * secondsPassed + b * secondsPassed + c;
    }

}
