package ev.projects.search;

import ev.projects.heuristics.AStarObject;
import ev.projects.variants.AStarVariant;

import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

public class AStarSolver {

    private final PriorityQueue<AStarObject> frontier;
    private final Set<AStarObject> explored = new HashSet<>();
    private final HashMap<AStarObject, Integer> costs = new HashMap<>();
    private final AStarObject startingObject;

    public AStarSolver(AStarObject startingObject, AStarVariant aStarVariant) {
        this.startingObject = startingObject;
        frontier = new PriorityQueue<>(aStarVariant);
    }

    public void solve() {
        frontier.add(startingObject);
        costs.put(startingObject, 0);
        while(!frontier.isEmpty()) {
            AStarObject current = frontier.remove();
            isSolved(current);
            Set<AStarObject> neighbours = current.getNeighbours();
            for(AStarObject neighbour : neighbours) {
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
    }

    private boolean isSolved(AStarObject current) {
        if(current.isSolved()) {
            System.out.println("SOLVED");
            frontier.clear();
            explored.clear();
            costs.clear();
            return true;
        }
        return false;
    }

}
