package com.programmersdiary.puzzle15.astar.heuristics;

import com.programmersdiary.astar.heuristics.AStarHeuristic;
import com.programmersdiary.astar.heuristics.AStarObject;

public class IDorMDHeuristic implements AStarHeuristic<int[]> {

    @Override
    public int calculateValue(AStarObject<int[]> aStarObject) {
        return Math.max(
                new ManhattanDistanceHeuristic().calculateValue(aStarObject),
                new InversionDistanceHeuristic().calculateValue(aStarObject)
        );
    }
}
