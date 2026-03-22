package model;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

public class BitmapItem extends SlideItem {
    private BufferedImage bufferedImage;
    private final String imageName;

    public BitmapItem(int level, String name) throws IOException {
        super(level);
        imageName = name;
        bufferedImage = ImageIO.read(new File(imageName));
    }

    public String getName() {
        return imageName;
    }

    @Override
    public Rectangle getBoundingBox(Graphics g, ImageObserver observer, float scale, Style myStyle) {
        return new Rectangle((int) (myStyle.getIndent() * scale), 0,
                (int) (bufferedImage.getWidth(observer) * scale),
                ((int) (myStyle.getLeading() * scale)) +
                        (int) (bufferedImage.getHeight(observer) * scale));
    }

    @Override
    public void draw(int x, int y, float scale, Graphics g, Style myStyle, ImageObserver observer) {
        int width = x + (int) (myStyle.getIndent() * scale);
        int height = y + (int) (myStyle.getLeading() * scale);
        g.drawImage(bufferedImage, width, height, (int) (bufferedImage.getWidth(observer) * scale),
                (int) (bufferedImage.getHeight(observer) * scale), observer);
    }

    @Override
    public String toString() {
        return "BitmapItem[" + getLevel() + "," + imageName + "]";
    }
}
