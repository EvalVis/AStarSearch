package ev.projects.obstacles;

import ev.projects.trafficMap.Cell;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public abstract class Obstacle {

    protected Cell cell;
    public abstract boolean noObstacle();

}
