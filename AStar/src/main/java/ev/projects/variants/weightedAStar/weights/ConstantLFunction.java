package ev.projects.variants.weightedAStar.weights;

import ev.projects.variants.weightedAStar.weights.LFunction;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ConstantLFunction extends LFunction {
    private double l;

    @Override
    protected double calculateL() {
        return l;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " l = " + l;
    }
}
