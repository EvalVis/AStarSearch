package com.programmersdiary.astar.variants.weightedAStar.weights.timed;

import com.programmersdiary.astar.variants.weightedAStar.weights.LFunction;

public abstract class TimedLFunction extends LFunction {

    protected long startTimeNano;

    public TimedLFunction(long startTimeNano) {
        this.startTimeNano = startTimeNano;
    }

    protected int getSecondsPassed() {
        double currentTimeNano = System.nanoTime();
        double timePassedNano = currentTimeNano - startTimeNano;
        return (int) (timePassedNano / 1_000_000_000);
    }

}
