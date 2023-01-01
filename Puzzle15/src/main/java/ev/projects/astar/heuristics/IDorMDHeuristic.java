package ev.projects.astar.heuristics;

import ev.projects.heuristics.AStarHeuristic;
import ev.projects.heuristics.AStarObject;

public class IDorMDHeuristic extends AStarHeuristic<Integer> {
    @Override
    public int calculateValue(AStarObject<Integer> aStarObject) {
        int MD = new ManhattanDistanceHeuristic().calculateValue(aStarObject);
        int ID = new InversionDistanceHeuristic().calculateValue(aStarObject);
        return Math.max(MD, ID);
    }
}
