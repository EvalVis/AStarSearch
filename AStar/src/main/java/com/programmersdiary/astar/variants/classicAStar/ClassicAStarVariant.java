package com.programmersdiary.astar.variants.classicAStar;

import com.programmersdiary.astar.heuristics.AStarHeuristic;
import com.programmersdiary.astar.heuristics.AStarObject;
import com.programmersdiary.astar.variants.AStarVariant;

public class ClassicAStarVariant<T> extends AStarVariant<T> {

    public ClassicAStarVariant(AStarHeuristic<T> heuristic) {
        super(heuristic);
    }

    @Override
    public int compare(AStarObject<T> aso1, AStarObject<T> aso2) {
        return Double.compare(getFValue(aso1), getFValue(aso2));
    }

    @Override
    public double getFValue(AStarObject<T> aso) {
        return aso.getGValue() + heuristic.calculateValue(aso);
    }
}
