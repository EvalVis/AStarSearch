package ev.projects.astar.heuristics;

import ev.projects.heuristics.AStarHeuristic;
import ev.projects.heuristics.AStarObject;
import ev.projects.puzzle.BlankCell;

import java.util.List;

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
