package nsu.lerabbb.editor.logic.filter.border;

import nsu.lerabbb.editor.Logger;
import nsu.lerabbb.editor.logic.Config;
import nsu.lerabbb.editor.logic.Utils;
import nsu.lerabbb.editor.logic.filter.Filter;

import java.awt.*;
import java.awt.image.BufferedImage;


public class RobertsOperator implements Filter {

    @Override
    public BufferedImage edit(BufferedImage img) {
        Logger.getInstance().info("Roberts operator");
        BufferedImage newImg = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
        int binParam = Config.getBinRoberts();
        int x, y, nextX, nextY;
        float f, pixel1, pixel2, pixel3, pixel4, Gx, Gy;
        int maxX = img.getWidth();
        int maxY = img.getHeight();

        for(y=0;y<maxY;y++){
            for(x=0;x<maxX;x++){
                nextX = Utils.checkBounds(x+1, 0, maxX);
                nextY = Utils.checkBounds(y+1, 0, maxY);

                pixel1 = getGray(img.getRGB(x,y));
                pixel2 = getGray(img.getRGB(nextX, nextY));
                pixel3 = getGray(img.getRGB(x, nextY));
                pixel4 = getGray(img.getRGB(nextX, y));

                Gx = pixel1 - pixel2;
                Gy = pixel4 - pixel3;

                f = Math.abs(Gx) + Math.abs(Gy);
                if(f > binParam){
                    newImg.setRGB(x,y, Color.WHITE.getRGB());
                } else {
                    newImg.setRGB(x,y,Color.BLACK.getRGB());
                }
            }
        }
        return newImg;
    }
    private int getR(int pixel){
        return (pixel >> 16) & 0xff;
    }
    private int getG(int pixel){
        return (pixel >> 8) & 0xff;
    }
    private int getB(int pixel){
        return (pixel) & 0xff;
    }
    private float getGray(int pixel){
        return (float) (0.299 * getR(pixel) + 0.587 * getG(pixel) + 0.114 * getB(pixel));
    }
}
