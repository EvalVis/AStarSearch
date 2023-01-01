package ev.projects.astar.heuristics;

import ev.projects.heuristics.AStarHeuristic;
import ev.projects.heuristics.AStarObject;
import ev.projects.utils.Utils;

import java.util.List;

public class ManhattanDistanceHeuristic extends AStarHeuristic<Integer> {

    @Override
    public int calculateValue(AStarObject<Integer> aStarObject) {
        List<Integer> cells = aStarObject.getCurrentStateData();
        int lineSize = (int) Math.sqrt(cells.size());
        int totalDistance = 0;
        for(int i = 0; i < cells.size(); i++) {
            Utils.StartEndCoordinates coordinates = Utils.StartEndCoordinates.getCoordinates(i, cells.get(i), lineSize);
            totalDistance += Math.abs((coordinates.getStartX() - coordinates.getEndX()))
                    + Math.abs((coordinates.getStartY() - coordinates.getEndY()));
        }
        return totalDistance;
    }
}
