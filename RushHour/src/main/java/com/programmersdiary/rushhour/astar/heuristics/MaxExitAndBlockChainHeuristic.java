package com.programmersdiary.rushhour.astar.heuristics;

import com.programmersdiary.rushhour.astar.heuristics.blockChainHeuristics.BlockChainHeuristic;
import com.programmersdiary.astar.heuristics.AStarHeuristic;
import com.programmersdiary.astar.heuristics.AStarObject;
import com.programmersdiary.rushhour.trafficMap.TrafficMap;

public class MaxExitAndBlockChainHeuristic implements AStarHeuristic<TrafficMap> {

    private final int maxMoveCount;

    public MaxExitAndBlockChainHeuristic(int maxMoveCount) {
        this.maxMoveCount = maxMoveCount;
    }

    @Override
    public int calculateValue(AStarObject<TrafficMap> aStarObject) {
        return Math.max(
                new ExitBlockingCarsCountHeuristic().calculateValue(aStarObject),
                new BlockChainHeuristic(maxMoveCount).calculateValue(aStarObject)
        );
    }
}
