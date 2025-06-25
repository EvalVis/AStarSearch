package com.programmersdiary.puzzle15.astar.heuristics;

import com.programmersdiary.astar.heuristics.AStarHeuristic;
import com.programmersdiary.astar.heuristics.AStarObject;
import com.programmersdiary.puzzle15.puzzle.BlankCell;

public class MisplacedTilesHeuristic implements AStarHeuristic<int[]> {

    @Override
    public int calculateValue(AStarObject<int[]> aStarObject) {
        int misplacedTilesCount = 0;
        int[] cells = aStarObject.getCurrentStateData();
        for(int i = 0; i < cells.length; i++) {
            if(cells[i] != (i + 1) && cells[i] != BlankCell.blankCellMarker) {
                misplacedTilesCount++;
            }
        }
        return misplacedTilesCount;
    }
}
