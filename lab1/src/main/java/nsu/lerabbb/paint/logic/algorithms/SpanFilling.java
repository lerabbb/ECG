package nsu.lerabbb.paint.logic.algorithms;

import nsu.lerabbb.paint.Logger;
import nsu.lerabbb.paint.logic.PaintConfig;
import nsu.lerabbb.paint.logic.Point;

import java.awt.image.BufferedImage;
import java.util.Stack;

public class SpanFilling implements FillingStrategy {
    private final Stack<Point> spanStack;
    private BufferedImage bufferedImage;
    private int replaceableRgb;
    private int fillingRgb;

    public SpanFilling(BufferedImage bufferedImage){
        this.bufferedImage = bufferedImage;
        this.spanStack = new Stack<>();
    }

    @Override
    public void setBuffer(BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
    }

    @Override
    public void fill(int x, int y) {
        if (x < 0 || x >= bufferedImage.getWidth() || y < 0 || y >= bufferedImage.getHeight()) {
            return;
        }
        Logger.getInstance().info("span filling started");
        spanStack.push(new Point(x, y));

        fillingRgb = PaintConfig.getInstance().getRGB();
        replaceableRgb = bufferedImage.getRGB(x, y);
        int rightX, leftX, spanY, i;
        boolean isUpSpanAdded, isDownSpanAdded;

        while (!spanStack.isEmpty()) {
            Point span = spanStack.pop();
            leftX = span.getX();
            spanY = span.getY();
            rightX = leftX;
            while (checkBounds(leftX - 1, spanY)) {
                bufferedImage.setRGB(leftX - 1, spanY, fillingRgb);
                leftX--;
            }
            while (checkBounds(rightX, spanY)) {
                bufferedImage.setRGB(rightX, spanY, fillingRgb);
                rightX++;
            }
            isUpSpanAdded = false;
            isDownSpanAdded = false;
            for (i = leftX; i < rightX; i++) {
                isUpSpanAdded = checkNearSpan(i, spanY + 1, isUpSpanAdded);
                isDownSpanAdded = checkNearSpan(i, spanY - 1, isDownSpanAdded);
            }
        }
        Logger.getInstance().info("span filling ended");
    }

    private boolean checkNearSpan(int x, int y, boolean flag) {
        if (!checkBounds(x, y) || bufferedImage.getRGB(x, y) == fillingRgb) {
            return false;
        }
        if(flag){
            return false;
        }
        spanStack.push(new Point(x, y));
        return true;
    }

    private boolean checkBounds(int x, int y){
        return x >= 0 &&  x < bufferedImage.getWidth() &&
                y >= 0 && y < bufferedImage.getHeight() &&
                bufferedImage.getRGB(x, y) == replaceableRgb;
    }
}
