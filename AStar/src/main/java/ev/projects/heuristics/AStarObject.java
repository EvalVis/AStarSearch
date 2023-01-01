package ev.projects.heuristics;

import java.util.List;
import java.util.Set;

public abstract class AStarObject {

    public abstract int getGValue();

    public abstract <T> List<T> getCurrentStateData();

    public abstract boolean isSolved();

    public abstract Set<AStarObject> getNeighbours();


}
