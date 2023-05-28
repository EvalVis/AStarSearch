package ev.projects.astar.heuristics;

import ev.projects.astar.heuristics.blockChainHeuristics.BlockChainHeuristic;
import ev.projects.heuristics.AStarHeuristic;
import ev.projects.heuristics.AStarObject;
import ev.projects.trafficMap.TrafficMap;

public class ExitBlockChain2DistanceHeuristic implements AStarHeuristic<TrafficMap> {

    private final int maxMoveCount;

    public ExitBlockChain2DistanceHeuristic(int maxMoveCount) {
        this.maxMoveCount = maxMoveCount;
    }

    @Override
    public int calculateValue(AStarObject<TrafficMap> aStarObject) {
        TrafficMap trafficMap = aStarObject.getCurrentStateData();
        if(trafficMap.getFinishX() - trafficMap.getMainCar().getFrontX() < 3) {
            return new BlockChainHeuristic(maxMoveCount).calculateValue(aStarObject);
        }
        return new ExitBlockingCarsCountHeuristic().calculateValue(aStarObject);
    }
}
