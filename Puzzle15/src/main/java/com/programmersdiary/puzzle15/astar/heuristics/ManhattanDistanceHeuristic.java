package com.programmersdiary.puzzle15.astar.heuristics;

import com.programmersdiary.astar.heuristics.AStarHeuristic;
import com.programmersdiary.astar.heuristics.AStarObject;
import com.programmersdiary.puzzle15.utils.Utils;

public class ManhattanDistanceHeuristic implements AStarHeuristic<int[]> {

    @Override
    public int calculateValue(AStarObject<int[]> aStarObject) {
        int[] cells = aStarObject.getCurrentStateData();
        int lineSize = (int) Math.sqrt(cells.length);
        int totalDistance = 0;
        for(int i = 0; i < cells.length; i++) {
            if(cells[i] == -1) {
                continue;
            }
            Utils.StartEndCoordinates coordinates = Utils.StartEndCoordinates.getCoordinates(i, cells[i], lineSize);
            totalDistance += Math.abs((coordinates.getStartX() - coordinates.getEndX()))
                    + Math.abs((coordinates.getStartY() - coordinates.getEndY()));
        }
        return totalDistance;
    }
}
