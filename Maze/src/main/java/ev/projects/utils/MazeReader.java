package ev.projects.utils;

import ev.projects.maze.Maze;
import ev.projects.maze.Point;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MazeReader {

    public static Maze read(String filePath, Point startPoint, Point endPoint) throws IOException {
        File sourceFile = new File(filePath);
        BufferedImage image = prepareImage(ImageIO.read(sourceFile));
        int[][] mazeData = new int[image.getHeight()][image.getWidth()];
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int currentRGB = image.getRGB(x, y);
                if(currentRGB == -16777216) {
                    mazeData[y][x] = -1;
                }
                else {
                    mazeData[y][x] = Math.abs(currentRGB);
                }
            }
        }
        return new Maze(mazeData, startPoint, endPoint);
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
