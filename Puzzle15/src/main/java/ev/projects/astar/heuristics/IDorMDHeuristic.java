package ev.projects.astar.heuristics;

import ev.projects.heuristics.AStarHeuristic;
import ev.projects.heuristics.AStarObject;

public class IDorMDHeuristic implements AStarHeuristic<int[]> {

    @Override
    public int calculateValue(AStarObject<int[]> aStarObject) {
        return Math.max(
                new ManhattanDistanceHeuristic().calculateValue(aStarObject),
                new InversionDistanceHeuristic().calculateValue(aStarObject)
        );
    }
}
