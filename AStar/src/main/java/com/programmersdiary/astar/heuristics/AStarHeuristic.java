package com.programmersdiary.astar.heuristics;

public interface AStarHeuristic<T> {

    int calculateValue(AStarObject<T> aStarObject);

}
