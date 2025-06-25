package com.programmersdiary.puzzle15.astar.heuristics;

import com.programmersdiary.astar.heuristics.AStarHeuristic;
import com.programmersdiary.astar.heuristics.AStarObject;
import com.programmersdiary.puzzle15.utils.Utils;

public class InversionDistanceHeuristic implements AStarHeuristic<int[]> {

    @Override
    public int calculateValue(AStarObject<int[]> aStarObject) {
        int[] cells = aStarObject.getCurrentStateData();
        int size = (int) Math.sqrt(cells.length);
        Utils.Inversions inversions = Utils.getInversions(cells);
        int horizontalInversions = inversions.getHorizontalInversions();
        int verticalInversions = Math.abs(inversions.getVerticalInversions() - endVerticalInversions(size, size));
        int maxInversionsNumber = size - 1;
        int horizontalDistance = horizontalInversions % maxInversionsNumber + horizontalInversions / maxInversionsNumber;
        int verticalDistance = verticalInversions % maxInversionsNumber + verticalInversions / maxInversionsNumber;
        return horizontalDistance + verticalDistance;
    }

    private int endVerticalInversions(int width, int height) {
        return width * height * (width - 1) * (height - 1) / 4;
    }

}
