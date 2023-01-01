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
        double l = lFunction.getL();
        double f1 = (1 - l) * aso1.getGValue() + l * heuristic.calculateValue(aso1);
        double f2 = (1 - l) * aso2.getGValue() + l * heuristic.calculateValue(aso2);
        return Double.compare(f1, f2);
    }

}
