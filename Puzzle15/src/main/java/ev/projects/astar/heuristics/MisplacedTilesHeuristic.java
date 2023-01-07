package ev.projects.astar.heuristics;

import ev.projects.heuristics.AStarHeuristic;
import ev.projects.heuristics.AStarObject;

import java.util.List;

public class MisplacedTilesHeuristic extends AStarHeuristic<int[]> {
    @Override
    public int calculateValue(AStarObject<int[]> aStarObject) {
        int misplacedTilesCount = 0;
        int[] cells = aStarObject.getCurrentStateData();
        for(int i = 0; i < cells.length - 1; i++) {
            if(cells[i] != (i + 1)) {
                misplacedTilesCount++;
            }
        }
        if(cells[cells.length - 1] != -1) {
            misplacedTilesCount++;
        }
        return misplacedTilesCount;
    }
}
