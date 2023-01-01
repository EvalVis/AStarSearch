package ev.projects.variants.weightedAStar.weights;

public abstract class LFunction {

    protected abstract double calculateL();

    public double getL() {
        double l = calculateL();
        l = Math.max(0, l);
        l = Math.min(1, l);
        return l;
    }

}
