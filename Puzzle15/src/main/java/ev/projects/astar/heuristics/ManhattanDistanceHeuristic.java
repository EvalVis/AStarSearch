package ev.projects.astar.heuristics;

import ev.projects.heuristics.AStarHeuristic;
import ev.projects.heuristics.AStarObject;
import ev.projects.utils.Utils;

import java.util.List;

public class ManhattanDistanceHeuristic extends AStarHeuristic<int[]> {

    @Override
    public int calculateValue(AStarObject<int[]> aStarObject) {
        int[] cells = aStarObject.getCurrentStateData();
        int lineSize = (int) Math.sqrt(cells.length);
        int totalDistance = 0;
        for(int i = 0; i < cells.length; i++) {
            Utils.StartEndCoordinates coordinates = Utils.StartEndCoordinates.getCoordinates(i, cells[i], lineSize);
            totalDistance += Math.abs((coordinates.getStartX() - coordinates.getEndX()))
                    + Math.abs((coordinates.getStartY() - coordinates.getEndY()));
        }
        return totalDistance;
    }
}
