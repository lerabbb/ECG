package nsu.lerabbb.editor.logic.filter.border;

import nsu.lerabbb.editor.Logger;
import nsu.lerabbb.editor.logic.Config;
import nsu.lerabbb.editor.logic.Utils;
import nsu.lerabbb.editor.logic.filter.Filter;

import java.awt.*;
import java.awt.image.BufferedImage;

import static nsu.lerabbb.editor.logic.Utils.*;

public class SobelOperator implements Filter {
    @Override
    public BufferedImage edit(BufferedImage img) {
        Logger.getInstance().info("Sobel operator");
        int maxX = img.getWidth();
        int maxY = img.getHeight();
        BufferedImage newImg = new BufferedImage(maxX, maxY, img.getType());
        int x, y, nextX, prevX, nextY, prevY;
        float Sx, Sy, S, pixel1, pixel2, pixel3, pixel4;
        int binParam = Config.getBinSobel();

        for (y = 0; y < img.getHeight(); y++) {
            for (x = 0; x < img.getWidth(); x++) {
                nextX = Utils.checkBounds(x+1, 0, maxX);
                prevX = Utils.checkBounds(x-1, 0, maxX);
                nextY = Utils.checkBounds(y+1, 0, maxY);
                prevY = Utils.checkBounds(y-1, 0, maxY);

                pixel1 = getGray(img.getRGB(nextX, prevY));
                pixel2 = getGray(img.getRGB(nextX, nextY));
                pixel3 = getGray(img.getRGB(prevX, prevY));
                pixel4 = getGray(img.getRGB(prevX, nextY));

                Sx = pixel1 + pixel2 - pixel3 - pixel4 + getGray(2*img.getRGB(nextX, y)) - getGray(2*img.getRGB(prevX, y));
                Sy = pixel4 + pixel2 - pixel3 - pixel1 + getGray(2*img.getRGB(x, nextY)) - getGray(2*img.getRGB(x, prevY));

                S = Math.abs(Sx) + Math.abs(Sy);
                if (S > binParam) {
                    newImg.setRGB(x, y, Color.WHITE.getRGB());
                } else {
                    newImg.setRGB(x, y, Color.BLACK.getRGB());
                }
            }
        }
        return newImg;
    }

    private float getGray(int pixel){
        return (float) (0.299 * getR(pixel) + 0.587 * getG(pixel) + 0.114 * getB(pixel));
    }
}