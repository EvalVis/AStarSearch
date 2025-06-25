package com.programmersdiary.maze.utils;

import com.programmersdiary.maze.astar.aStarObjects.MazeAStarObject;
import com.programmersdiary.maze.maze.Maze;
import com.programmersdiary.maze.maze.Point;
import com.programmersdiary.astar.search.MoveSequence;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class MazeDisplayer {

    public static void displaySolution(String sourceFilePath, MazeAStarObject mazeAStarObject) throws IOException {
        File sourceFile = new File(sourceFilePath);
        Maze maze = mazeAStarObject.getCurrentStateData();
        BufferedImage image = prepareImage(ImageIO.read(sourceFile));
        Point currentPoint = maze.getEndPoint();
        MoveSequence parent = mazeAStarObject.getMoveSequence();
        while(parent != null) {
            if(currentPoint.equals(maze.getEndPoint())) {
                image.setRGB(currentPoint.x(), currentPoint.y(), Color.GREEN.getRGB());
            }
            else {
                image.setRGB(currentPoint.x(), currentPoint.y(), Color.RED.getRGB());
            }
            currentPoint = changeCurrentPoint(parent.move(), currentPoint);
            parent = parent.parent();
        }
        image.setRGB(currentPoint.x(), currentPoint.y(), Color.GREEN.getRGB());
        File tempFile = new File(Files.createTempFile("solution_" + mazeAStarObject.getGValue() + "gValue_", ".png").toUri());
        ImageIO.write(
                image, "PNG", tempFile);
        Desktop.getDesktop().open(tempFile);
    }

    private static Point changeCurrentPoint(String move, Point currentPoint) {
        if(move.equals(Maze.MoveDirection.UP.toString())) {
            return new Point(currentPoint.x(), currentPoint.y() + 1);
        }
        else if(move.equals(Maze.MoveDirection.RIGHT.toString())) {
            return new Point(currentPoint.x() - 1, currentPoint.y());
        }
        else if(move.equals(Maze.MoveDirection.DOWN.toString())) {
            return new Point(currentPoint.x(), currentPoint.y() - 1);
        }
        else if(move.equals(Maze.MoveDirection.LEFT.toString())) {
            return new Point(currentPoint.x() + 1, currentPoint.y());
        }
        return currentPoint;
    }

    private static BufferedImage prepareImage(BufferedImage oldImage) {
        BufferedImage image = new BufferedImage(
                oldImage.getWidth(), oldImage.getHeight(),
                BufferedImage.TYPE_INT_ARGB
        );
        Graphics2D g = image.createGraphics();
        g.drawImage(oldImage, 0, 0, null);
        g.dispose();
        return image;
    }

}
