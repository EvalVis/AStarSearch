package ev.projects.trafficMap;

import ev.projects.obstacles.Obstacle;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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

}
