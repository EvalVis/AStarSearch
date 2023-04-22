package ev.projects.search;

import ev.projects.heuristics.AStarObject;
import ev.projects.variants.ARAVariant;
import lombok.Getter;

import java.util.*;
import java.util.function.Supplier;

// decrease change of epsilon when it is small.
// calculate time drop between epsilon.
public class ARASolver<T> {
    private final AStarObject<T> startObject;
    private final PriorityQueue<AStarObject<T>> open;
    private final Set<AStarObject<T>> closed = new HashSet<>();
    private final Set<AStarObject<T>> incons = new HashSet<>();
    private final HashMap<AStarObject<T>, Integer> costs = new HashMap<>();

    @Getter
    private final List<AStarObject<T>> solutions = new ArrayList<>();

    private final Supplier<Boolean> canImprove;

    public ARASolver(AStarObject<T> startObject, ARAVariant<T> araVariant) {
        this.startObject = startObject;
        this.open = new PriorityQueue<>(araVariant);
        canImprove = araVariant::increasePreciseness;
    }

    public void solve() {
        costs.put(startObject, 0);
        open.add(startObject);
        improvePath();
        while(canImprove.get()) {
            moveFromInconsToOpen();
            updateOpen();
            closed.clear();
            improvePath();
        }
    }

    private void updateOpen() {
        List<AStarObject<T>> toRemove = new ArrayList<>(open);
        for(AStarObject<T> aso : toRemove) {
            open.remove(aso);
            open.add(aso);
        }
    }

    private void moveFromInconsToOpen() {
        for(AStarObject<T> aso : incons) {
            open.remove(aso);
            open.add(aso);
        }
        incons.clear(); //clear incons?
    }

    private void improvePath() {
        while(!open.isEmpty()) {
            AStarObject<T> min = open.remove();
            if(min.isSolved()) {
                open.add(min);
                addSolution(min);
                break;
            }
            open.remove(min);
            closed.remove(min);
            closed.add(min);
            for(AStarObject<T> neighbour : min.getNeighbours()) {
                if(!costs.containsKey(neighbour)) {
                    costs.put(neighbour, Integer.MAX_VALUE);
                }
                if(neighbour.getGValue() < costs.get(neighbour)) {
                    costs.remove(neighbour);
                    costs.put(neighbour, neighbour.getGValue());
                    if(!closed.contains(neighbour)) {
                        open.remove(neighbour);
                        open.add(neighbour);
                    }
                    else {
                        incons.remove(neighbour);
                        incons.add(neighbour);
                    }
                }
            }
        }
    }

    private void addSolution(AStarObject<T> candidateSolution) {
        if(solutions.stream().noneMatch(
                solution -> solution.getSequenceOfMoves().equals(candidateSolution.getSequenceOfMoves()))
        ) {
            solutions.add(candidateSolution);
            System.out.println(candidateSolution.getSequenceOfMoves());
        }

    }

}
