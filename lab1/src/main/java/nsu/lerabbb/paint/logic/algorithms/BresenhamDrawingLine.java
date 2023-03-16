package nsu.lerabbb.paint.logic.algorithms;

import nsu.lerabbb.paint.Logger;
import nsu.lerabbb.paint.logic.PaintConfig;

import java.awt.image.BufferedImage;

public class BresenhamDrawingLine implements DrawingLineStrategy {
    private static final float PI = 3.14f;
    private BufferedImage bufferedImage;

    public BresenhamDrawingLine(BufferedImage bufferedImage){
        this.bufferedImage = bufferedImage;
    }

    @Override
    public void setBuffer(BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
    }

    @Override
    public void drawLine(int startX, int startY, int endX, int endY) {
        int x = startX, y = startY, err;
        int signX = (endX - startX >= 0) ? 1 : -1;
        int signY = (endY - startY >= 0) ? 1 : -1;
        int absDX = Math.abs(endX - startX);
        int absDY = Math.abs(endY - startY);
        int end = Math.max(absDX, absDY);
        int colorRGB = PaintConfig.getInstance().getRGB();

        if (absDX == 0 && absDY == 0) {
            bufferedImage.setRGB(x, y, colorRGB);
        } else if (absDY <= absDX) {
            err = -absDX;
            for (int i = 0; i < end; i++) {
                x += signX;
                err += 2 * absDY;
                if (err > 0) {
                    err -= 2 * absDX;
                    y += signY;
                }
                if(x >= 0 && x < bufferedImage.getWidth() && y >= 0 && y < bufferedImage.getHeight()) {
                    bufferedImage.setRGB(x, y, colorRGB);
                }
            }
        } else {
            err = -absDY;
            for (int i = 0; i < end; i++) {
                y += signY;
                err += 2 * absDX;
                if (err > 0) {
                    err -= 2 * absDY;
                    x += signX;
                }
                if(x >= 0 && x < bufferedImage.getWidth() && y >= 0 && y < bufferedImage.getHeight()) {
                    bufferedImage.setRGB(x, y, colorRGB);
                }
            }
        }
    }

    @Override
    public void drawPolygon(int x, int y) {
        Logger.getInstance().info("started drawing polygon");

        int n = PaintConfig.getInstance().getPeaksNum();
        int radius = PaintConfig.getInstance().getRadius();
        double rotation = (PaintConfig.getInstance().getRotation() - 90) * PI / 180;

        double angle = rotation;
        int prevX = (int) (radius * Math.cos(angle) + x);
        int prevY = (int) (radius * Math.sin(angle) + y);
        int startX = prevX, startY = prevY;
        int nextX, nextY;

        for (int i = 1; i < n; i++) {
            angle = 2 * PI * i / n + rotation;
            nextX = (int) (radius * Math.cos(angle) + x);
            nextY = (int) (radius * Math.sin(angle) + y);

            drawLine(prevX, prevY, nextX, nextY);

            prevX = nextX;
            prevY = nextY;
        }
        drawLine(prevX, prevY, startX, startY);

        Logger.getInstance().info("ended drawing polygon");
    }

    @Override
    public void drawStar(int x, int y){
        Logger.getInstance().info("started drawing star");

        int n = PaintConfig.getInstance().getPeaksNum();
        int R = PaintConfig.getInstance().getRadius();
        int r = R/2;
        double rotation = (PaintConfig.getInstance().getRotation() + 90) * PI / 180;

        int prevX = (int) (x + R * Math.cos(rotation));
        int prevY = (int) (y - R * Math.sin(rotation));
        int nextX, nextY;
        int startX = prevX, startY = prevY;

        for(int i=1; i < 2*n; i++){
            double angle = PI * i / n + rotation;
            if(i%2 == 0){
                nextX =  (int) (x + R * Math.cos(angle));
                nextY =  (int) (y - R * Math.sin(angle));
            } else{
                nextX = (int) (x + r * Math.cos(angle));
                nextY = (int) (y - r * Math.sin(angle));
            }
            drawLine(prevX, prevY, nextX, nextY);

            prevX = nextX;
            prevY = nextY;
        }

        drawLine(prevX, prevY, startX, startY);

        Logger.getInstance().info("ended drawing polygon");
    }
}
