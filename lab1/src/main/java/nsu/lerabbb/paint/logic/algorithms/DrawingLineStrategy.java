package nsu.lerabbb.paint.logic.algorithms;

import java.awt.image.BufferedImage;

public interface DrawingLineStrategy {
    void setBuffer(BufferedImage bufferedImage);
    void drawLine(int startX, int startY, int endX, int endY);
    void drawPolygon(int x, int y);
    void drawStar(int x, int y);
}