package ev.projects.variants;

import ev.projects.heuristics.AStarHeuristic;
import ev.projects.heuristics.AStarObject;

public class ARAVariant<T> extends AStarVariant<T> {

    private double epsilon;
    private final double epsilonDecrease;

    public ARAVariant(AStarHeuristic<T> heuristic, double epsilon, double epsilonDecrease) {
        super(heuristic);
        this.epsilon = epsilon;
        this.epsilonDecrease = epsilonDecrease;
    }

    public boolean increasePreciseness() {
        if(epsilon <= 1) {
            epsilon = 1;
            return false;
        }
        epsilon -= epsilonDecrease;
        return true;
    }

    @Override
    public int compare(AStarObject<T> aso1, AStarObject<T> aso2) {
        double f1 = aso1.getGValue() + epsilon * heuristic.calculateValue(aso1);
        double f2 = aso2.getGValue() + epsilon * heuristic.calculateValue(aso2);
        return Double.compare(f1, f2);
    }
}
