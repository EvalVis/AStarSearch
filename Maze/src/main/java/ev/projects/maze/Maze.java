package ev.projects.maze;

import lombok.Getter;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;

@Getter
public final class Maze {

    private final int[][] mazeData; // -1 is wall.
    private final Point currentPoint;

    private final Point endPoint;

    public Maze(int[][] mazeData, Point currentPoint, Point endPoint) {
        this.mazeData = mazeData;
        this.currentPoint = currentPoint;
        this.endPoint = endPoint;
    }

    public int getHeight() {
        return mazeData.length;
    }

    public int getWidth() {
        return Arrays.stream(mazeData).max(Comparator.comparingInt(a -> a.length)).get().length;
    }

    public int getCurrentValue() {
        return mazeData[currentPoint.getY()][currentPoint.getX()];
    }

    public boolean isSolved() {
        return currentPoint.equals(endPoint);
    }

    private boolean positionIsValid(Point proposedPoint) {
        if(proposedPoint.getY() < 0
                || proposedPoint.getX() < 0
                || proposedPoint.getY() >= mazeData.length
                || proposedPoint.getX() >= mazeData[proposedPoint.getY()].length
        ) {
            return false;
        }
        return mazeData[proposedPoint.getY()][proposedPoint.getX()] != -1;
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
        for(int y = 0; y < mazeData.length; y++) {
            sb.append("\t\t\t");
            for(int x = 0; x < mazeData[y].length; x++) {
                if(mazeData[y][x] == -1) {
                    sb.append("*");
                }
                else {
                    sb.append(mazeData[y][x]);
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
                return new Point(maze.currentPoint.getX(), maze.currentPoint.getY() - 1);
            }
        }, RIGHT {
            @Override
            public Point proposeMove(Maze maze) {
                return new Point(maze.currentPoint.getX() + 1, maze.currentPoint.getY());
            }
        }, DOWN {
            @Override
            public Point proposeMove(Maze maze) {
                return new Point(maze.currentPoint.getX(), maze.currentPoint.getY() + 1);
            }
        }, LEFT {
            @Override
            public Point proposeMove(Maze maze) {
                return new Point(maze.currentPoint.getX() - 1, maze.currentPoint.getY());
            }
        };

        public abstract Point proposeMove(Maze maze);
    }

}