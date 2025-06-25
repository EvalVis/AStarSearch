package com.programmersdiary.maze.astar.aStarObjects;

import com.programmersdiary.astar.heuristics.AStarObject;
import com.programmersdiary.maze.maze.Maze;
import com.programmersdiary.astar.search.MoveSequence;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class MazeAStarObject extends AStarObject<Maze> {

    private final Maze maze;

    public MazeAStarObject(MoveSequence moveSequence, int gValue, Maze maze) {
        super(moveSequence, gValue);
        this.maze = maze;
    }

    @Override
    public Maze getCurrentStateData() {
        return maze;
    }

    @Override
    public boolean isSolved() {
        return maze.isSolved();
    }

    @Override
    public Set<AStarObject<Maze>> getNeighbours() {
        Set<AStarObject<Maze>> neighbours = new HashSet<>();
        Maze upMoveMaze = maze.move(Maze.MoveDirection.UP);
        Maze rightMoveMaze = maze.move(Maze.MoveDirection.RIGHT);
        Maze downMoveMaze = maze.move(Maze.MoveDirection.DOWN);
        Maze leftMoveMaze = maze.move(Maze.MoveDirection.LEFT);
        if(!upMoveMaze.equals(maze)) {
            neighbours.add(addNeighbour(upMoveMaze, Maze.MoveDirection.UP.toString()));
        }
        if(!rightMoveMaze.equals(maze)) {
            neighbours.add(addNeighbour(rightMoveMaze, Maze.MoveDirection.RIGHT.toString()));
        }
        if(!downMoveMaze.equals(maze)) {
            neighbours.add(addNeighbour(downMoveMaze, Maze.MoveDirection.DOWN.toString()));
        }
        if(!leftMoveMaze.equals(maze)) {
            neighbours.add(addNeighbour(leftMoveMaze, Maze.MoveDirection.LEFT.toString()));
        }
        return neighbours;
    }

    private AStarObject<Maze> addNeighbour(Maze neighbour, String move) {
        int nextGValue = this.gValue + neighbour.getCurrentValue();
        MoveSequence newMoveSequence = new MoveSequence(moveSequence, move);
        return new MazeAStarObject(newMoveSequence, nextGValue, neighbour);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MazeAStarObject that = (MazeAStarObject) o;
        return Objects.equals(maze, that.maze);
    }

    @Override
    public int hashCode() {
        return Objects.hash(maze);
    }

    @Override
    public String getSequenceOfMoves() {
        String sequence = moveSequence.toString();
        String[] moves = sequence.split(", ");
        if(moves.length < 3) {
            return sequence;
        }
        StringBuilder simplerSequence = new StringBuilder(moves[0]).append(", ");
        int repeatCount = 1;
        String lastRepeation = moves[1];
        for(int i = 2; i <= moves.length - 2; i++) {
            if(moves[i].equals(lastRepeation)) {
                repeatCount++;
            }
            else {
                simplerSequence.append(lastRepeation);
                if(repeatCount != 1) {
                    simplerSequence.append(" (").append(repeatCount).append(")");
                }
                simplerSequence.append(", ");
                repeatCount = 1;
                lastRepeation = moves[i];
            }
        }
        if(lastRepeation.equals(moves[moves.length - 2])) {
            simplerSequence.append(lastRepeation).append(", ");
        }
        simplerSequence.append(moves[moves.length - 1]);
        return simplerSequence.toString();
    }

    @Override
    public String toString() {
        return "MazeAStarObject{\n" +
                "\tmaze=" + maze +
                ",\n\tgValue=" + gValue +
                ",\n\tmoveSequence=" + getSequenceOfMoves() +
                "\n}";
    }
}
