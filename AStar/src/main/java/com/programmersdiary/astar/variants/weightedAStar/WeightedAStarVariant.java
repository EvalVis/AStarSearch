package com.programmersdiary.astar.variants.weightedAStar;

import com.programmersdiary.astar.heuristics.AStarHeuristic;
import com.programmersdiary.astar.heuristics.AStarObject;
import com.programmersdiary.astar.variants.AStarVariant;
import com.programmersdiary.astar.variants.weightedAStar.weights.LFunction;

public class WeightedAStarVariant<T> extends AStarVariant<T> {

    private final LFunction lFunction;

    public WeightedAStarVariant(AStarHeuristic<T> heuristic, LFunction lFunction) {
        super(heuristic);
        this.lFunction = lFunction;
    }

    @Override
    public int compare(AStarObject<T> aso1, AStarObject<T> aso2) {
        return Double.compare(getFValue(aso1), getFValue(aso2));
    }

    @Override
    public double getFValue(AStarObject<T> aso) {
        return aso.getGValue() + lFunction.calculateL() * heuristic.calculateValue(aso);
    }

}
