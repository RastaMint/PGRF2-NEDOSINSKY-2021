package utery_17_25_c04.rasterize;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Optional;

public class RasterBufferedImage implements Raster<Integer> {

    private final BufferedImage img;
    private int clearColor;

    public RasterBufferedImage(int width, int height) {
        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    }

    public void repaint(Graphics graphics) {
        graphics.drawImage(img, 0, 0, null);
    }

    public void draw(RasterBufferedImage raster) {
        Graphics g = getGraphics();
        g.setColor(new Color(clearColor));
        g.fillRect(0, 0, getWidth(), getHeight());
        g.drawImage(raster.img, 0, 0, null);
    }

    public BufferedImage getImg() {
        return img;
    }

    public Graphics getGraphics() {
        return img.getGraphics();
    }

    @Override
    public Optional<Integer> getElement(int x, int y) {
        if (x >= 0 && y >= 0 && x < getWidth() && y < getHeight()) {
            return Optional.of(img.getRGB(x, y));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public void setElement(int x, int y, Integer color) {
        if (x >= 0 && y >= 0 && x < getWidth() && y < getHeight()) {
            img.setRGB(x, y, color);
        }
    }

    @Override
    public void clear() {
        Graphics g = getGraphics();
        g.setColor(new Color(clearColor));
        g.fillRect(0, 0, img.getWidth(), img.getHeight());
    }

    @Override
    public void setClearValue(Integer clearValue) {
        this.clearColor = clearValue;
    }

    @Override
    public int getWidth() {
        return img.getWidth();
    }

    @Override
    public int getHeight() {
        return img.getHeight();
    }

}
