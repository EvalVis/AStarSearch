package com.programmersdiary.puzzle15.utils;

import com.programmersdiary.puzzle15.astar.aStarObjects.PuzzleAStarObject;
import com.programmersdiary.astar.heuristics.AStarObject;
import com.programmersdiary.puzzle15.puzzle.Puzzle;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class PuzzleFileStorage {

    public static void generatePuzzlesAndWriteToFile(int amount, int size, String filePathWithFileName) {
        Set<Puzzle> puzzles = generatePuzzles(amount, size);
        writePuzzlesToFile(puzzles, filePathWithFileName);
    }

    private static Set<Puzzle> generatePuzzles(int amount, int size) {
        Set<Puzzle> puzzles = new HashSet<>();
        while(puzzles.size() != amount) {
            puzzles.add(new Puzzle(size));
        }
        return puzzles;
    }

    private static void writePuzzlesToFile(Set<Puzzle> puzzles, String filePathWithFileName) {
        File writeFile = new File(filePathWithFileName);
        try {
            writeFile.createNewFile();
            try(FileWriter fileWriter = new FileWriter(filePathWithFileName)) {
                for(Puzzle puzzle : puzzles) {
                    fileWriter.write(puzzle.toStringSeparateWithCommas() + System.lineSeparator());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Puzzle> readPuzzlesFromFile(String filePathWithFileName) {
        List<Puzzle> puzzles = new ArrayList<>();
        File readFile = new File(filePathWithFileName);
        try(Scanner fileReader = new Scanner(readFile)) {
            while(fileReader.hasNextLine()) {
                Puzzle readPuzzle = new Puzzle(fileReader.nextLine());
                puzzles.add(readPuzzle);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return puzzles;
    }

    public static List<AStarObject<int[]>> readPuzzleAStarObjectsFromFile(String filePathWithFileName) {
        List<Puzzle> readPuzzles = readPuzzlesFromFile(filePathWithFileName);
        List<AStarObject<int[]>> aStarObjects = new ArrayList<>();
        for(Puzzle readPuzzle : readPuzzles) {
            PuzzleAStarObject puzzleAStarObject = new PuzzleAStarObject(readPuzzle);
            aStarObjects.add(puzzleAStarObject);
        }
        return aStarObjects;
    }

}
