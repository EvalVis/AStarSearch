package ev.projects.main;

import ev.projects.astar.aStarObjects.MazeAStarObject;
import ev.projects.astar.heuristics.ManhattanDistanceHeuristic;
import ev.projects.heuristics.AStarObject;
import ev.projects.maze.Maze;
import ev.projects.maze.Point;
import ev.projects.search.ARANextSolver;
import ev.projects.search.MoveSequence;
import ev.projects.utils.MazeDisplayer;
import ev.projects.utils.MazeReader;

import java.io.IOException;

public class Main
{

    public static final String filePath = "C:/Users/vieva/Desktop/kursinioProjektas/AStarSearch/Maze/src/main/resources/mazes/examplemaze2harder.png";

    public static void main(String[] args) throws IOException, InterruptedException {
        //new MazeConverter(1000000).convert("C:/Users/vieva/Desktop/kursinioProjektas/AStarSearch/Maze/src/main/resources/mazes/maze4.png");
        Maze maze = MazeReader.read(filePath, new Point(31, 0), new Point(47, 65));
        //Maze maze = MazeReader.read(filePath, new Point(24, 0), new Point(41, 65));
        //Maze maze = MazeReader.read(filePath, new Point(313, 1), new Point(330, 642));
        MazeAStarObject startMazeAStarObject = new MazeAStarObject(
                new MoveSequence(null, "START"), 0, maze
        );
        MazeAStarObject endMazeAStarObject = new MazeAStarObject(
                new MoveSequence(null, "START"), 0,
                MazeReader.read(filePath, new Point(47, 65), new Point(47, 65))
        );
//        for(AStarObject<Maze> solvedMaze : mazeAStarObjects) {
//            System.out.println(solvedMaze);
//            MazeDisplayer.displaySolution(filePath, (MazeAStarObject) solvedMaze);
//        }
        //System.out.println(new AStarSolver<>(startMazeAStarObject, new WeightedAStarVariant<>(new ManhattanDistanceHeuristic(), new ConstantLFunction(0.5))).solve());
        ARANextSolver<Maze> solver = new ARANextSolver<>(100, 0.1, new ManhattanDistanceHeuristic(), startMazeAStarObject, endMazeAStarObject);
        solver.solve();
        for(AStarObject<Maze> aso : solver.getSolutions()) {
            System.out.println(aso.getSequenceOfMoves());
            MazeDisplayer.displaySolution(filePath, (MazeAStarObject) aso);
        }
    }
}
