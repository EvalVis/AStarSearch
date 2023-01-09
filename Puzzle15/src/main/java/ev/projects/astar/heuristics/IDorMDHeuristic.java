package ev.projects.astar.heuristics;

import ev.projects.heuristics.AStarHeuristic;
import ev.projects.heuristics.AStarObject;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class IDorMDHeuristic extends AStarHeuristic<int[]> {

    private final int puzzleLineSize;

    @Override
    public int calculateValue(AStarObject<int[]> aStarObject) {
        int MD = new ManhattanDistanceHeuristic().calculateValue(aStarObject);
        int ID = new InversionDistanceHeuristic(puzzleLineSize).calculateValue(aStarObject);
        return Math.max(MD, ID);
    }
}
