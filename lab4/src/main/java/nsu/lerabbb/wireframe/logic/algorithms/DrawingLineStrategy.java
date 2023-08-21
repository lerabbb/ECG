package nsu.lerabbb.wireframe.logic.algorithms;

import nsu.lerabbb.wireframe.logic.utils.Polygon;
import nsu.lerabbb.wireframe.logic.utils.Point;

import java.awt.*;
public interface DrawingLineStrategy {
    void drawLine(Point first, Point second, Color color);
    void drawLine(int startX, int startY, int endX, int endY, Color color);
    void drawRectangle(Polygon polygon);
}
