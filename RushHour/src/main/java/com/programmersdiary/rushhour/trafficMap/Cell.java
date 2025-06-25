package com.programmersdiary.rushhour.trafficMap;

import com.programmersdiary.rushhour.obstacles.Obstacle;

import java.util.Objects;

public class Cell {

    private Obstacle occupant;
    private final int x;
    private final int y;

    public Cell(Cell cell, Obstacle obstacle) {
        x = cell.getX();
        y = cell.getY();
        occupant = obstacle;
    }

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void transferToAnotherCell(Cell cell) {
        if(occupant == null) {
            throw new RuntimeException("No occupant to transfer!");
        }
        cell.setOccupant(occupant);
        occupant = null;
    }

    public void setOccupant(Obstacle occupant) {
        if(occupant != null && this.occupant != null) {
            throw new RuntimeException("Cannot place a new occupant since cell already has an occupant!");
        }
        this.occupant = occupant;
        this.occupant.setCell(this);
    }

    @Override
    public String toString() {
        return occupant != null ? occupant.toString() : "-";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cell cell = (Cell) o;
        if(occupant == null) {
            return x == cell.x && y == cell.y && cell.occupant == null;
        }
        if(cell.occupant == null) {
            return false;
        }
        return x == cell.x && y == cell.y && Objects.equals(occupant, cell.occupant);
    }

    @Override
    public int hashCode() {
        return Objects.hash(occupant, x, y);
    }

    public Obstacle getOccupant() {
        return occupant;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
