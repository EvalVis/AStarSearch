package ev.projects.variants.weightedAStar.weights.timed;

public class LinearTimedLFunction extends TimedLFunction {

    private final double a;
    private final double b;

    public LinearTimedLFunction(long startTimeNano, double a, double b) {
        super(startTimeNano);
        this.a = a;
        this.b = b;
    }

    @Override
    protected double calculateL() {
        return a * getSecondsPassed() + b;
    }


}
