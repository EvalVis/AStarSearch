package ev.projects.variants.weightedAStar;

import ev.projects.heuristics.AStarHeuristic;
import ev.projects.heuristics.AStarObject;
import ev.projects.variants.AStarVariant;
import ev.projects.variants.weightedAStar.weights.LFunction;

public class WeightedAStarVariant<T> extends AStarVariant<T> {

    private final LFunction lFunction;

    public WeightedAStarVariant(AStarHeuristic<T> heuristic, LFunction lFunction) {
        super(heuristic);
        this.lFunction = lFunction;
    }

    @Override
    public int compare(AStarObject<T> aso1, AStarObject<T> aso2) {
        return Double.compare(getFValue(aso1), getFValue(aso2));
    }

    @Override
    public double getFValue(AStarObject<T> aso) {
        return aso.getGValue() + lFunction.calculateL() * heuristic.calculateValue(aso);
    }

}
