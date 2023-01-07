package ev.projects.astar.heuristics;

import ev.projects.heuristics.AStarHeuristic;
import ev.projects.heuristics.AStarObject;
import ev.projects.utils.Utils;

public class InversionDistanceHeuristic extends AStarHeuristic<int[]> {
    @Override
    public int calculateValue(AStarObject<int[]> aStarObject) {
        int[] cells = aStarObject.getCurrentStateData();
        int verticalInversions = Utils.getInversionCountLeftRight(cells);
        int horizontalInversions = Utils.getInversionCountTopBottom(cells);
        int verticalDistance = verticalInversions % 3 + verticalInversions / 3;
        int horizontalDistance = horizontalInversions % 3 + horizontalInversions / 3;
        return verticalDistance + horizontalDistance;
    }
}
