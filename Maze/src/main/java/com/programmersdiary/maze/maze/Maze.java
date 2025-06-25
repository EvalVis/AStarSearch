package com.programmersdiary.maze.maze;

import java.util.Arrays;
import java.util.Objects;

public final class Maze {

    private final int[][] mazeData; // -1 is wall.
    private final Point currentPoint;

    private final Point endPoint;

    public Maze(int[][] mazeData, Point currentPoint, Point endPoint) {
        this.mazeData = mazeData;
        this.currentPoint = currentPoint;
        this.endPoint = endPoint;
    }

    public Point getCurrentPoint() {
        return currentPoint;
    }

    public Point getEndPoint() {
        return endPoint;
    }

    public int getCurrentValue() {
        return mazeData[currentPoint.y()][currentPoint.x()];
    }

    public boolean isSolved() {
        return currentPoint.equals(endPoint);
    }

    private boolean positionIsValid(Point proposedPoint) {
        if(proposedPoint.y() < 0
                || proposedPoint.x() < 0
                || proposedPoint.y() >= mazeData.length
                || proposedPoint.x() >= mazeData[proposedPoint.y()].length
        ) {
            return false;
        }
        return mazeData[proposedPoint.y()][proposedPoint.x()] != -1;
    }

    public Maze move(MoveDirection moveDirection) {
        Point proposedPoint = moveDirection.proposeMove(this);
        if(positionIsValid(proposedPoint)) {
            return new Maze(mazeData, proposedPoint, endPoint);
        }
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Maze maze = (Maze) o;
        return Arrays.deepEquals(mazeData, maze.mazeData)
                && Objects.equals(currentPoint, maze.currentPoint) &&
                Objects.equals(endPoint, maze.endPoint);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(currentPoint, endPoint);
        result = 31 * result + Arrays.deepHashCode(mazeData);
        return result;
    }

    @Override
    public String toString() {
        return "Maze{" +
                "\n\t\tmazeData={\n" + getMazeDataAsString() +
                "\t\t},\ncurrentPoint=" + currentPoint +
                ",\n endPoint=" + endPoint +
                "\n}";
    }

    private String getMazeDataAsString() {
        StringBuilder sb = new StringBuilder();
        for (int[] mazeDatum : mazeData) {
            sb.append("\t\t\t");
            for (int i : mazeDatum) {
                if (i == -1) {
                    sb.append("*");
                } else {
                    sb.append(i);
                }
                sb.append(",");
            }
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }

    public enum MoveDirection {
        UP {
            @Override
            public Point proposeMove(Maze maze) {
                return new Point(maze.currentPoint.x(), maze.currentPoint.y() - 1);
            }
        }, RIGHT {
            @Override
            public Point proposeMove(Maze maze) {
                return new Point(maze.currentPoint.x() + 1, maze.currentPoint.y());
            }
        }, DOWN {
            @Override
            public Point proposeMove(Maze maze) {
                return new Point(maze.currentPoint.x(), maze.currentPoint.y() + 1);
            }
        }, LEFT {
            @Override
            public Point proposeMove(Maze maze) {
                return new Point(maze.currentPoint.x() - 1, maze.currentPoint.y());
            }
        };

        public abstract Point proposeMove(Maze maze);
    }

}