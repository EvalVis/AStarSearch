package com.programmersdiary.maze.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MazeConverter {

    public void convert(String filePath) throws IOException {
        File sourceFile = new File(filePath);
        BufferedImage image = prepareImage(ImageIO.read(sourceFile));
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                if(image.getRGB(x, y) != 0 && image.getRGB(x, y) != -16777216) {
                    System.out.println(image.getRGB(x, y));
                }
            }
        }
        ImageIO.write(image, "PNG", sourceFile);
    }

    private BufferedImage prepareImage(BufferedImage oldImage) {
        BufferedImage image = new BufferedImage(oldImage.getWidth(), oldImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        g.drawImage(oldImage, 0, 0, null);
        g.dispose();
        return image;
    }

}
