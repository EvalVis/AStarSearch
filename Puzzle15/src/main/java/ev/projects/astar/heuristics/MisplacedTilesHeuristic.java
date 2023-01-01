package ev.projects.astar.heuristics;

import ev.projects.heuristics.AStarHeuristic;
import ev.projects.heuristics.AStarObject;

import java.util.List;

public class MisplacedTilesHeuristic extends AStarHeuristic<Integer> {
    @Override
    public int calculateValue(AStarObject<Integer> aStarObject) {
        int misplacedTilesCount = 0;
        List<Integer> cells = aStarObject.getCurrentStateData();
        for(int i = 0; i < cells.size() - 1; i++) {
            if(cells.get(i) != (i + 1)) {
                misplacedTilesCount++;
            }
        }
        if(cells.get(cells.size() - 1) != -1) {
            misplacedTilesCount++;
        }
        return misplacedTilesCount;
    }
}
