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

public class MazeReader {

    public static MazeAStarObject read(String filePath) throws IOException {
        File sourceFile = new File(filePath);
        BufferedImage image = prepareImage(ImageIO.read(sourceFile));
        int[][] mazeData = new int[image.getHeight()][image.getWidth()];
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int currentRGB = image.getRGB(x, y);
                if(currentRGB == Color.BLACK.getRGB()) {
                    mazeData[y][x] = -1;
                }
                else if(currentRGB == Color.WHITE.getRGB()){
                    mazeData[y][x] = 1;
                }
                else if(currentRGB == Color.GREEN.getRGB()){
                    mazeData[y][x] = 2;
                }
                else if(currentRGB == Color.MAGENTA.getRGB()){
                    mazeData[y][x] = 3;
                }
                else if(currentRGB == new Color(127, 51, 0).getRGB()){
                    mazeData[y][x] = 6;
                }
                else if(currentRGB == Color.BLUE.getRGB()){
                    mazeData[y][x] = 5;
                }
                else if(currentRGB == Color.YELLOW.getRGB()){
                    mazeData[y][x] = 4;
                }
                else {
                    throw new RuntimeException("Colour not expected!");
                }
            }
        }
        return new MazeAStarObject(
                new MoveSequence(null, "START"), 0,
                new Maze(
                        mazeData, new Point(1, 1),
                        new Point(image.getWidth() - 2, image.getHeight() - 2)
                )
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
