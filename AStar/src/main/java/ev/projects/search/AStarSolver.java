package ev.projects.search;

import ev.projects.heuristics.AStarObject;
import ev.projects.variants.AStarVariant;

import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

public class AStarSolver<T> {

    private final PriorityQueue<AStarObject<T>> frontier;
    private final Set<AStarObject<T>> explored = new HashSet<>();
    private final HashMap<AStarObject<T>, Integer> costs = new HashMap<>();
    private final AStarObject<T> startingObject;

    public AStarSolver(AStarObject<T> startingObject, AStarVariant<T> aStarVariant) {
        this.startingObject = startingObject;
        frontier = new PriorityQueue<>(aStarVariant);
    }

    public AStarObject<T> solve() {
        frontier.add(startingObject);
        costs.put(startingObject, 0);
        while(!frontier.isEmpty()) {
            AStarObject<T> current = frontier.remove();
            if(current.isSolved()) {
                return current;
            }
            Set<AStarObject<T>> neighbours = current.getNeighbours();
            for(AStarObject<T> neighbour : neighbours) {
                if(frontier.contains(neighbour) && costs.get(neighbour) <= current.getGValue()) {
                    continue;
                }
                else if(explored.contains(neighbour)) {
                    if(costs.get(neighbour) <= current.getGValue()) {
                        continue;
                    }
                    explored.remove(neighbour);
                }
                frontier.add(neighbour);
                costs.put(neighbour, current.getGValue());
            }
            explored.add(current);
        }
        return null;
    }

}
