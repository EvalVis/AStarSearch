package com.programmersdiary.rushhour.astar.heuristics;

import com.programmersdiary.astar.heuristics.AStarHeuristic;
import com.programmersdiary.astar.heuristics.AStarObject;
import com.programmersdiary.rushhour.trafficMap.TrafficMap;

public class ExitBlockingCarsCountHeuristic implements AStarHeuristic<TrafficMap> {

    @Override
    public int calculateValue(AStarObject<TrafficMap> aStarObject) {
        TrafficMap trafficMap = aStarObject.getCurrentStateData();
        int steps = trafficMap.getFinishX() - trafficMap.getMainCar().getRearX();
        int startX = trafficMap.getMainCar().getFrontX();
        int y = trafficMap.getMainCar().getFrontY();
        int count = 0;
        for(int i = 1; i <= steps; i++) {
            if(trafficMap.getCells()[y][startX + i].getOccupant() != null) {
                count++;
            }
        }
        return count;
    }

}
