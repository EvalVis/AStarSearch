package ev.projects.variants;

import ev.projects.heuristics.AStarHeuristic;
import ev.projects.heuristics.AStarObject;

import java.util.Comparator;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

public abstract class AStarVariant implements Comparator<AStarObject> {

    protected AStarHeuristic heuristic;

    public AStarVariant(AStarHeuristic heuristic) {
        this.heuristic = heuristic;
    }

    @Override
    public abstract int compare(AStarObject aso1, AStarObject aso2);

    @Override
    public Comparator<AStarObject> reversed() {
        return Comparator.super.reversed();
    }

    @Override
    public Comparator<AStarObject> thenComparing(Comparator<? super AStarObject> other) {
        return Comparator.super.thenComparing(other);
    }

    @Override
    public <U> Comparator<AStarObject> thenComparing(
            Function<? super AStarObject, ? extends U> keyExtractor, Comparator<? super U> keyComparator) {
        return Comparator.super.thenComparing(keyExtractor, keyComparator);
    }

    @Override
    public <U extends Comparable<? super U>> Comparator<AStarObject> thenComparing(
            Function<? super AStarObject, ? extends U> keyExtractor) {
        return Comparator.super.thenComparing(keyExtractor);
    }

    @Override
    public Comparator<AStarObject> thenComparingInt(ToIntFunction<? super AStarObject> keyExtractor) {
        return Comparator.super.thenComparingInt(keyExtractor);
    }

    @Override
    public Comparator<AStarObject> thenComparingLong(ToLongFunction<? super AStarObject> keyExtractor) {
        return Comparator.super.thenComparingLong(keyExtractor);
    }

    @Override
    public Comparator<AStarObject> thenComparingDouble(ToDoubleFunction<? super AStarObject> keyExtractor) {
        return Comparator.super.thenComparingDouble(keyExtractor);
    }

}
