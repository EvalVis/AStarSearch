package ev.projects.variants.classicAStar;

import ev.projects.heuristics.AStarHeuristic;
import ev.projects.heuristics.AStarObject;
import ev.projects.variants.AStarVariant;

public class ClassicAStarVariant<T> extends AStarVariant<T> {

    public ClassicAStarVariant(AStarHeuristic<T> heuristic) {
        super(heuristic);
    }

    @Override
    public int compare(AStarObject<T> aso1, AStarObject<T> aso2) {
        return Double.compare(getFValue(aso1), getFValue(aso2));
    }

    @Override
    public double getFValue(AStarObject<T> aso) {
        return aso.getGValue() + heuristic.calculateValue(aso);
    }
}
