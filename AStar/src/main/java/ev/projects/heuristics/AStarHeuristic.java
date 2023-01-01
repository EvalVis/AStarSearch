package ev.projects.heuristics;

public abstract class AStarHeuristic<T> {

    public abstract int calculateValue(AStarObject<T> aStarObject);

}
