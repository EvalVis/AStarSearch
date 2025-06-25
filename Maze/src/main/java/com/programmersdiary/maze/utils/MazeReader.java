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

public class MazeReader {

    private static final int BLACK   = 0xFF000000;
    private static final int WHITE   = 0xFFFFFFFF;
    private static final int GREEN   = 0xFF00FF00;
    private static final int MAGENTA = 0xFFFF00FF;
    private static final int BROWN   = 0xFF7F3300;  // (Red: 127, Green: 51, Blue: 0)
    private static final int BLUE    = 0xFF0000FF;
    private static final int YELLOW  = 0xFFFFFF00;

    public static MazeAStarObject read(String filePath) throws IOException {
        File sourceFile = new File(filePath);
        BufferedImage image = prepareImage(ImageIO.read(sourceFile));
        int[][] mazeData = new int[image.getHeight()][image.getWidth()];
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int currentRGB = image.getRGB(x, y);
                mazeData[y][x] = switch (currentRGB) {
                    case BLACK -> -1;
                    case WHITE -> 1;
                    case GREEN -> 2;
                    case MAGENTA -> 3;
                    case BROWN -> 6;
                    case BLUE -> 5;
                    case YELLOW -> 4;
                    default -> throw new RuntimeException("Colour not expected!");
                };
            }
        }
        return new MazeAStarObject(
                new MoveSequence(null, "START"),
                0,
                new Maze(mazeData, new Point(1, 1), new Point(image.getWidth() - 2, image.getHeight() - 2))
        );
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
