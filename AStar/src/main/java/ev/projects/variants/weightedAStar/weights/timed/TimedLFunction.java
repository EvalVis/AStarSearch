package ev.projects.variants.weightedAStar.weights.timed;

import ev.projects.variants.weightedAStar.weights.LFunction;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class TimedLFunction extends LFunction {

    protected long startTimeNano;

    protected int getSecondsPassed() {
        double currentTimeNano = System.nanoTime();
        double timePassedNano = currentTimeNano - startTimeNano;
        return (int) (timePassedNano / 1_000_000_000);
    }

}
