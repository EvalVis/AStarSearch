package ev.projects.obstacles;

import ev.projects.trafficMap.Cell;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Wall extends Obstacle {

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
}
