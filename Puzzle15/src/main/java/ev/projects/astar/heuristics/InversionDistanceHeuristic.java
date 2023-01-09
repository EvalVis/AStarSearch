package ev.projects.astar.heuristics;

import ev.projects.heuristics.AStarHeuristic;
import ev.projects.heuristics.AStarObject;
import ev.projects.utils.Utils;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class InversionDistanceHeuristic extends AStarHeuristic<int[]> {

    private final int puzzleLineSize;

    @Override
    public int calculateValue(AStarObject<int[]> aStarObject) {
        int[] cells = aStarObject.getCurrentStateData();
        int verticalInversions = Utils.getInversionCountLeftRight(cells);
        int horizontalInversions = Utils.getInversionCountTopBottom(cells);
        int maxInversionsNumber = puzzleLineSize - 1;
        int verticalDistance = verticalInversions % maxInversionsNumber + verticalInversions / maxInversionsNumber;
        int horizontalDistance = horizontalInversions % maxInversionsNumber + horizontalInversions / maxInversionsNumber;
        return verticalDistance + horizontalDistance;
    }
}
