package ev.projects.astar.heuristics;

import ev.projects.heuristics.AStarHeuristic;
import ev.projects.heuristics.AStarObject;
import ev.projects.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.ImmutablePair;

@RequiredArgsConstructor
public class InversionDistanceHeuristic implements AStarHeuristic<int[]> {

    @Override
    public int calculateValue(AStarObject<int[]> aStarObject) {
        int[] cells = aStarObject.getCurrentStateData();
        int size = (int) Math.sqrt(cells.length);
        ImmutablePair<Integer, Integer> inversions = Utils.getInversions(cells);
        int horizontalInversions = inversions.getKey();
        int verticalInversions = Math.abs(
                inversions.getValue() - endVerticalInversions(size, size)
        );
        int maxInversionsNumber = size - 1;
        int horizontalDistance = horizontalInversions % maxInversionsNumber + horizontalInversions / maxInversionsNumber;
        int verticalDistance = verticalInversions % maxInversionsNumber + verticalInversions / maxInversionsNumber;
        return horizontalDistance + verticalDistance;
    }

    private int endVerticalInversions(int width, int height) {
        return width * height * (width - 1) * (height - 1) / 4;
    }

}
