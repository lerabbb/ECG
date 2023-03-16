package nsu.lerabbb.paint.logic.algorithms;

import java.awt.image.BufferedImage;

public interface FillingStrategy {
    void setBuffer(BufferedImage bufferedImage);
    void fill(int x, int y);
}