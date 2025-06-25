package com.programmersdiary.maze.astar.heuristics;

import com.programmersdiary.astar.heuristics.AStarHeuristic;
import com.programmersdiary.astar.heuristics.AStarObject;
import com.programmersdiary.maze.maze.Maze;

public class ManhattanDistanceHeuristic implements AStarHeuristic<Maze> {
    @Override
    public int calculateValue(AStarObject<Maze> aStarObject) {
        Maze maze = aStarObject.getCurrentStateData();
        return Math.abs(maze.getCurrentPoint().x() - maze.getEndPoint().x())
                + Math.abs(maze.getCurrentPoint().y() - maze.getEndPoint().y());
    }
}
