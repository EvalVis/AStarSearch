package ev.projects.utils;

import ev.projects.astar.aStarObjects.MazeAStarObject;
import ev.projects.maze.Maze;
import ev.projects.maze.Point;
import ev.projects.search.MoveSequence;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class MazeDisplayer {


    public static void displaySolution(
            String sourceFilePath, MazeAStarObject mazeAStarObject
    ) throws IOException {
        File sourceFile = new File(sourceFilePath);
        Maze maze = mazeAStarObject.getCurrentStateData();
        BufferedImage image = prepareImage(ImageIO.read(sourceFile));
        Point currentPoint = maze.getEndPoint();
        MoveSequence parent = mazeAStarObject.getMoveSequence();
        while(parent != null) {
            if(currentPoint.equals(maze.getEndPoint())) {
                image.setRGB(currentPoint.getX(), currentPoint.getY(), Color.GREEN.getRGB());
            }
            else {
                image.setRGB(currentPoint.getX(), currentPoint.getY(), Color.RED.getRGB());
            }
            currentPoint = changeCurrentPoint(parent.getMove(), currentPoint);
            parent = parent.getParent();
        }
        image.setRGB(currentPoint.getX(), currentPoint.getY(), Color.GREEN.getRGB());
        File tempFile = new File(Files.createTempFile("solution_" + mazeAStarObject.getGValue() + "gValue_", ".png").toUri());
        ImageIO.write(
                image, "PNG", tempFile);
        Desktop.getDesktop().open(tempFile);
    }

    private static Point changeCurrentPoint(String move, Point currentPoint) {
        if(move.equals(Maze.MoveDirection.UP.toString())) {
            return new Point(currentPoint.getX(), currentPoint.getY() + 1);
        }
        else if(move.equals(Maze.MoveDirection.RIGHT.toString())) {
            return new Point(currentPoint.getX() - 1, currentPoint.getY());
        }
        else if(move.equals(Maze.MoveDirection.DOWN.toString())) {
            return new Point(currentPoint.getX(), currentPoint.getY() - 1);
        }
        else if(move.equals(Maze.MoveDirection.LEFT.toString())) {
            return new Point(currentPoint.getX() + 1, currentPoint.getY());
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
