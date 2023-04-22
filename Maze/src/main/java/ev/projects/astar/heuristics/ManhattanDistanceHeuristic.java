package ev.projects.astar.heuristics;

import ev.projects.heuristics.AStarHeuristic;
import ev.projects.heuristics.AStarObject;
import ev.projects.maze.Maze;

public class ManhattanDistanceHeuristic extends AStarHeuristic<Maze> {
    @Override
    public int calculateValue(AStarObject<Maze> aStarObject) {
        Maze maze = aStarObject.getCurrentStateData();
        return Math.abs(maze.getCurrentPoint().getX() - maze.getEndPoint().getX())
                + Math.abs(maze.getCurrentPoint().getY() - maze.getEndPoint().getY());
    }
}
