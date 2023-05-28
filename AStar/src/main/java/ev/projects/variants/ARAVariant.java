package ev.projects.variants;

import ev.projects.heuristics.AStarHeuristic;
import ev.projects.heuristics.AStarObject;
import lombok.Getter;

public class ARAVariant<T> extends AStarVariant<T> {

    @Getter
    private double epsilon;
    private final double epsilonDecrease;
    @Getter
    private final double finalEpsilon;

    public ARAVariant(
            AStarHeuristic<T> heuristic, double epsilon,
            double epsilonDecrease, double finalEpsilon
    ) {
        super(heuristic);
        this.epsilon = epsilon;
        this.epsilonDecrease = epsilonDecrease;
        this.finalEpsilon = finalEpsilon;
    }

    public boolean increasePreciseness() {
        if(epsilon <= finalEpsilon) {
            epsilon = finalEpsilon;
            return false;
        }
        epsilon -= epsilonDecrease;
        return true;
    }

    public double getFValue(AStarObject<T> aso) {
        return aso.getGValue() + epsilon * heuristic.calculateValue(aso);
    }

    @Override
    public int compare(AStarObject<T> aso1, AStarObject<T> aso2) {
        return Double.compare(getFValue(aso1), getFValue(aso2));
    }
}
