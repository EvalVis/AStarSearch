package ev.projects.astar.heuristics;

import ev.projects.heuristics.AStarHeuristic;
import ev.projects.heuristics.AStarObject;
import ev.projects.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.javatuples.Pair;

@RequiredArgsConstructor
public class InversionDistanceHeuristic implements AStarHeuristic<int[]> {

    @Override
    public int calculateValue(AStarObject<int[]> aStarObject) {
        int[] cells = aStarObject.getCurrentStateData();
        int size = (int) Math.sqrt(cells.length);
        Pair<Integer, Integer> inversions = Utils.getInversions(cells);
        int horizontalInversions = inversions.getValue0();
        int verticalInversions = Math.abs(
                inversions.getValue1() - endVerticalInversions(size, size)
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
