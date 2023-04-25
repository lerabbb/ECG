package nsu.lerabbb.editor.logic.filter.pixel;

import nsu.lerabbb.editor.Logger;
import nsu.lerabbb.editor.logic.Utils;
import nsu.lerabbb.editor.logic.filter.Filter;

import java.awt.image.BufferedImage;

public class NegativeFilter implements Filter {
    @Override
    public BufferedImage edit(BufferedImage img) {
        Logger.getInstance().info("Negative filter");
        BufferedImage newImg = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
        int pixel, r, g, b, y, x;

        for (y = 0; y < img.getHeight(); y++) {
            for (x = 0; x < img.getWidth(); x++) {
                pixel = img.getRGB(x, y);

                r = 255 - Utils.getR(pixel);
                g = 255 - Utils.getG(pixel);
                b = 255 - Utils.getB(pixel);

                pixel = Utils.getPixel(r, g, b);
                newImg.setRGB(x, y, pixel);
            }
        }
        return newImg;
    }
}
