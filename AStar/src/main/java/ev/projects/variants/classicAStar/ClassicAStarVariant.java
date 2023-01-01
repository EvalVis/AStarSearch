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
        double f1 = aso1.getGValue() + heuristic.calculateValue(aso1);
        double f2 = aso2.getGValue() + heuristic.calculateValue(aso2);
        return Double.compare(f1, f2);
    }
}
