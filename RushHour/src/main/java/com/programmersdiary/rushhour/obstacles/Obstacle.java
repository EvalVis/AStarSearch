package com.programmersdiary.rushhour.obstacles;

import com.programmersdiary.rushhour.trafficMap.Cell;

public abstract class Obstacle {

    protected Cell cell;
    public abstract boolean noObstacle();

    public Cell getCell() {
        return cell;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }
}
