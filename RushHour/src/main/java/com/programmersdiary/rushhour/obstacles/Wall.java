package com.programmersdiary.rushhour.obstacles;

import com.programmersdiary.rushhour.trafficMap.Cell;

public class Wall extends Obstacle {

    public Wall() {
    }

    public Wall(Wall wall) {
        cell = new Cell(wall.cell, this);
    }

    private Cell cell;

    @Override
    public boolean noObstacle() {
        return false;
    }

    @Override
    public String toString() {
        return "!";
    }

    @Override
    public Cell getCell() {
        return cell;
    }

    @Override
    public void setCell(Cell cell) {
        this.cell = cell;
    }
}
