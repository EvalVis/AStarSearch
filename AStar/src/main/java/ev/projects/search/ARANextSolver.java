package ev.projects.search;

import ev.projects.heuristics.AStarHeuristic;
import ev.projects.heuristics.AStarObject;
import lombok.Getter;

import java.util.*;
import java.util.stream.Stream;

public class ARANextSolver<T> {

    private double epsilon;
    private final double epsilonDecrease;
    private final AStarHeuristic<T> heuristic;
    private final AStarObject<T> startObject;
    private final AStarObject<T> endObject;
    private final HashMap<AStarObject<T>, Double> open = new HashMap<>();
    private final Set<AStarObject<T>> closed = new HashSet<>();
    private final Set<AStarObject<T>> incons = new HashSet<>();
    private final HashMap<AStarObject<T>, Integer> costs = new HashMap<>();

    @Getter
    private final List<AStarObject<T>> solutions = new ArrayList<>();

    public ARANextSolver(
            double epsilon, double epsilonDecrease, AStarHeuristic<T> heuristic,
            AStarObject<T> startObject, AStarObject<T> endObject
    ) {
        this.epsilon = epsilon;
        this.epsilonDecrease = epsilonDecrease;
        this.heuristic = heuristic;
        this.startObject = startObject;
        this.endObject = endObject;
    }

    public void solve() {
        costs.put(endObject, Integer.MAX_VALUE);
        costs.put(startObject, 0);
        open.put(startObject, fValue(startObject));
        improvePath();
        renewEpsilon();
        while(epsilon > 1) { // questionable statement.
            epsilon -= epsilonDecrease;
            moveFromInconsToOpen();
            incons.clear(); //clear incons?
            updateOpen();
            closed.clear();
            improvePath();
            renewEpsilon();
        }
    }

    private void renewEpsilon() {
//        double min = Stream.of(open.keySet(), incons)
//                .flatMapToDouble(
//                        a -> a.stream()
//                                .mapToDouble(b -> b.getGValue() + heuristic.calculateValue(b))
//                )
//                .min().getAsDouble();
//        epsilon = Math.min(epsilon, costs.get(endObject) / min);
        System.out.println("Epsilon: " + epsilon);
    }

    private void update(Set<AStarObject<T>> source) {
        List<AStarObject<T>> toRemove = new ArrayList<>(source);
        for(AStarObject<T> aso : toRemove) {
            replace(open, aso);
        }
    }

    private void replace(HashMap<AStarObject<T>, Double> source, AStarObject<T> objectToRemove) {
        source.remove(objectToRemove);
        source.put(objectToRemove, fValue(objectToRemove));
    }

    private void updateOpen() {
        update(open.keySet());
    }

    private void moveFromInconsToOpen() {
        update(incons);
    }

    private void improvePath() {
        AStarObject<T> min;
        while(open.getOrDefault(endObject, Double.MAX_VALUE) > open.get(min = findMin())) {
            open.remove(min);
            closed.add(min);
            for(AStarObject<T> neighbour : min.getNeighbours()) {
                if(!costs.containsKey(neighbour)) {
                    costs.put(neighbour, Integer.MAX_VALUE);
                }
                if(neighbour.getGValue() < costs.get(neighbour)) {
                    costs.remove(neighbour);
                    costs.put(neighbour, neighbour.getGValue());
                    if(!closed.contains(neighbour)) {
                        replace(open, neighbour);
                    }
                    else {
                        incons.add(neighbour);
                    }
                }
            }
        }
        for(AStarObject<T> aso : open.keySet()) {
            if(aso.equals(endObject)) {
                boolean canAdd = true;
                for(AStarObject<T> solution : solutions) {
                    if(solution.getSequenceOfMoves().equals(aso.getSequenceOfMoves())) {
                        canAdd = false;
                    }
                }
                if(canAdd) {
                    solutions.add(aso);
                    System.out.println(aso.getSequenceOfMoves());
                }
            }
        }
    }

    private AStarObject<T> findMin() {
        return Collections.min(open.entrySet(), Map.Entry.comparingByValue()).getKey();
    }

    private double fValue(AStarObject<T> aso) {
        return aso.getGValue() + epsilon * heuristic.calculateValue(aso);
    }

}
