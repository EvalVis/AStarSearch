package ev.projects.search;

import ev.projects.heuristics.AStarObject;
import ev.projects.variants.ARAVariant;

import java.util.*;

public class ARASolver<T> {
    private final AStarObject<T> startObject;
    private final PriorityQueue<AStarObject<T>> open;
    private final Set<AStarObject<T>> closed = new HashSet<>();
    private final Set<AStarObject<T>> incons = new HashSet<>();
    private final HashMap<AStarObject<T>, Integer> costs = new HashMap<>();

    private final List<AStarObject<T>> solutions = new ArrayList<>();
    private final ARAVariant<T> araVariant;

    private int bestSolutionF = Integer.MAX_VALUE;

    public ARASolver(AStarObject<T> startObject, ARAVariant<T> araVariant) {
        this.startObject = startObject;
        this.open = new PriorityQueue<>(araVariant);
        this.araVariant = araVariant;
    }

    public void solve() {
        costs.put(startObject, 0);
        open.add(startObject);
        improvePath();
        while(araVariant.increasePreciseness()) {
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
        incons.clear();
    }

    private void improvePath() {
        while(!open.isEmpty() && araVariant.getFValue(open.peek()) < bestSolutionF) {
            AStarObject<T> min = open.remove();
            if(min.isSolved()) {
                bestSolutionF = min.getGValue();
                addSolution(min);
                break;
            }
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
            System.out.println("NEW solution at epsilon: " + araVariant.getEpsilon());
            System.out.println("G:" + candidateSolution.getGValue() + ", " + candidateSolution.getSequenceOfMoves());
        }
    }
}
