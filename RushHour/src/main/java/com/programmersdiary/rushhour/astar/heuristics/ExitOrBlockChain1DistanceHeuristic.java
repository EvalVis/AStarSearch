package com.programmersdiary.rushhour.astar.heuristics;

import com.programmersdiary.rushhour.astar.heuristics.blockChainHeuristics.BlockChainHeuristic;
import com.programmersdiary.astar.heuristics.AStarHeuristic;
import com.programmersdiary.astar.heuristics.AStarObject;
import com.programmersdiary.rushhour.trafficMap.TrafficMap;

public class ExitOrBlockChain1DistanceHeuristic implements AStarHeuristic<TrafficMap> {

    private final int maxMoveCount;

    public ExitOrBlockChain1DistanceHeuristic(int maxMoveCount) {
        this.maxMoveCount = maxMoveCount;
    }

    @Override
    public int calculateValue(AStarObject<TrafficMap> aStarObject) {
        TrafficMap trafficMap = aStarObject.getCurrentStateData();
        if(trafficMap.getFinishX() - trafficMap.getMainCar().getFrontX() == 1) {
            return new BlockChainHeuristic(maxMoveCount).calculateValue(aStarObject);
        }
        return new ExitBlockingCarsCountHeuristic().calculateValue(aStarObject);
    }
}
