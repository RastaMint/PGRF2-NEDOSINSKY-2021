package utery_17_25_c04.rasterize;

import java.awt.*;

@Deprecated
public class LineRasterizerGraphics {

    private final RasterBufferedImage imageBuffer;

    public LineRasterizerGraphics(Raster<Integer> imageBuffer) {
        this.imageBuffer = ((RasterBufferedImage) imageBuffer);
    }

    public void rasterize(int x1, int y1, int x2, int y2, Color color) {
        Graphics g = imageBuffer.getGraphics();
        g.setColor(color);
        g.drawLine(x1, y1, x2, y2);
    }

}
