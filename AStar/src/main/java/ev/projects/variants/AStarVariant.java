package ev.projects.variants;

import ev.projects.heuristics.AStarHeuristic;
import ev.projects.heuristics.AStarObject;

import java.util.Comparator;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

public abstract class AStarVariant<T> implements Comparator<AStarObject<T>> {

    protected AStarHeuristic<T> heuristic;

    public AStarVariant(AStarHeuristic<T> heuristic) {
        this.heuristic = heuristic;
    }

    @Override
    public abstract int compare(AStarObject<T> aso1, AStarObject<T> aso2);

    @Override
    public Comparator<AStarObject<T>> reversed() {
        return Comparator.super.reversed();
    }

    @Override
    public Comparator<AStarObject<T>> thenComparing(Comparator<? super AStarObject<T>> other) {
        return Comparator.super.thenComparing(other);
    }

    @Override
    public <U> Comparator<AStarObject<T>> thenComparing(
            Function<? super AStarObject<T>, ? extends U> keyExtractor, Comparator<? super U> keyComparator) {
        return Comparator.super.thenComparing(keyExtractor, keyComparator);
    }

    @Override
    public <U extends Comparable<? super U>> Comparator<AStarObject<T>> thenComparing(
            Function<? super AStarObject<T>, ? extends U> keyExtractor) {
        return Comparator.super.thenComparing(keyExtractor);
    }

    @Override
    public Comparator<AStarObject<T>> thenComparingInt(ToIntFunction<? super AStarObject<T>> keyExtractor) {
        return Comparator.super.thenComparingInt(keyExtractor);
    }

    @Override
    public Comparator<AStarObject<T>> thenComparingLong(ToLongFunction<? super AStarObject<T>> keyExtractor) {
        return Comparator.super.thenComparingLong(keyExtractor);
    }

    @Override
    public Comparator<AStarObject<T>> thenComparingDouble(ToDoubleFunction<? super AStarObject<T>> keyExtractor) {
        return Comparator.super.thenComparingDouble(keyExtractor);
    }

}
