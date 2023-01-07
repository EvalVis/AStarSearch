package ev.projects.utils;

import ev.projects.astar.aStarObjects.TrafficMapAStarObject;
import ev.projects.astar.heuristics.ExitBlockingCarsCountHeuristic;
import ev.projects.search.AStarSolver;
import ev.projects.search.MoveSequence;
import ev.projects.trafficMap.TrafficMap;
import ev.projects.variants.classicAStar.ClassicAStarVariant;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import static ev.projects.utils.TrafficMapParser.parse6on6Map;

public class MapsToMovesDbConverter {

    public static void createMovesDatabaseFromMaps(String fileName) {
        int currentIndex = 0;
        String currentDirectory = System.getProperty("user.dir");
        File mapsFile = new File(currentDirectory + "/data/" + fileName);
        try(Scanner scanner = new Scanner(mapsFile)) {
            while(scanner.hasNextLine()) {
                currentIndex++;
                System.out.println(currentIndex);
                String[] row = scanner.nextLine().split(" ");
                String mapData = row[1];
                TrafficMap trafficMap = parse6on6Map(mapData);
                String solution = new AStarSolver<>(
                        new TrafficMapAStarObject(new MoveSequence(null, "START"), 0, trafficMap),
                        new ClassicAStarVariant<>(new ExitBlockingCarsCountHeuristic())).solve();
                String movesFileName = row[0] + "_" + row[2] + "_" + mapData + ".txt";
                try(FileWriter movesFileWriter =
                            new FileWriter(currentDirectory + "/data/" + movesFileName)) {
                    movesFileWriter.write(solution);
                } catch (IOException e) {
                    System.err.println("Failed to write to a move!");
                    throw new RuntimeException(e);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Maps file does not exist!");
            throw new RuntimeException();
        }
    }

    public static void createMultipleMovesDbFromMaps(String fullReadDirPath, String fullWriteDirPath) {
        File dir = new File(fullReadDirPath);
        File[] mapFiles = dir.listFiles();
        assert mapFiles != null;
        for(int i = 0; i < mapFiles.length; i++) {
            String fileName = mapFiles[i].getName();
            String map = fileName.split("_")[2].split("\\.")[0];
            int remaining = mapFiles.length - i;
            System.out.println(remaining);
            TrafficMap trafficMap = parse6on6Map(map);
            String solution = new AStarSolver<>(
                    new TrafficMapAStarObject(new MoveSequence(null, "START"), 0, trafficMap),
                    new ClassicAStarVariant<>(new ExitBlockingCarsCountHeuristic())).solve();
            try(FileWriter movesFileWriter =
                        new FileWriter(fullWriteDirPath + "/" + fileName)) {
                movesFileWriter.write(solution);
            } catch (IOException e) {
                System.err.println("Failed to write to a move!");
                throw new RuntimeException(e);
            }
        }
    }

}
