package com.programmersdiary.maze.maze;

public record Point(int x, int y) {

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
