package ev.projects.heuristics;

public interface AStarHeuristic<T> {

    int calculateValue(AStarObject<T> aStarObject);

}
