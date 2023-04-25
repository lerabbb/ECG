package nsu.lerabbb.editor.logic.filter.pixel;

import nsu.lerabbb.editor.Logger;
import nsu.lerabbb.editor.logic.filter.Filter;

import java.awt.image.BufferedImage;

import static nsu.lerabbb.editor.logic.Utils.*;

public class BlackWhiteFilter implements Filter {
    @Override
    public BufferedImage edit(BufferedImage img){
        Logger.getInstance().info("Black-white filter");
        BufferedImage newImg = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
        int pixel, r,g,b;

        for(int y=0; y<img.getHeight(); y++){
            for(int x=0; x<img.getWidth(); x++){
                pixel = img.getRGB(x, y);

                r = getR(pixel);
                g = getG(pixel);
                b = getB(pixel);

                pixel = (int) (0.299 * r + 0.587 * g + 0.114 * b);
                pixel = getPixel(pixel, pixel, pixel);
                newImg.setRGB(x, y, pixel);
            }
        }
        return newImg;
    }
}


